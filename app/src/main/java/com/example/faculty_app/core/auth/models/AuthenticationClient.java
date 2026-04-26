package com.example.faculty_app.core.auth.models;

import com.example.faculty_app.core.api.rvaucms.dto.response.VoidResponse;

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
