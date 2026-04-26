package com.example.faculty_app.mainapp.classes.models.classruntime;

import com.example.faculty_app.mainapp.classes.models.shared.Cls;
import com.example.faculty_app.mainapp.classes.models.shared.Course;
import com.example.faculty_app.mainapp.classes.models.shared.Offering;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassRuntime {
    @JsonProperty("class")
    public Cls cls;
    public Course course;
    public Offering offering;
    public SessionRuntime session;
    public ClassRuntime() {}
}
