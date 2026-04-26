package com.example.faculty_app.mainapp.classes.data.response.classruntime;

import com.example.faculty_app.mainapp.classes.data.response.shared.Cls;
import com.example.faculty_app.mainapp.classes.data.response.shared.Course;
import com.example.faculty_app.mainapp.classes.data.response.shared.Offering;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassRuntime {
    @JsonProperty("class")
    public Cls cls;
    public Course course;
    public Offering offering;
    public SessionRuntime session;
    public ClassRuntime() {}
}
