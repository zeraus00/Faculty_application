package com.example.faculty_app.mainapp.classes.data.local.mappers;

import com.example.faculty_app.mainapp.classes.data.remote.response.classlist.ClassList;
import com.example.faculty_app.mainapp.classes.data.remote.response.classlist.ClassListElement;
import com.example.faculty_app.mainapp.classes.data.remote.response.classlist.Offering;
import com.example.faculty_app.mainapp.classes.data.remote.response.shared.Cls;
import com.example.faculty_app.mainapp.classes.data.remote.response.shared.Course;
import com.example.faculty_app.mainapp.classes.data.remote.response.shared.Room;
import com.example.faculty_app.mainapp.classes.data.local.models.ClassDto;

import java.util.ArrayList;

public class ClassDtoMapper {
    public static ArrayList<ClassDto> fromApiClassList(ClassList classList) {
        ArrayList<ClassDto> dto = new ArrayList<ClassDto>();

        for (ClassListElement element : classList.classes) {
            String classInfo = formatClassInfo(element.cls, element.course);
            String courseName = element.course.name;

            var offering = element.offering;
            String schedule = formatSchedule(offering);
            String weekDay = offering != null ? offering.weekDay : "";
            Integer weekDayNumeric = offering == null ? null : offering.weekDayNumeric;

            dto.add(new ClassDto(classInfo, courseName, schedule, weekDay, weekDayNumeric));
        }

        return dto;
    }

    private static String formatClassInfo(Cls cls, Course course) {
        var classNumber = "#" + cls.classNumber;
        var courseCode = course.code;
        return classNumber + " · " + courseCode;
    }

    private static String formatSchedule(Offering offering) {
        String scheduleInfo = "";

        if (offering != null) {
            scheduleInfo = offering.startTime + " - " + offering.endTime;


            scheduleInfo += " | " + formatBuildingAndRoom(offering.room);
        }

        return scheduleInfo;
    }

    private static String formatBuildingAndRoom(Room room) {
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
