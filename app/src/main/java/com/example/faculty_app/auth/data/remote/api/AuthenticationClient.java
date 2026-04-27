package com.example.faculty_app.auth.data.remote.api;

import com.example.faculty_app.auth.data.remote.models.request.RefreshTokensRequest;
import com.example.faculty_app.auth.data.remote.models.request.SignInCodeRequest;
import com.example.faculty_app.auth.data.remote.models.request.SignOutRequest;
import com.example.faculty_app.auth.data.remote.models.request.VerifyCodeRequest;
import com.example.faculty_app.auth.data.remote.models.response.TokensResponse;
import com.example.faculty_app.core.api.axis.dto.response.VoidResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationClient {
    @POST("/auth/session-management/sign-in")
    Call<VoidResponse> requestSignInCode(@Body SignInCodeRequest request);

    @POST("/auth/session-management/verify-code")
    Call<TokensResponse> verifyCode(@Body VerifyCodeRequest request);

    @POST("/auth/session-management/refresh")
    Call<TokensResponse> refreshTokens(@Body RefreshTokensRequest request);

    @POST("/auth/session-management/sign-out")
    Call<VoidResponse> signOut(@Body SignOutRequest signOutRequest);
}
