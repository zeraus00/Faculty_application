package com.example.faculty_app.shared;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ServiceResult<TData> {
    private final boolean success;
    private final TData data;
    @NonNull
    private final String message;
    @Nullable
    private final Throwable cause;

    public ServiceResult(boolean success, TData data, @NonNull String message) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.cause = null;
    }

    public ServiceResult(boolean success,
                         TData data,
                         @NonNull String message,
                         @Nullable Throwable cause) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.cause = cause;
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

    @Nullable
    public Throwable getCause() {
        return cause;
    }
}
