package com.example.faculty_app.core.api.axis;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.faculty_app.BuildConfig;
import com.example.faculty_app.core.api.axis.dto.AxisCallback;
import com.example.faculty_app.core.api.axis.dto.response.ResultFail;
import com.example.faculty_app.core.api.axis.dto.response.AxisResponse;
import com.example.faculty_app.core.api.axis.dto.HttpCallback;
import com.example.faculty_app.core.api.axis.dto.response.AxisResult;
import com.example.faculty_app.core.api.axis.dto.response.ResultSuccess;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Axis {
    private static Retrofit retrofit;
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <TResult, TResponse extends ResultSuccess<TResult>> Callback<TResponse> axisCallback(
            AxisCallback<TResult> callback) {
        return new Callback<TResponse>() {
            @Override
            public void onResponse(@NonNull Call<TResponse> call,
                                   @NonNull Response<TResponse> response) {

                if (!response.isSuccessful()) {
                    String message = "Something went wrong.";

                    try (var errorBody = response.errorBody()) {
                        if (errorBody != null) {
                            var json = errorBody.string();
                            var mapped = mapper.readValue(json, ResultFail.class);

                            message = mapped.message;
                        }
                    } catch (IOException e) {
                        Log.e("AXIS_API", "Failed to parse response error.", e);
                    }

                    callback.onResult(new AxisResult.Fail<>(response.code(), message));
                    return;
                }
                var body = response.body();

                if (body == null) {
                    callback.onResult(new AxisResult.Fail<>("Response body is null."));
                    return;
                }

                if (!body.success) {
                    callback.onResult(new AxisResult.Fail<>(body.message));
                    return;
                }

                callback.onResult(new AxisResult.Success<TResult>(body.result));

            }

            @Override
            public void onFailure(@NonNull Call<TResponse> call, @NonNull Throwable t) {
                callback.onResult(new AxisResult.Fail<>("Failed request.", t));
            }
        };
    }

    public static <R, T extends AxisResponse<R>> Callback<T> rvaucMsCallback(HttpCallback<T> callback) {
        return new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                }
                else {
                    String message = "Something went wrong";

                    try {
                        try (var errorBody = response.errorBody()) {

                            if (errorBody != null) {
                                var json = errorBody.string();

                                var mapped = mapper.readValue(json, ResultFail.class);

                                message = mapped.message;
                            }
                        }
                    } catch (IOException e) {
                        Log.e("ApiErrorParser", "Failed to parse error", e);
                    }

                    callback.onError(message);
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                callback.onError(t.getMessage());
            }
        };
    }

    public static <T> T createService(Class<T> classRef) {
        return getRetrofitClient().create(classRef);
    }

    public static Retrofit getRetrofitClient() {
        if (retrofit == null)
            throw new RuntimeException("RvaucMs client was not initialized.");
        return retrofit;
    }

    public static <TInterceptor extends Interceptor> void init(TInterceptor[] interceptors,
                                                               Authenticator authenticator) {
        try {
            var retrofitBuilder = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                                                        .addConverterFactory(JacksonConverterFactory.create());

            var okHttpClientBuilder = new OkHttpClient.Builder().authenticator(authenticator);

            for (TInterceptor interceptor : interceptors) {
                okHttpClientBuilder.addInterceptor(interceptor);
            }

            var okHttpClient = okHttpClientBuilder.build();

            retrofitBuilder.client(okHttpClient);

            retrofit = retrofitBuilder.build();

            Log.e("RVAUCMS", "Success initializing rvaucms api service.");
        } catch (Exception e) {
            Log.e("RVAUCMS", "Failed initializing rvaucms api service.", e);
            throw new RuntimeException(e);
        }
    }
}
