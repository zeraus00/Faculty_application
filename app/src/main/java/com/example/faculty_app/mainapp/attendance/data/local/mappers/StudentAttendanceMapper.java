package com.example.faculty_app.mainapp.attendance.data.local.mappers;

import com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance.HistoryItemModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance.RecordModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance.SessionModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance.StudentAttendanceModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance.StudentModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance.SummaryModel;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.studentattendance.HistoryItemResponse;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.studentattendance.StudentResponse;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.studentattendance.StudentAttendance;

import java.util.ArrayList;

public class StudentAttendanceMapper {
    public static StudentAttendanceModel fromApi(StudentAttendance data) {
        var enrollment = data.enrollment;
        var student = data.student;
        var history = data.history;
        var summary = data.summary;

        StudentModel studentViewModel = new StudentModel(
                enrollment.id,
                enrollment.status,
                student.id,
                student.studentNumber,
                student.surname,
                student.firstName,
                student.middleName,
                formatName(student)
        );

        ArrayList<HistoryItemModel> historyViewModel = fromApiHistory(history);

        SummaryModel summaryViewModel = new SummaryModel(
                summary.totalSessions,
                summary.present + summary.late,
                summary.absent + summary.missingRecords
        );

        return new StudentAttendanceModel(studentViewModel, historyViewModel, summaryViewModel);
    }

    public static StudentAttendanceModel fromRepositoryFailure(String failureMessage) {
        StudentModel studentViewModel = new StudentModel(
                -1,
                "",
                -1,
                "",
                "",
                "",
                "",
                failureMessage
        );

        ArrayList<HistoryItemModel> historyViewModel = new ArrayList<>();

        SummaryModel summaryViewModel = new SummaryModel(0, 0, 0);

        return new StudentAttendanceModel(studentViewModel, historyViewModel, summaryViewModel);
    }

    private static ArrayList<HistoryItemModel> fromApiHistory(ArrayList<HistoryItemResponse> data) {
        ArrayList<HistoryItemModel> historyViewModel = new ArrayList<>();

        for (HistoryItemResponse item : data) {
            var record = item.record;
            var session = item.session;
            historyViewModel.add(new HistoryItemModel(
                    new RecordModel(
                            record.id,
                            record.status,
                            record.time
                    ),
                    new SessionModel(session.id, session.status, session.date)
            ));
        }

        return historyViewModel;
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
