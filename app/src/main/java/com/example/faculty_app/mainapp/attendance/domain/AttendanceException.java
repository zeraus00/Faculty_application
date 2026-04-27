package com.example.faculty_app.mainapp.attendance.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.faculty_app.shared.BaseException;

public class AttendanceException extends BaseException<AttendanceExceptionCode> {
    public AttendanceException(@NonNull AttendanceExceptionCode code, @NonNull String message) {
        super(code, message);
    }

    public AttendanceException(@NonNull AttendanceExceptionCode code,
                               @NonNull String message,
                               @Nullable Throwable cause) {
        super(code, message, cause);
    }
}
