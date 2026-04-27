package com.example.faculty_app.mainapp.classes.data.local.mappers;

import static com.example.faculty_app.mainapp.classes.data.local.mappers.Shared.formatBuildingAndRoom;
import static com.example.faculty_app.mainapp.classes.data.local.mappers.Shared.formatClassInfo;

import androidx.annotation.NonNull;

import com.example.faculty_app.mainapp.classes.data.local.models.RuntimeDto;
import com.example.faculty_app.mainapp.classes.data.remote.response.shared.Offering;
import com.example.faculty_app.mainapp.classes.data.remote.response.classruntime.ClassRuntime;

public class RuntimeDtoMapper {
    @NonNull
    public static RuntimeDto fromApiRuntime(@NonNull ClassRuntime runtime) {

        var classCode = formatClassInfo(runtime.cls, runtime.course);
        var schedule = formatSchedule(runtime.offering);
        var buildingAndRoom = formatBuildingAndRoom(runtime.offering.room);

        return new RuntimeDto(classCode,
                              runtime.course.name,
                              schedule,
                              buildingAndRoom,
                              runtime.offering.weekDay,
                              runtime.session.runtimeStatus);
    }

    private static String formatSchedule(Offering offering) {
        String scheduleInfo = "";

        if (offering != null) {
            scheduleInfo = offering.startTime + " - " + offering.endTime;
        }

        return scheduleInfo;
    }


}
