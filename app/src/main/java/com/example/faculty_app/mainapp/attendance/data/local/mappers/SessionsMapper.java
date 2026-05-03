package com.example.faculty_app.mainapp.attendance.data.local.mappers;

import com.example.faculty_app.mainapp.attendance.data.local.models.sessions.SessionModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.sessions.SessionsModel;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessions.SessionResponse;
import com.example.faculty_app.mainapp.attendance.data.remote.response.axis.sessions.SessionsResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SessionsMapper {
    public static SessionsModel fromApi(SessionsResponse data) {
        ArrayList<SessionModel> sessionModels = new ArrayList<>();
        for (SessionResponse session : data.sessions) {
            sessionModels.add(new SessionModel(
                    session.id,
                    session.classOfferingId,
                    session.status,
                    session.datePh,
                    session.startTimeMs,
                    session.endTimeMs
            ));
        }

        return new SessionsModel(sessionModels);
    }

    public static SessionsModel fromRepositoryFailure() {
        return new SessionsModel(new ArrayList<>(List.of(new SessionModel(
                -1,
                -1,
                "Something went wrong.",
                "",
                0,
                0
        ))));
    }
}
