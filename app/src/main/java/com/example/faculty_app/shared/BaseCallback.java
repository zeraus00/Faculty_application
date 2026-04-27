package com.example.faculty_app.shared;

public interface BaseCallback<TResult> {
    void onResult(BaseResult<TResult> result);
}
