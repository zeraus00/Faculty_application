package com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance;

public class StudentModel {
    int enrollmentId;
    String enrollmentStatus;
    int studentId;
    String studentNumber;
    String surname;
    String firstName;
    String middleName;
    String fullName;

    public StudentModel(int enrollmentId,
                        String enrollmentStatus,
                        int studentId,
                        String studentNumber,
                        String surname,
                        String firstName,
                        String middleName,
                        String fullName) {
        this.enrollmentId = enrollmentId;
        this.enrollmentStatus = enrollmentStatus;
        this.studentId = studentId;
        this.studentNumber = studentNumber;
        this.surname = surname;
        this.firstName = firstName;
        this.middleName = middleName;
        this.fullName = fullName;
    }
}
