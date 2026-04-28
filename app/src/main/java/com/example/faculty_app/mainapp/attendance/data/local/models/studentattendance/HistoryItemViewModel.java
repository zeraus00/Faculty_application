package com.example.faculty_app.mainapp.attendance.data.local.models.studentattendance;

public class HistoryItemViewModel {
    public RecordViewModel record;
    public SessionViewModel session;

    public HistoryItemViewModel(RecordViewModel record, SessionViewModel session) {
        this.record = record;
        this.session = session;
    }
}
