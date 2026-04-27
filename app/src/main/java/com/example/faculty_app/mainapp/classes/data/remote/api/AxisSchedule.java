package com.example.faculty_app.mainapp.classes.data.remote.api;

import static com.example.faculty_app.core.api.axis.Axis.axisCallback;

import com.example.faculty_app.core.api.axis.Axis;
import com.example.faculty_app.core.api.axis.dto.AxisCallback;
import com.example.faculty_app.mainapp.classes.data.remote.response.classlist.ClassList;
import com.example.faculty_app.mainapp.classes.data.remote.response.classruntime.ClassRuntime;

public class AxisSchedule {
    private static AxisScheduleClient client;

    public static void getClassList(AxisCallback<ClassList> callback) {
        getClient().getClassList().enqueue(axisCallback(callback));
    }

    public static void getClassRuntime(AxisCallback<ClassRuntime> callback) {
        getClient().getClassRuntime().enqueue(axisCallback(callback));
    }

    public static AxisScheduleClient getClient() {
        if (client == null)
            client = Axis.createService(AxisScheduleClient.class);
        return client;
    }
}
