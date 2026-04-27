package com.example.faculty_app.mainapp.classes.services;

import static com.example.faculty_app.mainapp.classes.data.local.mappers.ClassDtoMapper.fromApiClassList;
import static com.example.faculty_app.mainapp.classes.data.local.mappers.RuntimeDtoMapper.fromApiRuntime;

import com.example.faculty_app.BuildConfig;
import com.example.faculty_app.mainapp.classes.data.local.models.ClassDto;
import com.example.faculty_app.mainapp.classes.data.local.models.RuntimeDto;
import com.example.faculty_app.mainapp.classes.data.remote.response.classlist.ClassList;
import com.example.faculty_app.mainapp.classes.data.remote.response.classruntime.ClassRuntime;
import com.example.faculty_app.mainapp.classes.data.repositories.ScheduleRepository;
import com.example.faculty_app.mainapp.classes.domain.ClassException;
import com.example.faculty_app.mainapp.classes.domain.ClassExceptionCode;
import com.example.faculty_app.shared.RepositoryCallback;
import com.example.faculty_app.shared.RepositoryResult;
import com.example.faculty_app.shared.ServiceCallback;
import com.example.faculty_app.shared.ServiceResult;

import java.util.ArrayList;

public class ScheduleService {
    public static void getCurrentOrNextClass(ServiceCallback<RuntimeDto> callback) {
        ScheduleRepository.fetchCurrentOrNext(new RepositoryCallback<ClassRuntime>() {
            @Override
            public void onResult(RepositoryResult<ClassRuntime> result) {
                if (result instanceof RepositoryResult.Success) {
                    var runtime = ((RepositoryResult.Success<ClassRuntime>) result).getData();
                    callback.onResult(new ServiceResult<>(true,
                                                          fromApiRuntime(runtime),
                                                          "Success retrieving current or next " +
                                                                  "class."));
                }
                else if (result instanceof RepositoryResult.Fail) {
                    var exception =
                            ((RepositoryResult.Fail<?, ClassException>) result).getException();
                    var code = exception.getCode();
                    var message = exception.getMessage();

                    callback.onResult(new ServiceResult<>(false,
                                                          new RuntimeDto("",
                                                                         code ==
                                                                                 ClassExceptionCode.NOT_FOUND ?
                                                                         "No classes today." :
                                                                         "Something went wrong.",
                                                                         "",
                                                                         "",
                                                                         ""),
                                                          message));
                }
            }
        });
    }

    public static void getClassList(ServiceCallback<ArrayList<ClassDto>> callback) {
        ScheduleRepository.fetchClassList(new RepositoryCallback<ClassList>() {
            @Override
            public void onResult(RepositoryResult<ClassList> result) {
                if (result instanceof RepositoryResult.Success) {
                    var classList = ((RepositoryResult.Success<ClassList>) result).getData();

                    if (classList == null) {
                        callback.onResult(new ServiceResult<>(false,
                                                              null,
                                                              "Failed retrieving classes."));
                        return;
                    }

                    callback.onResult(new ServiceResult<>(true,
                                                          fromApiClassList(classList),
                                                          "Success retrieving classes"));
                }
                else if (result instanceof RepositoryResult.Fail) {
                    var exception = ((RepositoryResult.Fail<?, ?>) result).getException();
                    var code = exception.getCode();
                    if (code == null && BuildConfig.DEBUG && BuildConfig.USE_MOCK_AUTH) {
                        callback.onResult(new ServiceResult<>(true,
                                                              getMockClasses(),
                                                              "Success mocking classes."));
                        return;
                    }

                    var message = exception.getMessage();
                    callback.onResult(new ServiceResult<>(false, null, message));
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
