package com.example.faculty_app.core.api.axis.dto.response;

public class AxisResponse<T> {
    public boolean success;
    public T result;
    public String message;

    public AxisResponse() {
    }

    public boolean hasResult() {
        return result != null;
    }
}
