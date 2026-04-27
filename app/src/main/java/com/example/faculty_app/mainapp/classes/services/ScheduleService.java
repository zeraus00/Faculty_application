package com.example.faculty_app.mainapp.classes.services;

import static com.example.faculty_app.mainapp.classes.data.local.mappers.ClassDtoMapper.fromApiClassList;

import com.example.faculty_app.BuildConfig;
import com.example.faculty_app.mainapp.classes.data.local.models.ClassDto;
import com.example.faculty_app.mainapp.classes.data.remote.response.classlist.ClassList;
import com.example.faculty_app.mainapp.classes.data.repositories.ScheduleRepository;
import com.example.faculty_app.mainapp.classes.domain.ClassException;
import com.example.faculty_app.shared.BaseCallback;
import com.example.faculty_app.shared.BaseResult;

import java.util.ArrayList;

public class ScheduleService {
    public static void getClassList(BaseCallback<ArrayList<ClassDto>> callback) {
        ScheduleRepository.fetchClassList(new BaseCallback<ClassList>() {
            @Override
            public void onResult(BaseResult<ClassList> result) {
                if (result instanceof BaseResult.Success) {
                    var classList = ((BaseResult.Success<ClassList>) result).getData();
                    callback.onResult(new BaseResult.Success<>(fromApiClassList(classList)));
                }
                else if (result instanceof BaseResult.Fail) {
                    if (BuildConfig.DEBUG && BuildConfig.USE_MOCK_AUTH) {
                        callback.onResult(new BaseResult.Success<>(getMockClasses()));
                        return;
                    }
                    callback.onResult(new BaseResult.Fail<>(((BaseResult.Fail<?, ClassException>) result).getException()));
                }
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
