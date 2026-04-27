package com.example.faculty_app.auth.data.repositories;

import androidx.annotation.Nullable;

import com.example.faculty_app.auth.data.remote.api.AuthApi;
import com.example.faculty_app.auth.data.local.SessionManager;
import com.example.faculty_app.auth.data.remote.api.AxisAuth;
import com.example.faculty_app.auth.data.repositories.callbacks.AuthRepositoryCallback;
import com.example.faculty_app.auth.domain.AuthenticationException;
import com.example.faculty_app.auth.domain.AuthenticationExceptionCode;
import com.example.faculty_app.core.api.axis.dto.AxisCallback;
import com.example.faculty_app.core.api.axis.dto.HttpCallback;
import com.example.faculty_app.auth.data.remote.models.request.RefreshTokensRequest;
import com.example.faculty_app.auth.data.remote.models.response.Tokens;
import com.example.faculty_app.auth.data.remote.models.response.TokensResponse;
import com.example.faculty_app.core.api.axis.dto.response.AxisResult;
import com.example.faculty_app.shared.BaseResult;

import java.io.IOException;

public class AuthRepository {

    private static AuthRepository instance;
    private final SessionManager sessionManager;

    public AuthRepository(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public static synchronized void init(SessionManager sessionManager) {
        if (instance == null) {
            instance = new AuthRepository(sessionManager);
        }
    }

    public static synchronized AuthRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("TokenRefresher is not initialized.");
        }
        return instance;
    }

    public BaseResult<Tokens> axisRefresh() {
        RefreshTokensRequest request;

        try {
            request = buildRequest();
        } catch (IllegalStateException e) {
            return new BaseResult.Fail<>(new AuthenticationException(AuthenticationExceptionCode.REFRESH_EXCEPTION,
                                                                     "Failed refreshing session.",
                                                                     e));
        }

        try {
            var response = AuthApi.refreshTokens(request);

            if (!response.isSuccessful()) {
                return new BaseResult.Fail<>(new AuthenticationException(AuthenticationExceptionCode.REFRESH_EXCEPTION,
                                                                         "Failed refreshing " +
                                                                                 "session."));
            }

            var body = response.body();
            if (body == null || !body.success || body.result == null) {
                return new BaseResult.Fail<>(new AuthenticationException(AuthenticationExceptionCode.REFRESH_EXCEPTION,
                                                                         "Failed refreshing " +
                                                                                 "session."));
            }

            sessionManager.setAccessToken(body.result.accessToken);
            sessionManager.setRefreshToken(body.result.refreshToken);

            return new BaseResult.Success<>(body.result);

        } catch (IOException e) {
            return new BaseResult.Fail<>(new AuthenticationException(AuthenticationExceptionCode.REFRESH_EXCEPTION,
                                                                     "Failed refreshing session.",
                                                                     e));
        }
    }

    public void axisRefreshAsync(AuthRepositoryCallback<Tokens> callback) {
        RefreshTokensRequest request;

        try {
            request = buildRequest();
        } catch (IllegalStateException e) {
            callback.onResult(new BaseResult.Fail<>(new AuthenticationException(
                    AuthenticationExceptionCode.REFRESH_EXCEPTION,
                    "Failed refreshing session.",
                    e)));
            return;
        }

        AxisAuth.refreshTokensAsync(request, new AxisCallback<Tokens>() {
            @Override
            public void onResult(AxisResult<Tokens> result) {
                if (result instanceof AxisResult.Success) {
                    var tokens = ((AxisResult.Success<Tokens>) result).getData();
                    sessionManager.setAccessToken(tokens.accessToken);
                    sessionManager.setRefreshToken(tokens.refreshToken);

                    callback.onResult(new BaseResult.Success<>(tokens));
                }
                else if (result instanceof AxisResult.Fail) {
                    var fail = (AxisResult.Fail<Tokens>) result;

                    var code = fail.code;

                    if (code != null && code == 401)
                        sessionManager.clear();

                    callback.onResult(new BaseResult.Fail<>(new AuthenticationException(
                            AuthenticationExceptionCode.REFRESH_EXCEPTION,
                            fail.message,
                            fail.throwable)));
                }
            }
        });
    }

    private RefreshTokensRequest buildRequest() {
        String refreshToken = sessionManager.getRefreshToken();

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalStateException("Missing refresh token.");
        }

        RefreshTokensRequest request = new RefreshTokensRequest();
        request.refreshToken = refreshToken;

        return request;
    }
}