package com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance;

public class SummaryModel {
    public int total;
    public int present;
    public int absent;

    public SummaryModel(int total, int present, int absent) {
        this.total = total;
        this.present = present;
        this.absent = absent;
    }
}
