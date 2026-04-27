package com.example.faculty_app.mainapp.classes.data.local;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.faculty_app.mainapp.classes.data.local.models.ClassDto;
import com.example.faculty_app.mainapp.classes.data.local.models.RuntimeDto;

import java.time.LocalDate;
import java.util.ArrayList;

public class ClassesViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<ClassDto>> classList =
            new MutableLiveData<>(new ArrayList<>());

    private final MutableLiveData<ArrayList<ClassDto>> classesToday =
            new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<RuntimeDto> runtime = new MutableLiveData<>(new RuntimeDto("",
                                                                                             "Currently Offline",
                                                                                             "",
                                                                                             "",
                                                                                             "",
                                                                                             "offline"));

    public LiveData<ArrayList<ClassDto>> getClassList() {
        return classList;
    }

    public LiveData<ArrayList<ClassDto>> getClassesToday() {
        return classesToday;
    }

    public LiveData<RuntimeDto> getRuntime() {
        return runtime;
    }

    public void setClassList(ArrayList<ClassDto> classes) {
        classList.setValue(classes);
        classesToday.setValue(filterClassesToday(classes));
    }

    public void setRuntime(RuntimeDto cls) {
        runtime.setValue(cls);
    }

    private ArrayList<ClassDto> filterClassesToday(ArrayList<ClassDto> classes) {
        ArrayList<ClassDto> filtered = new ArrayList<>();

        int today = LocalDate.now().getDayOfWeek().getValue();

        for (ClassDto item : classes) {
            Integer day = item.weekDayNumeric;

            if (day != null && day == today) {
                filtered.add(item);
            }
        }

        return filtered;
    }
}
