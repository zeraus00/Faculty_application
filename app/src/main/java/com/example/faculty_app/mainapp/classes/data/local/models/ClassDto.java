package com.example.faculty_app.mainapp.classes.data.local.models;

import androidx.annotation.Nullable;

public class ClassDto {
    public String code, name, details, weekDay;
    @Nullable
    public Integer weekDayNumeric;

    public ClassDto(String c,
                    String n,
                    String d,
                    String weekDay,
                    @Nullable Integer weekDayNumeric) {
        this.code = c;
        this.name = n;
        this.details = d;
        this.weekDay = weekDay;
        this.weekDayNumeric = weekDayNumeric;
    }
}
