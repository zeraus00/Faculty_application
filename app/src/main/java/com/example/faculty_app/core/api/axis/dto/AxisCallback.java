package com.example.faculty_app.core.api.axis.dto;

import com.example.faculty_app.core.api.axis.dto.response.AxisResult;

public interface AxisCallback<TResult> {
    void onResult(AxisResult<TResult> result);
}
