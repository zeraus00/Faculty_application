package com.example.faculty_app.mainapp;

public class AbsentHistoryModel {

    private String date;
    private String description;
    private String status;

    public AbsentHistoryModel(String date, String description, String status) {
        this.date = date;
        this.description = description;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}