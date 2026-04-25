package com.example.faculty_app.core.api.rvaucms.dto;

public interface HttpCallback<T> {
    void onSuccess(T response);
    void onError(String message);
}
