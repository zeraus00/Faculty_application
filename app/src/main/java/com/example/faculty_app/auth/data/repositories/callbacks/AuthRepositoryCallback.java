package com.example.faculty_app.auth.data.repositories.callbacks;

import com.example.faculty_app.shared.BaseResult;

public interface AuthRepositoryCallback<T> {
    void onResult(BaseResult<T> result);
}
