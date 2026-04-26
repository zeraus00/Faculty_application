package com.example.faculty_app.auth.api.models.request;

public class VerifyCodeRequest {
    public String email;
    public String code;
    public boolean isPersistentAuth = false;

    public VerifyCodeRequest() {
    }
}
