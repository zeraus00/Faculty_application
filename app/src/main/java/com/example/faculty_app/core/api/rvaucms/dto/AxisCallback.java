package com.example.faculty_app.core.api.rvaucms.dto;

import com.example.faculty_app.core.api.rvaucms.dto.response.AxisResult;

public interface AxisCallback<TResult> {
    void onResult(AxisResult<TResult> result);
}
