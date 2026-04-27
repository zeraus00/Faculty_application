package com.example.faculty_app.core.api.axis.dto.response;

public class ApiSuccess<T> {
    public boolean success = true;
    public T result;
    public String message;

    public ApiSuccess() {
    }
}
