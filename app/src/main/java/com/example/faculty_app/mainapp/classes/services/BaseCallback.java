package com.example.faculty_app.mainapp.classes.services;

public interface BaseCallback<T> {
    void onSuccess(T dto);

    void onFail(String message);
}
