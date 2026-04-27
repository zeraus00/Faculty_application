package com.example.faculty_app.mainapp.classes.data.remote.api;

import static com.example.faculty_app.core.api.axis.Axis.rvaucMsCallback;

import com.example.faculty_app.core.api.axis.Axis;
import com.example.faculty_app.core.api.axis.dto.HttpCallback;
import com.example.faculty_app.mainapp.classes.data.remote.response.classlist.ClassListResponse;
import com.example.faculty_app.mainapp.classes.data.remote.response.classruntime.ClassRuntimeResponse;

public class ScheduleApi {
    private static Client client;

    public static void getClassList(HttpCallback<ClassListResponse> callback) {
        getClient().getClassList().enqueue(rvaucMsCallback(callback));
    }

    public static void getClassRuntime(HttpCallback<ClassRuntimeResponse> callback) {
        getClient().getClassRuntime().enqueue(rvaucMsCallback(callback));
    }

    public static Client getClient() {
        if (client == null)
            client = Axis.createService(Client.class);
        return client;
    }
}
