package com.example.faculty_app.mainapp.attendance.data.remote.response.axis.studentattendance;

import java.util.ArrayList;

public class StudentAttendance {
    public EnrollmentResponse enrollment;
    public StudentResponse student;
    public ArrayList<HistoryItemResponse> history;
    public SummaryResponse summary;
}
