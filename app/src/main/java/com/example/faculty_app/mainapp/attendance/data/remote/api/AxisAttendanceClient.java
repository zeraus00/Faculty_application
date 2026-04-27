package com.example.faculty_app.mainapp.attendance.data.remote.api;

import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessionattendance.AxisSessionAttendance;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessions.AxisSessions;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.studentattendance.AxisStudentAttendance;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface AxisAttendanceClient {
    @Headers("X-Inject-Auth: true")
    @GET("/enrollments/attendance/class/{classId}/sessions")
    Call<AxisSessions> getSessions(@Path("classId") int classId);

    @Headers("X-Inject-Auth: true")
    @GET("/enrollments/attendance/class/offering/session/{classSessionId}")
    Call<AxisSessionAttendance> getSessionAttendance(@Path("classSessionId") int classSessionId);

    @Headers("X-Inject-Auth: true")
    @GET("/enrollments/attendance/records/enrollment/:enrollmentId")
    Call<AxisStudentAttendance> getStudentAttendance(@Path("enrollmentId") int enrollmentId);
}
