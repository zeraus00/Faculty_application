package com.example.faculty_app.mainapp.attendance.data.local.mappers;

import com.example.faculty_app.mainapp.attendance.data.local.models.sessions.SessionViewModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.sessions.SessionsViewModel;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessions.Session;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessions.Sessions;

import java.util.ArrayList;

public class SessionsMapper {
    public static SessionsViewModel fromApi(Sessions data) {
        ArrayList<SessionViewModel> sessionModels = new ArrayList<>();
        for (Session session : data.sessions) {
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
