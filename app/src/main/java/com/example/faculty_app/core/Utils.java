package com.example.faculty_app.core;

import com.example.faculty_app.BuildConfig;

public class Utils {
    public static boolean isMockAuth() {
        return BuildConfig.DEBUG && BuildConfig.USE_MOCK_AUTH;
    }
}
