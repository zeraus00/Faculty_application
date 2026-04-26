package com.example.faculty_app.core.auth.models;

public class VerifyCodeRequest {
    public String email;
    public String code;
    public boolean isPersistentAuth = false;
    public VerifyCodeRequest() {}
}
