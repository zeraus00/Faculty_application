package com.example.faculty_app.mainapp.classes.data.repositories;

import com.example.faculty_app.shared.BaseResult;

public interface ScheduleRepositoryCallback<T> {
    void onResult(BaseResult<T> result);
}
