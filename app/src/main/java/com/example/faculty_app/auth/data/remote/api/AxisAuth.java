package com.example.faculty_app.auth.data.remote.api;

import static com.example.faculty_app.core.api.axis.Axis.axisCallback;
import static com.example.faculty_app.core.api.axis.Axis.rvaucMsCallback;

import com.example.faculty_app.auth.data.remote.models.request.RefreshTokensRequest;
import com.example.faculty_app.auth.data.remote.models.request.SignInCodeRequest;
import com.example.faculty_app.auth.data.remote.models.request.SignOutRequest;
import com.example.faculty_app.auth.data.remote.models.request.VerifyCodeRequest;
import com.example.faculty_app.auth.data.remote.models.response.AxisTokens;
import com.example.faculty_app.auth.data.remote.models.response.Tokens;
import com.example.faculty_app.core.api.axis.Axis;
import com.example.faculty_app.core.api.axis.dto.AxisCallback;

import java.io.IOException;

import retrofit2.Response;

public class AxisAuth {

    private static AxisAuthClient authClient;

    public static void requestSignInCode(SignInCodeRequest request, AxisCallback<Void> callback) {
        var auth = getAuthClient();
        auth.requestSignInCode(request).enqueue(axisCallback(callback));
    }

    public static void verifyCode(VerifyCodeRequest request, AxisCallback<Tokens> callback) {
        var auth = getAuthClient();
        auth.verifyCode(request).enqueue(axisCallback(callback));
    }

    public static Response<AxisTokens> refreshTokens(RefreshTokensRequest request) throws IOException {
        return getAuthClient().refreshTokens(request).execute();
    }

    public static void refreshTokensAsync(RefreshTokensRequest request,
                                          AxisCallback<Tokens> callback) {
        var auth = getAuthClient();
        auth.refreshTokens(request).enqueue(axisCallback(callback));
    }

    public static void signOut(SignOutRequest request, AxisCallback<Void> callback) {
        var auth = getAuthClient();
        auth.signOut(request).enqueue(axisCallback(callback));
    }

    private static AxisAuthClient getAuthClient() {
        if (authClient == null)
            authClient = Axis.createService(AxisAuthClient.class);
        return authClient;
    }
}
