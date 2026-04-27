package com.example.faculty_app.mainapp.classes.data.remote.api;

import com.example.faculty_app.mainapp.classes.data.remote.response.classlist.AxisClassList;
import com.example.faculty_app.mainapp.classes.data.remote.response.classruntime.AxisClassRuntime;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface AxisScheduleClient {
    @Headers("X-Inject-Auth: true")
    @GET("/enrollments/schedule/class-list")
    Call<AxisClassList> getClassList();

    @Headers("X-Inject-Auth: true")
    @GET("/enrollments/schedule/current-or-next")
    Call<AxisClassRuntime> getClassRuntime();
}
