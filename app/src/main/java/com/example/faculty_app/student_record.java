package com.example.faculty_app;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class student_record extends Fragment {

    private TextView txtStudentName;
    private TextView txtStudentId;
    private TextView txtStatus;
    private TextView txtPresentCount;
    private TextView txtAbsentCount;
    private TextView txtViolationCount;

    private LinearLayout absent;
    private LinearLayout violation;

    public student_record() {
        super(R.layout.fragment_student_rec);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtStudentName = view.findViewById(R.id.txtStudentName);
        txtStudentId = view.findViewById(R.id.txtStudentId);
        txtStatus = view.findViewById(R.id.txtStatus);
        txtPresentCount = view.findViewById(R.id.txtPresentCount);
        txtAbsentCount = view.findViewById(R.id.txtAbsentCount);
        txtViolationCount = view.findViewById(R.id.txtViolationCount);

        absent = view.findViewById(R.id.absent);
        violation = view.findViewById(R.id.violation);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.getString("student_name", "");
            String id = bundle.getString("student_id", "");
            boolean isPresent = bundle.getBoolean("student_present", false);
            String studentViolation = bundle.getString("student_violation", "NO VIOLATION");

            txtStudentName.setText(name);
            txtStudentId.setText(id);
            txtStatus.setText(isPresent ? "PRESENT" : "ABSENT");

            txtPresentCount.setText(isPresent ? "1" : "0");
            txtAbsentCount.setText(isPresent ? "0" : "1");
            txtViolationCount.setText("NO VIOLATION".equalsIgnoreCase(studentViolation) ? "0" : "1");
        }

        absent.setOnClickListener(v -> {
            absent_rec absentFragment = new absent_rec();

            Bundle data = new Bundle();
            if (getArguments() != null) {
                data.putString("student_name", getArguments().getString("student_name", ""));
                data.putString("student_id", getArguments().getString("student_id", ""));
                data.putBoolean("student_present", getArguments().getBoolean("student_present", false));
                data.putString("student_violation", getArguments().getString("student_violation", "NO VIOLATION"));
            }

            absentFragment.setArguments(data);

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, absentFragment)
                    .addToBackStack(null)
                    .commit();
        });

        violation.setOnClickListener(v -> {
            student_violation_rec violationFragment = new student_violation_rec();

            Bundle data = new Bundle();
            if (getArguments() != null) {
                data.putString("student_name", getArguments().getString("student_name", ""));
                data.putString("student_id", getArguments().getString("student_id", ""));
                data.putBoolean("student_present", getArguments().getBoolean("student_present", false));
                data.putString("student_violation", getArguments().getString("student_violation", "NO VIOLATION"));
            }

            violationFragment.setArguments(data);

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, violationFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }
}