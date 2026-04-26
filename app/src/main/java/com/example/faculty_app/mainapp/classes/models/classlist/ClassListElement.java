package com.example.faculty_app.mainapp.classes.models.classlist;

import androidx.annotation.Nullable;

import com.example.faculty_app.mainapp.classes.models.shared.Cls;
import com.example.faculty_app.mainapp.classes.models.shared.Course;
import com.example.faculty_app.mainapp.classes.models.shared.Offering;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassListElement {
    @JsonProperty("class")
    public Cls cls;
    public Course course;
    @Nullable
    public Offering offering;
    public ClassListElement() {}
}
