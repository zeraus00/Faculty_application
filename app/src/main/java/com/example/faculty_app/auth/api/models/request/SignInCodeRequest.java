package com.example.faculty_app.auth.api.models.request;

public class SignInCodeRequest {
    public String identifier;
    public String password;
    public boolean isPersistentAuth = false;
    public String deviceToken = "";

    public SignInCodeRequest() {
    }
}
