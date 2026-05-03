package com.example.faculty_app.mainapp.classes.data.local.mappers;

import static com.example.faculty_app.mainapp.classes.data.local.mappers.Shared.formatBuildingAndRoom;
import static com.example.faculty_app.mainapp.classes.data.local.mappers.Shared.formatClassInfo;

import com.example.faculty_app.mainapp.classes.data.remote.response.classlist.ClassList;
import com.example.faculty_app.mainapp.classes.data.remote.response.classlist.ClassListElement;
import com.example.faculty_app.mainapp.classes.data.remote.response.classlist.Offering;
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

            dto.add(new ClassDto(
                    element.cls.id,
                    classInfo,
                    courseName,
                    schedule,
                    weekDay,
                    weekDayNumeric
            ));
        }

        return dto;
    }

    private static String formatSchedule(Offering offering) {
        String scheduleInfo = "";

        if (offering != null) {
            scheduleInfo = offering.startTime + " - " + offering.endTime;


            scheduleInfo += " | " + formatBuildingAndRoom(offering.room);
        }

        return scheduleInfo;
    }

}
