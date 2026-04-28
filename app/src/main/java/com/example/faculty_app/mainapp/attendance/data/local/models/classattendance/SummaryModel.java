package com.example.faculty_app.mainapp.attendance.data.local.models.classattendance;

public class SummaryModel {
    private int total;
    private int present;
    private int absent;

    public SummaryModel(int total, int present, int absent) {
        this.total = total;
        this.present = present;
        this.absent = absent;
    }

    public int getTotal() {
        return total;
    }

    public int getPresent() {
        return present;
    }

    public int getAbsent() {
        return absent;
    }
}
