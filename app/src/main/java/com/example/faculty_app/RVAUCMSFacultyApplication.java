package com.example.faculty_app;

import android.app.Application;
import android.util.Log;


import com.example.faculty_app.core.api.rvaucms.RvaucMsService;
import com.example.faculty_app.core.auth.AuthInterceptor;
import com.example.faculty_app.core.auth.SessionManager;
import com.example.faculty_app.core.auth.TokenAuthenticator;

import okhttp3.Interceptor;

public class RVAUCMSFacultyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SessionManager.init(this);
        RvaucMsService.init(
                new Interceptor[] {
                    new AuthInterceptor(() -> SessionManager.getInstance().getAccessToken())
                },
                new TokenAuthenticator()
        );

        if (BuildConfig.DEBUG && BuildConfig.USE_MOCK_AUTH) enableDevSession();
    }
    private void enableDevSession() {
        Log.d("DEV_SESSION_ENABLED", "Developer session enabled. Authentication is mocked.");
        SessionManager session = SessionManager.getInstance();

        session.setAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NywiZW1haWwiOiJsZWUuYWdhdG9uQGdtYWlsLmNvbSIsInVzZXJuYW1lIjoiTGVlQTciLCJyb2xlIjoic3R1ZGVudCIsInN1cm5hbWUiOiJBZ2F0b24iLCJmaXJzdE5hbWUiOiJMZWUgQXJjaGVsYXVzIiwibWlkZGxlTmFtZSI6IiIsImdlbmRlciI6Im1hbGUiLCJjb250YWN0TnVtYmVyIjoiMDkxNzEyMzQ1MDciLCJkZXBhcnRtZW50IjoiRGVwYXJ0bWVudCBPZiBDb21wdXRlciBTY2llbmNlIiwic3R1ZGVudE51bWJlciI6IjEwMS0wMDAxIiwieWVhckxldmVsIjozLCJibG9jayI6IkEiLCJpYXQiOjE3NzcyMDIzNzcsImV4cCI6MjA5Mjc3ODM3N30.mPEquFTY03eQS205YhcLvKSvmRXO9_rTwYvpUXQU19k");
        session.setRememberMe(true);

        //  optional mock payload
    }
}
