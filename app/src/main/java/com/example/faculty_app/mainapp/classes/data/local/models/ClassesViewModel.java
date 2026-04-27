package com.example.faculty_app.mainapp.classes.data.local.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class ClassesViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<ClassDto>> classList =
            new MutableLiveData<>(new ArrayList<>());

    private final MutableLiveData<ArrayList<ClassDto>> classesToday =
            new MutableLiveData<>(new ArrayList<>());

    public LiveData<ArrayList<ClassDto>> getClassList() {
        return classList;
    }

    public LiveData<ArrayList<ClassDto>> getClassesToday() {
        return classesToday;
    }

    public void setClassList(ArrayList<ClassDto> classes) {
        classList.setValue(classes);
        classesToday.setValue(filterClassesToday(classes));
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
