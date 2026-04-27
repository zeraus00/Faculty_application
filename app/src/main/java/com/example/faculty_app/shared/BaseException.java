package com.example.faculty_app.shared;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BaseException<E extends Enum<E>> {
    @NonNull
    private final E code;
    @NonNull
    private final String message;
    @Nullable
    private final Throwable cause;

    public BaseException(@NonNull E code, @NonNull String message) {
        this.code = code;
        this.message = message;
        this.cause = null;
    }

    public BaseException(@NonNull E code, @NonNull String message, @Nullable Throwable cause) {
        this.code = code;
        this.message = message;
        this.cause = cause;
    }

    @NonNull
    public E getCode() {
        return code;
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
