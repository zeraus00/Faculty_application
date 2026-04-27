package com.example.faculty_app.mainapp.classes.data.local.models;

import com.example.faculty_app.mainapp.classes.data.remote.response.classlist.ClassList;

public interface ClassListCallback {
    void onSuccess(ClassList classList);

    void onFail(String message);
}
