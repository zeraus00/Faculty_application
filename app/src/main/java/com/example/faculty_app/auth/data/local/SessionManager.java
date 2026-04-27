package com.example.faculty_app.auth.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.example.faculty_app.auth.data.remote.jwt.JwtDecoder;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private final SharedPreferences prefs;
    private static SessionManager instance;
    private final List<Runnable> logoutListeners = new ArrayList<>();
    private String email;
    private boolean rememberMe;
    private String accessToken;
    private Payload payload;

    private SessionManager(Context context) {
        prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE);
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null)
            throw new RuntimeException("SessionManager is not initialized.");
        return instance;
    }

    public static void init(Context context) {
        if (instance == null)
            instance = new SessionManager(context);
    }

    public void addLogoutListener(Runnable listener) {
        if (!logoutListeners.contains(listener))
            logoutListeners.add(listener);
    }

    public void removeLogoutListener(Runnable listener) {
        logoutListeners.remove(listener);
    }

    public void notifyLoggedOut() {
        for (Runnable listener : new ArrayList<>(logoutListeners))
            listener.run();
    }

    public void clear() {
        email = null;
        rememberMe = false;
        accessToken = null;
        payload = null;
        var editor = prefs.edit();
        editor.clear();
        editor.apply();
        notifyLoggedOut();
    }

    public Payload getPayload() {
        return payload;
    }

    public void setAccessToken(String token) {
        accessToken = token;
        setPayload(token);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setRefreshToken(String token) {
        var editor = prefs.edit();
        editor.putString("refreshToken", token);
        editor.apply();
    }

    public @Nullable String getRefreshToken() {
        return prefs.getString("refreshToken", null);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public boolean getRememberMe() {
        return rememberMe;
    }

    private void setPayload(String token) {
        payload = JwtDecoder.decodeJwt(token);
    }
}
