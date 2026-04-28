package com.example.faculty_app.mainapp.attendance.data.local.mappers;

import com.example.faculty_app.mainapp.attendance.data.local.models.sessions.SessionViewModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.sessions.SessionsViewModel;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessions.SessionResponse;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessions.SessionsResponse;

import java.util.ArrayList;

public class SessionsMapper {
    public static SessionsViewModel fromApi(SessionsResponse data) {
        ArrayList<SessionViewModel> sessionModels = new ArrayList<>();
        for (SessionResponse session : data.sessions) {
            sessionModels.add(new SessionViewModel(
                    session.id,
                    session.classOfferingId,
                    session.status,
                    session.datePh,
                    session.startTimeMs,
                    session.endTimeMs
            ));
        }

        return new SessionsViewModel(sessionModels);
    }

    public static SessionsViewModel fromRepositoryFailure() {
        return new SessionsViewModel(new ArrayList<>());
    }
}
