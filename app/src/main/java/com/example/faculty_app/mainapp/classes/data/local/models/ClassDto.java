package com.example.faculty_app.mainapp.classes.data.local.models;

import androidx.annotation.Nullable;

public class ClassDto {
    public final int id;
    public final String code, name, details, weekDay;
    @Nullable
    public final Integer weekDayNumeric;

    public ClassDto(int id,
                    String c,
                    String n,
                    String d,
                    String weekDay,
                    @Nullable Integer weekDayNumeric) {
        this.id = id;
        this.code = c;
        this.name = n;
        this.details = d;
        this.weekDay = weekDay;
        this.weekDayNumeric = weekDayNumeric;
    }
}
