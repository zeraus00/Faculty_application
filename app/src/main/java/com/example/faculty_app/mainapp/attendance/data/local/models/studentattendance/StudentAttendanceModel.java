package com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance;

import java.util.ArrayList;

public class StudentAttendanceModel {
    public StudentModel student;
    public ArrayList<HistoryItemModel> history;
    public SummaryModel summary;

    public StudentAttendanceModel(StudentModel student,
                                  ArrayList<HistoryItemModel> history,
                                  SummaryModel summary) {
        this.student = student;
        this.history = history;
        this.summary = summary;
    }
}
