package com.example.faculty_app.shared;

public interface ServiceCallback<TData> {
    void onResult(ServiceResult<TData> result);
}
