package com.example.faculty_app;

import static com.example.faculty_app.core.Utils.isMockAuth;

import android.app.Application;
import android.util.Log;


import com.example.faculty_app.auth.services.TokenRefresher;
import com.example.faculty_app.core.api.axis.AxisService;
import com.example.faculty_app.auth.services.AuthInterceptor;
import com.example.faculty_app.auth.services.SessionManager;
import com.example.faculty_app.auth.services.TokenAuthenticator;

import okhttp3.Interceptor;

public class AxisFacultyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SessionManager.init(this);
        TokenRefresher.init(SessionManager.getInstance());
        AxisService.init(new Interceptor[]{new AuthInterceptor(() -> SessionManager.getInstance()
                                                                                   .getAccessToken())},
                         new TokenAuthenticator());

        if (isMockAuth())
            enableDevSession();
    }

    private void enableDevSession() {
        Log.d("DEV_SESSION_ENABLED", "Developer session enabled. Authentication is mocked.");
        SessionManager session = SessionManager.getInstance();

        session.setAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                                       ".eyJpZCI6NiwiZW1haWwiOiJiZWEuYmVsYXJtaW5vQGx1LmVkdS5waCIsInVzZXJuYW1lIjoiQmVhQmVsYTYiLCJyb2xlIjoicHJvZmVzc29yIiwic3VybmFtZSI6IkJlbGFybWlubyIsImZpcnN0TmFtZSI6IkJlYSIsIm1pZGRsZU5hbWUiOiIiLCJnZW5kZXIiOiJmZW1hbGUiLCJjb250YWN0TnVtYmVyIjoiMDkxNzEyMzQ1MDYiLCJjb2xsZWdlIjoiQ29sbGVnZSBvZiBDb21wdXRpbmcgU2NpZW5jZSIsImZhY3VsdHlSYW5rIjoicHJvZmVzc29yIiwiaWF0IjoxNzc3MjMyODExLCJleHAiOjIwOTI4MDg4MTF9.UDSUPabm_iwmrRMBUw5pHJvuLM7AxvLDoIct9AukauI");
        session.setRememberMe(true);

        //  optional mock payload
    }
}
