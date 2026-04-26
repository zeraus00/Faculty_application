package com.example.faculty_app.mainapp.classes.data.api;

import static com.example.faculty_app.core.api.rvaucms.RvaucMsService.rvaucMsCallback;

import com.example.faculty_app.core.api.rvaucms.RvaucMsService;
import com.example.faculty_app.core.api.rvaucms.dto.HttpCallback;
import com.example.faculty_app.mainapp.classes.data.response.classlist.ClassListResponse;
import com.example.faculty_app.mainapp.classes.data.response.classruntime.ClassRuntimeResponse;

public class ScheduleApi {
    private static Client client;
    public static void getClassList(HttpCallback<ClassListResponse> callback) {
        getClient().getClassList().enqueue(rvaucMsCallback(callback));
    }
    public static void getClassRuntime(HttpCallback<ClassRuntimeResponse> callback) {
        getClient().getClassRuntime().enqueue(rvaucMsCallback(callback));
    }
    public static Client getClient() {
        if (client == null) client = RvaucMsService.createService(Client.class);
        return client;
    }
}
