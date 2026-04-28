package com.example.faculty_app.mainapp.attendance.data.local.models.classattendance;

public class SummaryViewModel {
    public int total;
    public int present;
    public int absent;

    public SummaryViewModel(int total, int present, int absent) {
        this.total = total;
        this.present = present;
        this.absent = absent;
    }
}
