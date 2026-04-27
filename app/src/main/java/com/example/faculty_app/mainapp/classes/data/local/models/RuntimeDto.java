package com.example.faculty_app.mainapp.classes.data.local.models;

import androidx.annotation.Nullable;

public class RuntimeDto {

    public String code, name, time, room, weekDay;

    public RuntimeDto(String c, String n, String t, String r, String weekDay) {
        this.code = c;
        this.name = n;
        this.time = t;
        this.room = r;
        this.weekDay = weekDay;
    }
}
