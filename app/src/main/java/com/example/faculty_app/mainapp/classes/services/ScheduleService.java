package com.example.faculty_app.mainapp.classes.services;

import com.example.faculty_app.core.api.axis.dto.HttpCallback;
import com.example.faculty_app.mainapp.classes.data.api.ScheduleApi;
import com.example.faculty_app.mainapp.classes.data.response.classlist.ClassListResponse;
import com.example.faculty_app.mainapp.classes.domain.models.ClassListCallback;

public class ScheduleService {

    public static void fetchClassList(ClassListCallback callback) {
        ScheduleApi.getClassList(new HttpCallback<ClassListResponse>() {
            @Override
            public void onSuccess(ClassListResponse response) {
                if (response.success && response.result != null) {
                    callback.onSuccess(response.result);
                    return;
                }

                var message = response.result == null ?
                              "Received null." :
                              response.message != null && !response.message.isBlank() ?
                              response.message :
                              "Failed fetching classes.";
                callback.onFail(message);
            }

            @Override
            public void onError(String message) {

                var finalMessage = message != null && !message.isBlank() ?
                                   message :
                                   "Failed fetching classes.";
                callback.onFail(finalMessage);
            }
        });
    }

}
