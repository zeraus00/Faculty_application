package com.example.faculty_app.mainapp.attendance.data.local.models.classattendance;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.faculty_app.mainapp.attendance.data.local.models.sessions.SessionModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.sessions.SessionsModel;

import java.util.ArrayList;
import java.util.List;

public class ClassAttendanceViewModel extends ViewModel {

    private final MutableLiveData<ClassAttendanceModel> classAttendance =
            new MutableLiveData<>(new ClassAttendanceModel(new ArrayList<>(),
            new SummaryModel(0, 0, 0)
    ));
    private final MutableLiveData<SessionsModel> sessions = new MutableLiveData<>(new SessionsModel(
            new ArrayList<>()));

    public void setClassAttendance(@NonNull ClassAttendanceModel classAttendance) {
        this.classAttendance.setValue(classAttendance);
    }

    public void setSessions(@NonNull SessionsModel sessions) {
        this.sessions.setValue(sessions);
    }

    public MutableLiveData<ClassAttendanceModel> getClassAttendance() {
        return classAttendance;
    }

    public MutableLiveData<SessionsModel> getSessions() {
        return sessions;
    }
}
