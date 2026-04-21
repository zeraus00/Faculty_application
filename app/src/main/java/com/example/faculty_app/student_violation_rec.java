package com.example.faculty_app;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class student_violation_rec extends Fragment {

    private RecyclerView violationRecyclerView;
    private ViolationRecordAdapter violationAdapter;
    private List<ViolationRecordModel> violationList;

    private LinearLayout absent;
    private LinearLayout present;

    private TextView txtStudentName;
    private TextView txtStudentId;
    private TextView txtStatus;
    private TextView txtPresentCount;
    private TextView txtAbsentCount;
    private TextView txtViolationCount;

    public student_violation_rec() {
        super(R.layout.fragment_student_violation_rec);
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
        present = view.findViewById(R.id.presentrec);

        violationRecyclerView = view.findViewById(R.id.violationRecyclerView);
        violationRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        violationRecyclerView.setNestedScrollingEnabled(false);

        bindStudentData();
        setupViolationList();

        absent.setOnClickListener(v -> {
            absent_rec absentFragment = new absent_rec();
            absentFragment.setArguments(copyArgs());

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, absentFragment)
                    .addToBackStack(null)
                    .commit();
        });

        present.setOnClickListener(v -> {
            student_record recordFragment = new student_record();
            recordFragment.setArguments(copyArgs());

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, recordFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void bindStudentData() {
        Bundle args = getArguments();
        if (args == null) return;

        String name = args.getString("student_name", "");
        String id = args.getString("student_id", "");
        boolean isPresent = args.getBoolean("student_present", false);
        String studentViolation = args.getString("student_violation", "").trim();

        boolean hasViolation = !studentViolation.isEmpty()
                && !studentViolation.equalsIgnoreCase("NO VIOLATION")
                && !studentViolation.equalsIgnoreCase("NONE");

        txtStudentName.setText(name);
        txtStudentId.setText(id);
        txtStatus.setText(isPresent ? "PRESENT" : "ABSENT");

        txtPresentCount.setText(isPresent ? "1" : "0");
        txtAbsentCount.setText(isPresent ? "0" : "1");
        txtViolationCount.setText(hasViolation ? "1" : "0");
    }

    private void setupViolationList() {
        violationList = new ArrayList<>();

        Bundle args = getArguments();
        if (args != null) {
            String studentViolation = args.getString("student_violation", "").trim();

            boolean hasViolation = !studentViolation.isEmpty()
                    && !studentViolation.equalsIgnoreCase("NO VIOLATION")
                    && !studentViolation.equalsIgnoreCase("NONE");

            if (hasViolation) {
                violationList.add(new ViolationRecordModel(
                        studentViolation,
                        "Mar 10, 2026",
                        "UNSETTLED"
                ));
            }
        }

        violationAdapter = new ViolationRecordAdapter(
                violationList,
                new ViolationRecordAdapter.OnViolationActionListener() {
                    @Override
                    public void onMarkSettledClicked(ViolationRecordModel item, int position) {
                        violationList.set(position, new ViolationRecordModel(
                                item.getTitle(),
                                item.getDate(),
                                "SETTLED"
                        ));
                        violationAdapter.notifyItemChanged(position);
                    }
                }
        );

        violationRecyclerView.setAdapter(violationAdapter);
    }

    private Bundle copyArgs() {
        Bundle old = getArguments();
        Bundle b = new Bundle();

        if (old != null) {
            b.putString("student_name", old.getString("student_name", ""));
            b.putString("student_id", old.getString("student_id", ""));
            b.putBoolean("student_present", old.getBoolean("student_present", false));
            b.putString("student_violation", old.getString("student_violation", "NO VIOLATION"));
        }

        return b;
    }
}