package com.example.faculty_app.mainapp.classes.data.api;

import com.example.faculty_app.mainapp.classes.data.response.classlist.ClassListResponse;
import com.example.faculty_app.mainapp.classes.data.response.classruntime.ClassRuntimeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface Client {
    @Headers("X-Inject-Auth: true")
    @GET("/enrollments/schedule/class-list")
    Call<ClassListResponse> getClassList();
    @Headers("X-Inject-Auth: true")
    @GET("/enrollments/schedule/current-or-next")
    Call<ClassRuntimeResponse> getClassRuntime();
}
