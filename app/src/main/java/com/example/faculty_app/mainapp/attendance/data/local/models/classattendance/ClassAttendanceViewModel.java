package com.example.faculty_app.mainapp.attendance.data.local.models.classattendance;

import java.util.ArrayList;

public class ClassAttendanceViewModel {
    private ArrayList<AttendanceItemViewModel> attendance;
    private SummaryViewModel summary;

    public ClassAttendanceViewModel(ArrayList<AttendanceItemViewModel> attendance,
                                    SummaryViewModel summary) {
        this.attendance = attendance;
        this.summary = summary;
    }

    public ArrayList<AttendanceItemViewModel> getAttendance() {
        return attendance;
    }

    public SummaryViewModel getSummary() {
        return summary;
    }
}
