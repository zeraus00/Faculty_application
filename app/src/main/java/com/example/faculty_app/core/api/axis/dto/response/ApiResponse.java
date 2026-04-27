package com.example.faculty_app.core.api.axis.dto.response;

public class ApiResponse<T> {
    public boolean success;
    public T result;
    public String message;

    public ApiResponse() {
    }

    public boolean hasResult() {
        return result != null;
    }
}
