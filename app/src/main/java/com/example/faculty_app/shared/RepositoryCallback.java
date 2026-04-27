package com.example.faculty_app.shared;

public interface RepositoryCallback<TResult> {
    void onResult(RepositoryResult<TResult> result);
}
