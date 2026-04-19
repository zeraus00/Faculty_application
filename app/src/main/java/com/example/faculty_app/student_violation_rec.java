package com.example.faculty_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

    public student_violation_rec() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_violation_rec, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        absent = view.findViewById(R.id.absent);
        present = view.findViewById(R.id.presentrec);

        violationRecyclerView = view.findViewById(R.id.violationRecyclerView);
        violationRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        violationRecyclerView.setNestedScrollingEnabled(false);

        violationList = new ArrayList<>();
        violationList.add(new ViolationRecordModel("Improper Uniform", "Mar 10, 2026", "UNSETTLED"));

        violationAdapter = new ViolationRecordAdapter(violationList, new ViolationRecordAdapter.OnViolationActionListener() {
            @Override
            public void onMarkSettledClicked(ViolationRecordModel item, int position) {
                violationList.set(position, new ViolationRecordModel(
                        item.getTitle(),
                        item.getDate(),
                        "SETTLED"
                ));
                violationAdapter.notifyItemChanged(position);
            }
        });

        violationRecyclerView.setAdapter(violationAdapter);

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