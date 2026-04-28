package com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance;

import androidx.annotation.Nullable;

public class RecordModel {
    @Nullable
    Integer id;
    String status;
    String time;

    public RecordModel(@Nullable Integer id, String status, String time) {
        this.id = id;
        this.status = status;
        this.time = time;
    }
}
