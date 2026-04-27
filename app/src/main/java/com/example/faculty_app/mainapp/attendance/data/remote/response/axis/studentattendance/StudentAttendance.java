package com.example.faculty_app.mainapp.attendance.data.remote.response.axis.studentattendance;

import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessionattendance.Enrollment;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessionattendance.Summary;

import java.util.ArrayList;

public class StudentAttendance {
    public Enrollment enrollment;
    public Student student;
    public ArrayList<HistoryItem> history;
    public Summary summary;
}
