package com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance;

public class SessionModel {
    int id;
    String status;
    String date;

    public SessionModel(int id, String status, String date) {
        this.id = id;
        this.status = status;
        this.date = date;
    }
}
