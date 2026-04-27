package com.example.faculty_app.auth.data.repositories.callbacks;

import com.example.faculty_app.auth.data.remote.models.response.Tokens;
import com.example.faculty_app.shared.BaseResult;

public interface RefreshCallback {
    void onResult(BaseResult<Tokens> result);
}
