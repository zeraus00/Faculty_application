package com.example.faculty_app.mainapp.classes.domain.models;

import com.example.faculty_app.mainapp.classes.data.response.classlist.ClassList;

public interface ClassListCallback {
    void onSuccess(ClassList classList);

    void onFail(String message);
}
