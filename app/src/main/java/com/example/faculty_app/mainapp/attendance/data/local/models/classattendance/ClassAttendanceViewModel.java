package com.example.faculty_app.mainapp.attendance.data.local.models.classattendance;

import java.util.ArrayList;

public class ClassAttendanceViewModel {
    public ArrayList<AttendanceItemViewModel> attendance;
    public SummaryViewModel summary;

    public ClassAttendanceViewModel(ArrayList<AttendanceItemViewModel> attendance,
                                    SummaryViewModel summary) {
        this.attendance = attendance;
        this.summary = summary;
    }
}
