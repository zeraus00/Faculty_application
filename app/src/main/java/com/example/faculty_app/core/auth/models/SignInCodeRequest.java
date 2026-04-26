package com.example.faculty_app.core.auth.models;

public class SignInCodeRequest {
    public String identifier;
    public String password;
    public boolean isPersistentAuth = false;
    public String deviceToken = "";
    public SignInCodeRequest(){}
}
