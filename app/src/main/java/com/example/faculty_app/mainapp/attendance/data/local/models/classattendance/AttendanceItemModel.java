package com.example.faculty_app.mainapp.attendance.data.local.models.classattendance;

import androidx.annotation.NonNull;

public class AttendanceItemModel {
    private int enrollmentId;
    @NonNull
    private String name;
    @NonNull
    private String studentNumber;
    @NonNull
    private String status;
    @NonNull
    private String violation;

    public AttendanceItemModel(int enrollmentId,
                               @NonNull String name,
                               @NonNull String studentNumber,
                               @NonNull String status,
                               @NonNull String violation) {
        this.enrollmentId = enrollmentId;
        this.name = name;
        this.studentNumber = studentNumber;
        this.status = status;
        this.violation = violation;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getStudentNumber() {
        return studentNumber;
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    @NonNull
    public String getViolation() {
        return violation;
    }

    public void setStatus(@NonNull String status) {
        this.status = status;
    }

    public void setViolation(@NonNull String violation) {
        this.violation = violation;
    }
}
