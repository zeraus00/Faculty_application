package com.example.faculty_app.mainapp.attendance.data.local.models.classattendance;

import java.util.ArrayList;

public class ClassAttendanceModel {
    private final ArrayList<AttendanceItemModel> attendance;
    private final SummaryModel summary;

    public ClassAttendanceModel(ArrayList<AttendanceItemModel> attendance, SummaryModel summary) {
        this.attendance = attendance;
        this.summary = summary;
    }

    public ArrayList<AttendanceItemModel> getAttendance() {
        return attendance;
    }

    public SummaryModel getSummary() {
        return summary;
    }
}
