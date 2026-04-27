package com.example.faculty_app.shared;

import androidx.annotation.NonNull;

public abstract class BaseResult<TData> {
    public final static class Success<TData> extends BaseResult<TData> {
        private final TData data;

        public Success(TData data) {
            this.data = data;
        }

        public TData getData() {
            return data;
        }
    }

    public final static class Fail<TData, TException extends BaseException<?>> extends BaseResult<TData> {
        @NonNull
        private final TException exception;

        public Fail(@NonNull TException exception) {
            this.exception = exception;
        }

        @NonNull
        public TException getException() {
            return exception;
        }
    }
}
