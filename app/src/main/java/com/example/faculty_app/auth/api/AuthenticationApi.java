package com.example.faculty_app.auth.api;

import static com.example.faculty_app.core.api.rvaucms.RvaucMsService.rvaucMsCallback;

import com.example.faculty_app.core.api.rvaucms.dto.HttpCallback;
import com.example.faculty_app.core.api.rvaucms.dto.response.VoidResponse;
import com.example.faculty_app.core.api.rvaucms.RvaucMsService;
import com.example.faculty_app.auth.api.models.AuthenticationClient;
import com.example.faculty_app.auth.api.models.request.RefreshTokensRequest;
import com.example.faculty_app.auth.api.models.request.SignInCodeRequest;
import com.example.faculty_app.auth.api.models.request.SignOutRequest;
import com.example.faculty_app.auth.api.models.response.TokensResponse;
import com.example.faculty_app.auth.api.models.request.VerifyCodeRequest;

import java.io.IOException;

import retrofit2.Response;

public class AuthenticationApi {
    private static AuthenticationClient authenticationClient;

    public static void requestSignInCode(SignInCodeRequest request,
                                         HttpCallback<VoidResponse> callback) {
        var auth = getAuthenticationClient();
        auth.requestSignInCode(request).enqueue(rvaucMsCallback(callback));
    }

    public static void verifyCode(VerifyCodeRequest request,
                                  HttpCallback<TokensResponse> callback) {
        var auth = getAuthenticationClient();
        auth.verifyCode(request).enqueue(rvaucMsCallback(callback));
    }

    public static Response<TokensResponse> refreshTokens(RefreshTokensRequest request) throws IOException {
        return getAuthenticationClient().refreshTokens(request).execute();
    }

    public static void refreshTokensAsync(RefreshTokensRequest request,
                                          HttpCallback<TokensResponse> callback) {
        var auth = getAuthenticationClient();
        auth.refreshTokens(request).enqueue(rvaucMsCallback(callback));
    }

    public static void signOut(SignOutRequest request, HttpCallback<VoidResponse> callback) {
        var auth = getAuthenticationClient();
        auth.signOut(request).enqueue(rvaucMsCallback(callback));
    }

    private static AuthenticationClient getAuthenticationClient() {
        if (authenticationClient == null)
            authenticationClient = RvaucMsService.createService(AuthenticationClient.class);
        return authenticationClient;
    }
}
