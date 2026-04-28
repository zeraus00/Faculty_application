package com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance;

import java.util.ArrayList;

public class StudentAttendanceViewModel {
    public StudentViewModel student;
    public ArrayList<HistoryItemViewModel> history;
    public SummaryViewModel summary;

    public StudentAttendanceViewModel(StudentViewModel student,
                                      ArrayList<HistoryItemViewModel> history,
                                      SummaryViewModel summary) {
        this.student = student;
        this.history = history;
        this.summary = summary;
    }
}
