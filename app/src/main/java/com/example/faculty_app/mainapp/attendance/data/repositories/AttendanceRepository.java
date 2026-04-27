package com.example.faculty_app.mainapp.attendance.data.repositories;

import com.example.faculty_app.core.api.axis.dto.AxisCallback;
import com.example.faculty_app.core.api.axis.dto.response.AxisResult;
import com.example.faculty_app.mainapp.attendance.data.remote.api.AxisAttendance;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessionattendance.SessionAttendance;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessions.Sessions;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.studentattendance.StudentAttendance;
import com.example.faculty_app.mainapp.attendance.domain.AttendanceException;
import com.example.faculty_app.mainapp.attendance.domain.AttendanceExceptionCode;
import com.example.faculty_app.shared.RepositoryCallback;
import com.example.faculty_app.shared.RepositoryResult;

public class AttendanceRepository {
    public static void fetchSessions(int classId, RepositoryCallback<Sessions> callback) {
        AxisAttendance.getSessions(classId, new AxisCallback<Sessions>() {
            @Override
            public void onResult(AxisResult<Sessions> result) {
                if (result instanceof AxisResult.Success) {
                    var data = ((AxisResult.Success<Sessions>) result).getData();

                    callback.onResult(new RepositoryResult.Success<>(data));
                }
                else if (result instanceof AxisResult.Fail) {
                    var fail = (AxisResult.Fail<Sessions>) result;
                    var code = fail.code;
                    callback.onResult(new RepositoryResult.Fail<>(new AttendanceException(
                            code == null ?
                            AttendanceExceptionCode.API_DISCONNECTED :
                            AttendanceExceptionCode.API_EXCEPTION, fail.message, fail.throwable)));
                }
            }
        });
    }

    public static void fetchSessionAttendance(int classSessionId,
                                              RepositoryCallback<SessionAttendance> callback) {
        AxisAttendance.getSessionAttendance(classSessionId, new AxisCallback<SessionAttendance>() {
            @Override
            public void onResult(AxisResult<SessionAttendance> result) {
                if (result instanceof AxisResult.Success) {
                    var data = ((AxisResult.Success<SessionAttendance>) result).getData();

                    callback.onResult(new RepositoryResult.Success<>(data));
                }
                else if (result instanceof AxisResult.Fail) {
                    var fail = (AxisResult.Fail<SessionAttendance>) result;
                    var code = fail.code;
                    callback.onResult(new RepositoryResult.Fail<>(new AttendanceException(
                            code == null ?
                            AttendanceExceptionCode.API_DISCONNECTED :
                            code == 403 ?
                            AttendanceExceptionCode.ATTENDANCE_FORBIDDEN_EXCEPTION :
                            AttendanceExceptionCode.API_EXCEPTION, fail.message, fail.throwable)));
                }
            }
        });
    }

    public static void fetchStudentAttendance(int enrollmentId,
                                              RepositoryCallback<StudentAttendance> callback) {
        AxisAttendance.getStudentAttendance(enrollmentId, new AxisCallback<StudentAttendance>() {
            @Override
            public void onResult(AxisResult<StudentAttendance> result) {
                if (result instanceof AxisResult.Success) {
                    var data = ((AxisResult.Success<StudentAttendance>) result).getData();

                    callback.onResult(new RepositoryResult.Success<>(data));
                }
                else if (result instanceof AxisResult.Fail) {
                    var fail = (AxisResult.Fail<StudentAttendance>) result;
                    var code = fail.code;
                    callback.onResult(new RepositoryResult.Fail<>(new AttendanceException(
                            code == null ?
                            AttendanceExceptionCode.API_DISCONNECTED :
                            code == 403 ?
                            AttendanceExceptionCode.ATTENDANCE_FORBIDDEN_EXCEPTION :
                            AttendanceExceptionCode.API_EXCEPTION, fail.message, fail.throwable)));
                }
            }
        });
    }
}
