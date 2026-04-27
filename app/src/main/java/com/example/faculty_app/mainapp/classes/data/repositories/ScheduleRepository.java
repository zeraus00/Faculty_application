package com.example.faculty_app.mainapp.classes.data.repositories;

import com.example.faculty_app.core.api.axis.dto.AxisCallback;
import com.example.faculty_app.core.api.axis.dto.response.AxisResult;
import com.example.faculty_app.mainapp.classes.data.remote.api.AxisSchedule;
import com.example.faculty_app.mainapp.classes.data.remote.response.classlist.ClassList;
import com.example.faculty_app.mainapp.classes.data.remote.response.classruntime.ClassRuntime;
import com.example.faculty_app.mainapp.classes.domain.ClassException;
import com.example.faculty_app.mainapp.classes.domain.ClassExceptionCode;
import com.example.faculty_app.shared.RepositoryCallback;
import com.example.faculty_app.shared.RepositoryResult;

public class ScheduleRepository {
    public static void fetchClassList(RepositoryCallback<ClassList> callback) {
        AxisSchedule.getClassList(new AxisCallback<ClassList>() {
            @Override
            public void onResult(AxisResult<ClassList> result) {
                if (result instanceof AxisResult.Success) {
                    var classList = ((AxisResult.Success<ClassList>) result).getData();
                    callback.onResult(new RepositoryResult.Success<>(classList));
                }
                else if (result instanceof AxisResult.Fail) {
                    var fail = (AxisResult.Fail<ClassList>) result;
                    callback.onResult(new RepositoryResult.Fail<>(new ClassException(
                            ClassExceptionCode.API_ERROR,
                            fail.message,
                            fail.throwable)));
                }
            }
        });
    }

    public static void fetchCurrentOrNext(RepositoryCallback<ClassRuntime> callback) {
        AxisSchedule.getCurrentOrNext(new AxisCallback<ClassRuntime>() {
            @Override
            public void onResult(AxisResult<ClassRuntime> result) {
                if (result instanceof AxisResult.Success) {
                    var classRuntime = ((AxisResult.Success<ClassRuntime>) result).getData();
                    callback.onResult(new RepositoryResult.Success<>(classRuntime));
                }
                else if (result instanceof AxisResult.Fail) {
                    var fail = (AxisResult.Fail<ClassRuntime>) result;
                    var code = fail.code;
                    callback.onResult(new RepositoryResult.Fail<>(new ClassException(
                            code != null && code == 404 ?
                            ClassExceptionCode.NOT_FOUND :
                            ClassExceptionCode.API_ERROR, fail.message, fail.throwable)));
                }
            }
        });
    }
}
