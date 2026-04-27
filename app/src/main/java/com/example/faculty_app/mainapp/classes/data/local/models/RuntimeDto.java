package com.example.faculty_app.mainapp.classes.data.local.models;

import androidx.annotation.Nullable;

public class RuntimeDto {

    public String code, name, time, room, weekDay, runtimeStatus;

    public RuntimeDto(String c,
                      String n,
                      String t,
                      String r,
                      String weekDay,
                      String runtimeStatus) {
        this.code = c;
        this.name = n;
        this.time = t;
        this.room = r;
        this.weekDay = weekDay;
        this.runtimeStatus = runtimeStatus;
    }
}
