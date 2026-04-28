package com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance;

public class HistoryItemModel {
    public RecordModel record;
    public SessionModel session;

    public HistoryItemModel(RecordModel record, SessionModel session) {
        this.record = record;
        this.session = session;
    }
}
