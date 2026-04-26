package com.example.faculty_app.mainapp.attendance;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faculty_app.R;
import com.example.faculty_app.mainapp.violations.ViolationHistoryFragment;

import java.util.ArrayList;
import java.util.List;

public class AbsentHistoryFragment extends Fragment {

    private RecyclerView absentRecyclerView;
    private AbsentHistoryAdapter absentAdapter;
    private List<AbsentHistoryModel> absentList;

    private LinearLayout presentrec, violation;

    private TextView txtStudentName;
    private TextView txtStudentId;
    private TextView txtStatus;
    private TextView txtPresentCount;
    private TextView txtAbsentCount;
    private TextView txtViolationCount;

    public AbsentHistoryFragment() {
        super(R.layout.fragment_absent_rec);
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

        absentRecyclerView = view.findViewById(R.id.absentRecyclerView);
        absentRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        absentRecyclerView.setNestedScrollingEnabled(false);

        presentrec = view.findViewById(R.id.presentrec);
        violation = view.findViewById(R.id.violation);

        bindStudentData();
        setupAbsentList();

        presentrec.setOnClickListener(v -> {
            PresentHistoryFragment fragment = new PresentHistoryFragment();
            fragment.setArguments(copyArgs());

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        violation.setOnClickListener(v -> {
            ViolationHistoryFragment fragment = new ViolationHistoryFragment();
            fragment.setArguments(copyArgs());

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void bindStudentData() {
        Bundle bundle = getArguments();
        if (bundle == null) return;

        String name = bundle.getString("student_name", "");
        String id = bundle.getString("student_id", "");
        boolean isPresent = bundle.getBoolean("student_present", false);
        String studentViolation = bundle.getString("student_violation", "").trim();

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

    private void setupAbsentList() {
        absentList = new ArrayList<>();

        Bundle args = getArguments();
        if (args != null) {
            boolean isPresent = args.getBoolean("student_present", false);

            String absentDate = args.getString("absent_date", "Mar 9, 2026");
            String absentDescription = args.getString("absent_description", "Marked Absent in Class");
            String absentStatus = args.getString("absent_status", "UNEXCUSED");

            if (!isPresent) {
                absentList.add(new AbsentHistoryModel(
                        absentDate,
                        absentDescription,
                        absentStatus
                ));
            }
        }

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
    }

    private Bundle copyArgs() {
        Bundle old = getArguments();
        Bundle data = new Bundle();

        if (old != null) {
            data.putString("student_name", old.getString("student_name", ""));
            data.putString("student_id", old.getString("student_id", ""));
            data.putBoolean("student_present", old.getBoolean("student_present", false));
            data.putString("student_violation", old.getString("student_violation", "NO VIOLATION"));

            data.putString("absent_date", old.getString("absent_date", "Mar 9, 2026"));
            data.putString("absent_description", old.getString("absent_description", "Marked Absent in Class"));
            data.putString("absent_status", old.getString("absent_status", "UNEXCUSED"));
        }

        return data;
    }
}