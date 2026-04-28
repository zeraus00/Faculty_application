package com.example.faculty_app.mainapp.attendance.services;


import com.example.faculty_app.BuildConfig;
import com.example.faculty_app.mainapp.attendance.data.local.mappers.ClassAttendanceMapper;
import com.example.faculty_app.mainapp.attendance.data.local.mappers.SessionsMapper;
import com.example.faculty_app.mainapp.attendance.data.local.mappers.StudentAttendanceMapper;
import com.example.faculty_app.mainapp.attendance.data.local.models.classattendance.ClassAttendanceModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.sessions.SessionsModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance.StudentAttendanceModel;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessionattendance.SessionAttendanceResponse;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessions.SessionsResponse;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.studentattendance.StudentAttendance;
import com.example.faculty_app.mainapp.attendance.data.repositories.AttendanceRepository;
import com.example.faculty_app.mainapp.attendance.domain.AttendanceException;
import com.example.faculty_app.mainapp.attendance.domain.AttendanceExceptionCode;
import com.example.faculty_app.shared.RepositoryCallback;
import com.example.faculty_app.shared.RepositoryResult;
import com.example.faculty_app.shared.ServiceCallback;
import com.example.faculty_app.shared.ServiceResult;

public class AttendanceService {
    public static void getClassSessions(int classId, ServiceCallback<SessionsModel> callback) {
        AttendanceRepository.fetchSessions(
                classId, new RepositoryCallback<SessionsResponse>() {
                    @Override
                    public void onResult(RepositoryResult<SessionsResponse> result) {
                        if (result instanceof RepositoryResult.Success) {
                            var data =
                                    ((RepositoryResult.Success<SessionsResponse>) result).getData();
                            callback.onResult(new ServiceResult<>(
                                    true,
                                    SessionsMapper.fromApi(data),
                                    "Success loading sessions."
                            ));
                        }
                        else if (result instanceof RepositoryResult.Fail) {
                            var exception =
                                    ((RepositoryResult.Fail<?, AttendanceException>) result).getException();
                            var code = exception.getCode();

                            if (code == AttendanceExceptionCode.API_DISCONNECTED &&
                                    BuildConfig.USE_MOCK_AUTH) {
                                //  todo: mock here
                            }

                            var message = exception.getMessage();

                            callback.onResult(new ServiceResult<>(
                                    false,
                                    SessionsMapper.fromRepositoryFailure(),
                                    message,
                                    exception.getCause()
                            ));

                        }
                    }
                }
                                          );
    }

    public static void getClassAttendance(int classSessionId,
                                          ServiceCallback<ClassAttendanceModel> callback) {
        AttendanceRepository.fetchSessionAttendance(
                classSessionId, new RepositoryCallback<SessionAttendanceResponse>() {
                    @Override
                    public void onResult(RepositoryResult<SessionAttendanceResponse> result) {
                        if (result instanceof RepositoryResult.Success) {
                            var data =
                                    ((RepositoryResult.Success<SessionAttendanceResponse>) result).getData();
                            callback.onResult(new ServiceResult<>(
                                    true,
                                    ClassAttendanceMapper.fromApi(data),
                                    "Success retrieving " + "attendance."
                            ));
                        }
                        else if (result instanceof RepositoryResult.Fail) {
                            var exception =
                                    ((RepositoryResult.Fail<?, AttendanceException>) result).getException();
                            var code = exception.getCode();

                            if (code == AttendanceExceptionCode.API_DISCONNECTED &&
                                    BuildConfig.USE_MOCK_AUTH) {
                                callback.onResult(new ServiceResult<>(
                                        true,
                                        ClassAttendanceMapper.fromMock(),
                                        "Success mocking class attendance."
                                ));
                            }

                            var message = exception.getMessage();

                            callback.onResult(new ServiceResult<>(
                                    false,
                                    ClassAttendanceMapper.fromRepositoryFailure(),
                                    message,
                                    exception.getCause()
                            ));
                        }
                    }
                }
                                                   );
    }

    public static void getStudentAttendance(int enrollmentId,
                                            ServiceCallback<StudentAttendanceModel> callback) {
        AttendanceRepository.fetchStudentAttendance(
                enrollmentId, new RepositoryCallback<StudentAttendance>() {
                    @Override
                    public void onResult(RepositoryResult<StudentAttendance> result) {
                        if (result instanceof RepositoryResult.Success) {
                            var data =
                                    ((RepositoryResult.Success<StudentAttendance>) result).getData();

                            callback.onResult(new ServiceResult<>(
                                    true,
                                    StudentAttendanceMapper.fromApi(data),
                                    "Success retrieving student attendance"
                            ));
                        }
                        else if (result instanceof RepositoryResult.Fail) {
                            var exception =
                                    ((RepositoryResult.Fail<?, AttendanceException>) result).getException();
                            var code = exception.getCode();

                            if (code == AttendanceExceptionCode.API_DISCONNECTED &&
                                    BuildConfig.USE_MOCK_AUTH) {
                                //  todo: mock here
                            }

                            var message = exception.getMessage();

                            callback.onResult(new ServiceResult<>(
                                    false,
                                    StudentAttendanceMapper.fromRepositoryFailure(code ==
                                                                                          AttendanceExceptionCode.ATTENDANCE_FORBIDDEN_EXCEPTION ?
                                                                                  "You are not " +
                                                                                          "allowed to access this resource" :
                                                                                  "Something went" +
                                                                                          " wrong"),
                                    message,
                                    exception.getCause()
                            ));
                        }
                    }
                }
                                                   );
    }
}
