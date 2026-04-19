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

public class absent_rec extends Fragment {

    private RecyclerView absentRecyclerView;
    private AbsentHistoryAdapter absentAdapter;
    private List<AbsentHistoryModel> absentList;

    private LinearLayout presentrec, violation;

    public absent_rec() {
        super(R.layout.fragment_absent_rec);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        absentRecyclerView = view.findViewById(R.id.absentRecyclerView);
        absentRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        absentRecyclerView.setNestedScrollingEnabled(false);

        absentList = new ArrayList<>();
        absentList.add(new AbsentHistoryModel("Mar 9, 2026", "Marked Absent in Class", "UNEXCUSED"));
        absentList.add(new AbsentHistoryModel("Mar 1, 2026", "Marked Absent in Class", "PENDING APPEAL"));
        absentList.add(new AbsentHistoryModel("Mar 9, 2026", "Marked Absent in Class", "APPROVED"));

        absentAdapter = new AbsentHistoryAdapter(absentList, new AbsentHistoryAdapter.OnAbsentActionListener() {
            @Override
            public void onApproveClicked(AbsentHistoryModel item, int position) {
                absentList.set(position, new AbsentHistoryModel(
                        item.getDate(),
                        item.getDescription(),
                        "APPROVED"
                ));
                absentAdapter.notifyItemChanged(position);
            }

            @Override
            public void onDenyClicked(AbsentHistoryModel item, int position) {
                absentList.set(position, new AbsentHistoryModel(
                        item.getDate(),
                        item.getDescription(),
                        "UNEXCUSED"
                ));
                absentAdapter.notifyItemChanged(position);
            }
        });

        absentRecyclerView.setAdapter(absentAdapter);


        presentrec = view.findViewById(R.id.presentrec);
        violation = view.findViewById(R.id.violation);

        // Go back to student_record
        presentrec.setOnClickListener(v -> {
            student_record fragment = new student_record();

            Bundle data = new Bundle();
            if (getArguments() != null) {
                data.putString("student_name", getArguments().getString("student_name", ""));
                data.putString("student_id", getArguments().getString("student_id", ""));
                data.putBoolean("student_present", getArguments().getBoolean("student_present", false));
                data.putString("student_violation", getArguments().getString("student_violation", "NO VIOLATION"));
            }

            fragment.setArguments(data);

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Go to student_violation_rec
        violation.setOnClickListener(v -> {
            student_violation_rec fragment = new student_violation_rec();

            Bundle data = new Bundle();
            if (getArguments() != null) {
                data.putString("student_name", getArguments().getString("student_name", ""));
                data.putString("student_id", getArguments().getString("student_id", ""));
                data.putBoolean("student_present", getArguments().getBoolean("student_present", false));
                data.putString("student_violation", getArguments().getString("student_violation", "NO VIOLATION"));
            }

            fragment.setArguments(data);

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }
}