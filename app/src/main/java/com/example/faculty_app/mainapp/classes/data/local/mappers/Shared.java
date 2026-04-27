package com.example.faculty_app.mainapp.classes.data.local.mappers;

import com.example.faculty_app.mainapp.classes.data.remote.response.classlist.Offering;
import com.example.faculty_app.mainapp.classes.data.remote.response.shared.Cls;
import com.example.faculty_app.mainapp.classes.data.remote.response.shared.Course;
import com.example.faculty_app.mainapp.classes.data.remote.response.shared.Room;

public class Shared {
    public static String formatClassInfo(Cls cls, Course course) {
        var classNumber = "#" + cls.classNumber;
        var courseCode = course.code;
        return classNumber + " · " + courseCode;
    }

    public static String formatBuildingAndRoom(Room room) {
        String roomDetails = "N/A";

        if (room != null) {
            roomDetails = room.name;
            String building = room.building;
            if (building != null) {
                StringBuilder sb = new StringBuilder();

                for (String word : building.trim().split("\\s+")) {
                    if (!word.isEmpty()) {
                        sb.append(word.charAt(0));
                    }
                }

                roomDetails = sb + " " + roomDetails;
            }
        }

        return roomDetails;
    }
}
