package com.example.faculty_app.core.api.axis.dto.response;

public class ResultSuccess<T> {
    public boolean success;
    public T result;
    public String message;

    public ResultSuccess() {
    }

    public boolean hasResult() {
        return result != null;
    }
}
