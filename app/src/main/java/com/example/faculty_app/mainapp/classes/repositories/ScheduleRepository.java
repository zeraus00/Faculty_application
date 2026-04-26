package com.example.faculty_app.mainapp.classes.repositories;

import static com.example.faculty_app.mainapp.classes.domain.mappers.ClassDtoMapper.fromApiClassList;

import com.example.faculty_app.BuildConfig;
import com.example.faculty_app.mainapp.classes.data.response.classlist.ClassList;
import com.example.faculty_app.mainapp.classes.domain.models.ClassDto;
import com.example.faculty_app.mainapp.classes.services.ScheduleService;
import com.example.faculty_app.mainapp.classes.services.ScheduleServiceCallback;

import java.time.LocalDate;
import java.util.ArrayList;

public class ScheduleRepository {
    public static void fetchClassDtoList(ClassDtoListCallback callback) {
        ScheduleService.fetchClassList(new ScheduleServiceCallback() {
            @Override
            public void onSuccess(ClassList classList) {
                callback.onSuccess(fromApiClassList(classList));
            }

            @Override
            public void onFail(String message) {
                if (BuildConfig.USE_MOCK_AUTH) {
                    callback.onSuccess(getMockClasses());
                }
                callback.onFail(message);
            }
        });
    }

    private static ArrayList<ClassDto> getMockClasses() {
        ArrayList<ClassDto> mockClasses = new ArrayList<>();

        mockClasses.add(new ClassDto("#606 · DS-3202",
                                     "Machine Learning",
                                     "7:00 AM - 9:00 AM | AV 308b",
                                     "Mon",
                                     1));
        mockClasses.add(new ClassDto("#607 · CS-301",
                                     "Automata Theory",
                                     "10:00 AM - 12:00 PM | RM 402",
                                     "Mon",
                                     1));
        mockClasses.add(new ClassDto("#608 · OS-101",
                                     "Operating Systems",
                                     "1:00 PM - 3:00 PM | LB 204",
                                     "Mon",
                                     1));
        mockClasses.add(new ClassDto("#609 · DS-3203",
                                     "Data Science",
                                     "3:00 PM - 5:00 PM | AV 308b",
                                     "Mon",
                                     1));
        mockClasses.add(new ClassDto("#610 · IT-402",
                                     "Network Security",
                                     "8:00 AM - 10:00 AM | Lab 1",
                                     "Mon",
                                     1));
        mockClasses.add(new ClassDto("#611 · CS-202",
                                     "Data Structures",
                                     "10:00 AM - 12:00 PM | RM 302",
                                     "Mon",
                                     1));
        return mockClasses;
    }
}
