package com.example.faculty_app.mainapp.attendance.data.local.mappers;

import com.example.faculty_app.mainapp.attendance.data.local.models.classattendance.ClassAttendanceModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.classattendance.AttendanceItemModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.classattendance.SummaryModel;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessionattendance.AttendanceItemResponse;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessionattendance.SessionAttendanceResponse;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessionattendance.StudentResponse;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessionattendance.SummaryResponse;

import java.util.ArrayList;
import java.util.Arrays;

public class ClassAttendanceMapper {
    public static ClassAttendanceModel fromApi(SessionAttendanceResponse sessionAttendance) {
        ArrayList<AttendanceItemModel> attendance =
                fromAttendanceRecordsResponse(sessionAttendance.attendanceRecords);

        SummaryModel summary = fromApiSummary(sessionAttendance.summary);

        return new ClassAttendanceModel(attendance, summary);
    }

    public static ClassAttendanceModel fromRepositoryFailure() {
        return new ClassAttendanceModel(new ArrayList<>(), new SummaryModel(0, 0, 0));
    }

    public static ClassAttendanceModel fromMock() {
        ArrayList<AttendanceItemModel> attendanceList = new ArrayList<>(Arrays.asList(
                new AttendanceItemModel(1, "Alice Johnson", "211-442", "present", "NONE"),
                new AttendanceItemModel(2, "Bob Smith", "211-445", "present", "NO ID"),
                new AttendanceItemModel(3, "Charlie Brown", "211-448", "present", "NONE"),
                new AttendanceItemModel(4, "Diana Prince", "211-450", "present", "NONE"),
                new AttendanceItemModel(
                        5,
                        "Edward Norton",
                        "211-455",
                        "present",
                        "IMPROPER UNIFORM"
                )
                                                                                     ));

        SummaryModel summary = new SummaryModel(attendanceList.size(), attendanceList.size(), 0);

        return new ClassAttendanceModel(attendanceList, summary);
    }

    private static ArrayList<AttendanceItemModel> fromAttendanceRecordsResponse(ArrayList<AttendanceItemResponse> attendance) {
        ArrayList<AttendanceItemModel> dto = new ArrayList<>();

        for (AttendanceItemResponse i : attendance) {
            var enrollment = i.enrollment;
            var student = i.student;
            var record = i.record;
            String fullName = formatName(student);
            String studentNumber = student.studentNumber;

            dto.add(new AttendanceItemModel(
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

    private static SummaryModel fromApiSummary(SummaryResponse summary) {
        int total = summary.totalEnrollments;
        int present = summary.present + summary.late;
        int absent = summary.absent + summary.missingRecords;

        return new SummaryModel(total, present, absent);
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
