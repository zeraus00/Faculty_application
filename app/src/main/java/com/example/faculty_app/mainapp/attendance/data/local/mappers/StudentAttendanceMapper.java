package com.example.faculty_app.mainapp.attendance.data.local.mappers;

import com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance.HistoryItemViewModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance.RecordViewModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance.SessionViewModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance.StudentAttendanceViewModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance.StudentViewModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance.SummaryViewModel;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.studentattendance.HistoryItem;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.studentattendance.Student;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.studentattendance.StudentAttendance;

import java.util.ArrayList;

public class StudentAttendanceMapper {
    public static StudentAttendanceViewModel fromApi(StudentAttendance data) {
        var enrollment = data.enrollment;
        var student = data.student;
        var history = data.history;
        var summary = data.summary;

        StudentViewModel studentViewModel = new StudentViewModel(
                enrollment.id,
                enrollment.status,
                student.id,
                student.studentNumber,
                student.surname,
                student.firstName,
                student.middleName,
                formatName(student)
        );

        ArrayList<HistoryItemViewModel> historyViewModel = fromApiHistory(history);

        SummaryViewModel summaryViewModel = new SummaryViewModel(
                summary.totalSessions,
                summary.present + summary.late,
                summary.absent + summary.missingRecords
        );

        return new StudentAttendanceViewModel(studentViewModel, historyViewModel, summaryViewModel);
    }

    public static StudentAttendanceViewModel fromRepositoryFailure(String failureMessage) {
        StudentViewModel studentViewModel = new StudentViewModel(
                -1,
                "",
                -1,
                "",
                "",
                "",
                "",
                failureMessage
        );

        ArrayList<HistoryItemViewModel> historyViewModel = new ArrayList<>();

        SummaryViewModel summaryViewModel = new SummaryViewModel(0, 0, 0);

        return new StudentAttendanceViewModel(studentViewModel, historyViewModel, summaryViewModel);
    }

    private static ArrayList<HistoryItemViewModel> fromApiHistory(ArrayList<HistoryItem> data) {
        ArrayList<HistoryItemViewModel> historyViewModel = new ArrayList<>();

        for (HistoryItem item : data) {
            var record = item.record;
            var session = item.session;
            historyViewModel.add(new HistoryItemViewModel(
                    new RecordViewModel(
                            record.id,
                            record.status,
                            record.time
                    ),
                    new SessionViewModel(session.id, session.status, session.date)
            ));
        }

        return historyViewModel;
    }

    private static String formatName(Student student) {
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
