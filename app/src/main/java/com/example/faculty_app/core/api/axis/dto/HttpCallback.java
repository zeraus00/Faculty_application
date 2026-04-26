package com.example.faculty_app.core.api.axis.dto;

public interface HttpCallback<T> {
    void onSuccess(T response);

    void onError(String message);
}
