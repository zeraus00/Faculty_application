package com.example.faculty_app.auth.data.repositories;

import com.example.faculty_app.auth.data.local.SessionManager;
import com.example.faculty_app.auth.data.remote.api.AxisAuth;
import com.example.faculty_app.auth.data.remote.models.request.SignInCodeRequest;
import com.example.faculty_app.auth.data.remote.models.request.VerifyCodeRequest;
import com.example.faculty_app.auth.domain.AuthenticationException;
import com.example.faculty_app.auth.domain.AuthenticationExceptionCode;
import com.example.faculty_app.core.api.axis.dto.AxisCallback;
import com.example.faculty_app.auth.data.remote.models.request.RefreshTokensRequest;
import com.example.faculty_app.auth.data.remote.models.response.Tokens;
import com.example.faculty_app.core.api.axis.dto.response.AxisResult;
import com.example.faculty_app.shared.RepositoryCallback;
import com.example.faculty_app.shared.RepositoryResult;

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

    public void requestSignInCode(String email,
                                  String password,
                                  boolean rememberMe,
                                  RepositoryCallback<Void> callback) {
        var request = new SignInCodeRequest();
        request.identifier = email;
        request.password = password;
        request.isPersistentAuth = rememberMe;

        AxisAuth.requestSignInCode(request, new AxisCallback<Void>() {
            @Override
            public void onResult(AxisResult<Void> result) {
                if (result instanceof AxisResult.Success) {
                    sessionManager.setEmail(request.identifier);
                    sessionManager.setRememberMe(request.isPersistentAuth);

                    callback.onResult(new RepositoryResult.Success<Void>(null));
                }
                else if (result instanceof AxisResult.Fail) {
                    var fail = (AxisResult.Fail<Void>) result;
                    var code = fail.code;
                    var message = fail.message;

                    callback.onResult(new RepositoryResult.Fail<>(new AuthenticationException(
                            code != null && code == 401 ?
                            AuthenticationExceptionCode.IDENTITY_VERIFICATION_EXCEPTION :
                            AuthenticationExceptionCode.CODE_REQUEST_EXCEPTION, message)));
                }
            }
        });
    }

    public void verifyCode(String email,
                           String code,
                           boolean rememberMe,
                           RepositoryCallback<Void> callback) {
        var request = new VerifyCodeRequest();
        request.email = email;
        request.code = code;
        request.isPersistentAuth = rememberMe;

        AxisAuth.verifyCode(request, new AxisCallback<Tokens>() {
            @Override
            public void onResult(AxisResult<Tokens> result) {
                if (result instanceof AxisResult.Success) {
                    var tokens = ((AxisResult.Success<Tokens>) result).getData();
                    sessionManager.setAccessToken(tokens.accessToken);
                    sessionManager.setRefreshToken(tokens.refreshToken);

                    callback.onResult(new RepositoryResult.Success<>(null));
                }
                else if (result instanceof AxisResult.Fail) {
                    var fail = (AxisResult.Fail<Tokens>) result;
                    var code = fail.code;
                    callback.onResult(new RepositoryResult.Fail<Void, AuthenticationException>(new AuthenticationException(
                            code != null && code == 401 ?
                            AuthenticationExceptionCode.IDENTITY_VERIFICATION_EXCEPTION :
                            AuthenticationExceptionCode.CODE_VERIFICATION_EXCEPTION,
                            fail.message,
                            fail.throwable)));
                }
            }
        });
    }

    public RepositoryResult<Tokens> refresh() {
        RefreshTokensRequest request;

        try {
            request = buildRefreshRequest();
        } catch (IllegalStateException e) {
            return new RepositoryResult.Fail<>(new AuthenticationException(
                    AuthenticationExceptionCode.REFRESH_EXCEPTION,
                    "Failed refreshing session.",
                    e));
        }

        try {
            var response = AxisAuth.refreshTokens(request);

            if (!response.isSuccessful()) {
                return new RepositoryResult.Fail<>(new AuthenticationException(
                        AuthenticationExceptionCode.REFRESH_EXCEPTION,
                        "Failed refreshing " + "session."));
            }

            var body = response.body();
            if (body == null || !body.success || body.result == null) {
                return new RepositoryResult.Fail<>(new AuthenticationException(
                        AuthenticationExceptionCode.REFRESH_EXCEPTION,
                        "Failed refreshing " + "session."));
            }

            sessionManager.setAccessToken(body.result.accessToken);
            sessionManager.setRefreshToken(body.result.refreshToken);

            return new RepositoryResult.Success<>(body.result);

        } catch (IOException e) {
            return new RepositoryResult.Fail<>(new AuthenticationException(
                    AuthenticationExceptionCode.REFRESH_EXCEPTION,
                    "Failed refreshing session.",
                    e));
        }
    }

    public void refreshAsync(RepositoryCallback<Tokens> callback) {
        RefreshTokensRequest request;

        try {
            request = buildRefreshRequest();
        } catch (IllegalStateException e) {
            callback.onResult(new RepositoryResult.Fail<>(new AuthenticationException(
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

                    callback.onResult(new RepositoryResult.Success<>(tokens));
                }
                else if (result instanceof AxisResult.Fail) {
                    var fail = (AxisResult.Fail<Tokens>) result;

                    var code = fail.code;

                    if (code != null && code == 401)
                        sessionManager.clear();

                    callback.onResult(new RepositoryResult.Fail<>(new AuthenticationException(
                            AuthenticationExceptionCode.REFRESH_EXCEPTION,
                            fail.message,
                            fail.throwable)));
                }
            }
        });
    }

    private RefreshTokensRequest buildRefreshRequest() {
        String refreshToken = sessionManager.getRefreshToken();

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalStateException("Missing refresh token.");
        }

        RefreshTokensRequest request = new RefreshTokensRequest();
        request.refreshToken = refreshToken;

        return request;
    }
}