package com.example.faculty_app.mainapp.attendance.data.local.models.classattendance;

import androidx.annotation.NonNull;

public class AttendanceItemViewModel {
    public int enrollmentId;
    @NonNull
    public String name;
    @NonNull
    public String studentNumber;
    @NonNull
    public String status;

    public AttendanceItemViewModel(int enrollmentId,
                                   @NonNull String name,
                                   @NonNull String studentNumber,
                                   @NonNull String status) {
        this.enrollmentId = enrollmentId;
        this.name = name;
        this.studentNumber = studentNumber;
        this.status = status;
    }
}
