package com.example.faculty_app;

import android.app.Application;


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
    }
}
