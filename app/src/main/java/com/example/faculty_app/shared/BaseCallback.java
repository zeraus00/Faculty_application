package com.example.faculty_app.shared;

public interface BaseCallback<T> {
    void onSuccess(T dto);

    void onFail(String message);
}
