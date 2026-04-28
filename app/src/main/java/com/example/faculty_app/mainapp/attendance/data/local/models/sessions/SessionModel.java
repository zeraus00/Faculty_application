package com.example.faculty_app.mainapp.attendance.data.local.models.sessions;

import androidx.annotation.NonNull;

public class SessionModel {
    public int id;
    public int classOffering;
    @NonNull
    public String status;
    @NonNull
    public String date;
    public int startTimeMs;
    public int getStartTimeMs;

    public SessionModel(int id,
                        int classOffering,
                        @NonNull String status,
                        @NonNull String date,
                        int startTimeMs,
                        int getStartTimeMs) {
        this.id = id;
        this.classOffering = classOffering;
        this.status = status;
        this.date = date;
        this.startTimeMs = startTimeMs;
        this.getStartTimeMs = getStartTimeMs;
    }
}
