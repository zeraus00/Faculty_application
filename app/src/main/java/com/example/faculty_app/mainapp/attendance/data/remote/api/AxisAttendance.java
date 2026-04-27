package com.example.faculty_app.mainapp.attendance.data.remote.api;

import static com.example.faculty_app.core.api.axis.Axis.axisCallback;

import com.example.faculty_app.core.api.axis.Axis;
import com.example.faculty_app.core.api.axis.dto.AxisCallback;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessionattendance.SessionAttendance;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessions.Sessions;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.studentattendance.StudentAttendance;

public class AxisAttendance {
    private static AxisAttendanceClient client;

    public static void getSessions(int classId, AxisCallback<Sessions> callback) {
        getClient().getSessions(classId).enqueue(axisCallback(callback));
    }

    public static void getSessionAttendance(int classSessionId,
                                            AxisCallback<SessionAttendance> callback) {
        getClient().getSessionAttendance(classSessionId).enqueue(axisCallback(callback));
    }

    public static void getStudentAttendance(int enrollmentId,
                                            AxisCallback<StudentAttendance> callback) {
        getClient().getStudentAttendance(enrollmentId).enqueue(axisCallback(callback));
    }

    public static AxisAttendanceClient getClient() {
        if (client == null)
            client = Axis.createService(AxisAttendanceClient.class);
        return client;
    }
}
