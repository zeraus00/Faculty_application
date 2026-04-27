package com.example.faculty_app.auth.data.remote.api;

import com.example.faculty_app.auth.data.remote.models.request.RefreshTokensRequest;
import com.example.faculty_app.auth.data.remote.models.request.SignInCodeRequest;
import com.example.faculty_app.auth.data.remote.models.request.SignOutRequest;
import com.example.faculty_app.auth.data.remote.models.request.VerifyCodeRequest;
import com.example.faculty_app.auth.data.remote.models.response.AxisTokens;
import com.example.faculty_app.core.api.axis.dto.response.AxisVoid;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AxisAuthClient {
    @POST("/auth/session-management/sign-in")
    Call<AxisVoid> requestSignInCode(@Body SignInCodeRequest request);

    @POST("/auth/session-management/verify-code")
    Call<AxisTokens> verifyCode(@Body VerifyCodeRequest request);

    @POST("/auth/session-management/refresh")
    Call<AxisTokens> refreshTokens(@Body RefreshTokensRequest request);

    @POST("/auth/session-management/sign-out")
    Call<AxisVoid> signOut(@Body SignOutRequest signOutRequest);
}
