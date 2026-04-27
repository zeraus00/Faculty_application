package com.example.faculty_app.mainapp.classes.data.remote.response.classlist;

import androidx.annotation.Nullable;

import com.example.faculty_app.mainapp.classes.data.remote.response.shared.Cls;
import com.example.faculty_app.mainapp.classes.data.remote.response.shared.Course;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassListElement {
    @JsonProperty("class")
    public Cls cls;
    public Course course;
    @Nullable
    public Offering offering;

    public ClassListElement() {
    }
}
