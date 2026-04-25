package com.example.faculty_app.mainapp;

public class PresentHistoryModel {

    private final String date;
    private final String description;
    private final String status;

    public PresentHistoryModel(String date, String description, String status) {
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