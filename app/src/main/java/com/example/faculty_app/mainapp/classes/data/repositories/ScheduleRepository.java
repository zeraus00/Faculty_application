package com.example.faculty_app.mainapp.classes.data.repositories;

import com.example.faculty_app.core.api.axis.dto.AxisCallback;
import com.example.faculty_app.core.api.axis.dto.response.AxisResult;
import com.example.faculty_app.mainapp.classes.data.remote.api.AxisSchedule;
import com.example.faculty_app.mainapp.classes.data.remote.response.classlist.ClassList;
import com.example.faculty_app.mainapp.classes.domain.ClassException;
import com.example.faculty_app.mainapp.classes.domain.ClassExceptionCode;
import com.example.faculty_app.shared.BaseCallback;
import com.example.faculty_app.shared.BaseResult;

public class ScheduleRepository {
    public static void fetchClassList(BaseCallback<ClassList> callback) {
        AxisSchedule.getClassList(new AxisCallback<ClassList>() {
            @Override
            public void onResult(AxisResult<ClassList> result) {
                if (result instanceof AxisResult.Success) {
                    var classList = ((AxisResult.Success<ClassList>) result).getData();
                    callback.onResult(new BaseResult.Success<>(classList));
                }
                else if (result instanceof AxisResult.Fail) {
                    var fail = (AxisResult.Fail<ClassList>) result;
                    callback.onResult(new BaseResult.Fail<>(new ClassException(ClassExceptionCode.API_ERROR,
                                                                               fail.message,
                                                                               fail.throwable)));
                }
            }
        });
    }

}
