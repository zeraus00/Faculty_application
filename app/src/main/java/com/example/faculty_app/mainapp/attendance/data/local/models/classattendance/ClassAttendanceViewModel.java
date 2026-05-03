package com.example.faculty_app.mainapp.attendance.data.local.models.classattendance;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.faculty_app.mainapp.attendance.data.local.models.sessions.SessionsModel;

public class ClassAttendanceViewModel extends ViewModel {

    private MutableLiveData<ClassAttendanceModel> classAttendance;
    private MutableLiveData<SessionsModel> sessions;

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
