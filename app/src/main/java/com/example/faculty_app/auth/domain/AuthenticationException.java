package com.example.faculty_app.auth.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.faculty_app.shared.BaseException;

public class AuthenticationException extends BaseException<AuthenticationExceptionCode> {
    public AuthenticationException(@NonNull AuthenticationExceptionCode code,
                                   @NonNull String message) {
        super(code, message);
    }

    public AuthenticationException(@NonNull AuthenticationExceptionCode code,
                                   @NonNull String message,
                                   @Nullable Throwable cause) {
        super(code, message, cause);
    }
}
