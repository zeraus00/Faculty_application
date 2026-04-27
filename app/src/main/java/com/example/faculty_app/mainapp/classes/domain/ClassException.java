package com.example.faculty_app.mainapp.classes.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.faculty_app.shared.BaseException;

public class ClassException extends BaseException<ClassExceptionCode> {

    public ClassException(@NonNull ClassExceptionCode code, @NonNull String message) {
        super(code, message);
    }

    public ClassException(@NonNull ClassExceptionCode code,
                          @NonNull String message,
                          @Nullable Throwable cause) {
        super(code, message, cause);
    }
}
