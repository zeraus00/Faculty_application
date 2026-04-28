package com.example.faculty_app.mainapp.attendance.data.local.mappers;

import com.example.faculty_app.mainapp.attendance.data.local.models.classattendance.ClassAttendanceViewModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.classattendance.AttendanceItemViewModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.classattendance.SummaryViewModel;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessionattendance.AttendanceItemResponse;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessionattendance.SessionAttendanceResponse;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessionattendance.StudentResponse;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessionattendance.SummaryResponse;

import java.util.ArrayList;
import java.util.Arrays;

public class ClassAttendanceMapper {
    public static ClassAttendanceViewModel fromApi(SessionAttendanceResponse sessionAttendance) {
        ArrayList<AttendanceItemViewModel> attendance = fromAttendanceRecordsResponse(
                sessionAttendance.attendanceRecords);

        SummaryViewModel summary = fromApiSummary(sessionAttendance.summary);

        return new ClassAttendanceViewModel(attendance, summary);
    }

    public static ClassAttendanceViewModel fromRepositoryFailure() {
        return new ClassAttendanceViewModel(new ArrayList<>(), new SummaryViewModel(0, 0, 0));
    }

    public static ClassAttendanceViewModel fromMock() {
        ArrayList<AttendanceItemViewModel> attendanceList = new ArrayList<>(Arrays.asList(
                new AttendanceItemViewModel(1, "Alice Johnson", "211-442", "present", "NONE"),
                new AttendanceItemViewModel(2, "Bob Smith", "211-445", "present", "NO ID"),
                new AttendanceItemViewModel(3, "Charlie Brown", "211-448", "present", "NONE"),
                new AttendanceItemViewModel(4, "Diana Prince", "211-450", "present", "NONE"),
                new AttendanceItemViewModel(
                        5,
                        "Edward Norton",
                        "211-455",
                        "present",
                        "IMPROPER UNIFORM"
                )
                                                                                         ));

        SummaryViewModel summary = new SummaryViewModel(
                attendanceList.size(),
                attendanceList.size(),
                0
        );

        return new ClassAttendanceViewModel(attendanceList, summary);
    }

    private static ArrayList<AttendanceItemViewModel> fromAttendanceRecordsResponse(ArrayList<AttendanceItemResponse> attendance) {
        ArrayList<AttendanceItemViewModel> dto = new ArrayList<>();

        for (AttendanceItemResponse i : attendance) {
            var enrollment = i.enrollment;
            var student = i.student;
            var record = i.record;
            String fullName = formatName(student);
            String studentNumber = student.studentNumber;

            dto.add(new AttendanceItemViewModel(
                    enrollment.id,
                    fullName,
                    studentNumber,
                    record.status,
                    ""
                    // todo: replace this
            ));
        }

        return dto;
    }

    private static SummaryViewModel fromApiSummary(SummaryResponse summary) {
        int total = summary.totalEnrollments;
        int present = summary.present + summary.late;
        int absent = summary.absent + summary.missingRecords;

        return new SummaryViewModel(total, present, absent);
    }

    private static String formatName(StudentResponse student) {
        String surname = student.surname;
        String firstName = student.firstName;
        String middleName = student.middleName;
        String middleInitial = "";

        StringBuilder sb = new StringBuilder();

        for (String word : middleName.trim().split("\\s+")) {
            if (!word.isEmpty()) {
                sb.append(word.charAt(0));
                sb.append(". ");
            }
        }

        middleInitial = sb.toString();

        return surname + ", " + firstName + " " + middleInitial;
    }
}
