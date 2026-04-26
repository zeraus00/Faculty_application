package com.example.faculty_app.core.api.axis.dto.response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class AxisResult<T> {
    public static final class Success<T> extends AxisResult<T> {
        private final T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    public static final class Fail<T> extends AxisResult<T> {
        @Nullable
        public final Integer code;
        @NonNull
        public final String message;
        @Nullable
        public final Throwable throwable;

        public Fail(@NonNull String message) {
            this.code = null;
            this.message = message;
            this.throwable = null;
        }

        public Fail(@Nullable Integer code, @NonNull String message) {
            this.code = code;
            this.message = message;
            this.throwable = null;
        }

        public Fail(@NonNull String message, @Nullable Throwable throwable) {
            this.code = null;
            this.message = message;
            this.throwable = throwable;
        }

        public Fail(@Nullable Integer code,
                    @NonNull String message,
                    @Nullable Throwable throwable) {
            this.code = code;
            this.message = message;
            this.throwable = throwable;
        }
    }
}
