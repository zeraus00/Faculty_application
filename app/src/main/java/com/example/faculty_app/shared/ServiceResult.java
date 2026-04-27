package com.example.faculty_app.shared;

import androidx.annotation.NonNull;

public class ServiceResult<TData> {
    private final boolean success;
    private final TData data;
    @NonNull
    private final String message;

    public ServiceResult(boolean success, TData data, @NonNull String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public TData getData() {
        return data;
    }

    @NonNull
    public String getMessage() {
        return message;
    }
}
