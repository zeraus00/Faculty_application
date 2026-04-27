package com.example.faculty_app.mainapp.classes.data.remote.response.classlist;

import androidx.annotation.Nullable;

import com.example.faculty_app.mainapp.classes.data.remote.response.shared.Room;

public class Offering {
    public String weekDay;
    public int weekDayNumeric;
    public String startTime;
    public String endTime;
    @Nullable
    public Room room;

    public Offering() {
    }
}
