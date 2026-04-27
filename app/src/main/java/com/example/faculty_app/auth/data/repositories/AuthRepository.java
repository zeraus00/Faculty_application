package com.example.faculty_app.auth.data.repositories;

import androidx.annotation.Nullable;

import com.example.faculty_app.auth.data.remote.api.AuthApi;
import com.example.faculty_app.auth.data.local.SessionManager;
import com.example.faculty_app.core.api.axis.dto.HttpCallback;
import com.example.faculty_app.auth.data.remote.models.request.RefreshTokensRequest;
import com.example.faculty_app.auth.data.remote.models.response.Tokens;
import com.example.faculty_app.auth.data.remote.models.response.TokensResponse;

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

    @Nullable
    public Tokens refreshTokens() {
        RefreshTokensRequest request;

        try {
            request = buildRequest();
        } catch (IllegalStateException e) {
            return null;
        }

        try {
            var response = AuthApi.refreshTokens(request);

            if (!response.isSuccessful()) {
                return null;
            }

            var body = response.body();
            if (body == null || !body.success || body.result == null) {
                return null;
            }

            sessionManager.setAccessToken(body.result.accessToken);
            sessionManager.setRefreshToken(body.result.refreshToken);

            return body.result;

        } catch (IOException e) {
            return null;
        }
    }

    public void refreshTokensAsync(RefreshCallback callback) {
        RefreshTokensRequest request;

        try {
            request = buildRequest();
        } catch (IllegalStateException e) {
            callback.onFail(e.getMessage());
            return;
        }

        AuthApi.refreshTokensAsync(request, new HttpCallback<>() {
            @Override
            public void onSuccess(TokensResponse response) {
                if (!response.success || response.result == null) {
                    callback.onFail(
                            response.message != null ? response.message : "Refresh failed.");
                    return;
                }

                var tokens = response.result;

                sessionManager.setAccessToken(tokens.accessToken);
                sessionManager.setRefreshToken(tokens.refreshToken);

                callback.onSuccess(tokens);
            }

            @Override
            public void onError(String message) {

                callback.onFail(message);
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

    public interface RefreshCallback {
        void onSuccess(Tokens tokens);

        void onFail(String message);
    }
}