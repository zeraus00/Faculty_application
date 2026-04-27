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
import com.example.faculty_app.mainapp.attendance.adapters.PresentHistoryAdapter;
import com.example.faculty_app.mainapp.attendance.data.local.models.PresentHistoryModel;
import com.example.faculty_app.mainapp.violations.ViolationHistoryFragment;

import java.util.ArrayList;
import java.util.List;

public class PresentHistoryFragment extends Fragment {

    private TextView txtStudentName;
    private TextView txtStudentId;
    private TextView txtStatus;
    private TextView txtPresentCount;
    private TextView txtAbsentCount;
    private TextView txtViolationCount;

    private LinearLayout absent;
    private LinearLayout violation;

    private RecyclerView historyRecyclerView;
    private PresentHistoryAdapter historyAdapter;
    private List<PresentHistoryModel> historyList;

    public PresentHistoryFragment() {
        super(R.layout.fragment_present_history);
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

        historyRecyclerView = view.findViewById(R.id.historyRecyclerView);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        historyRecyclerView.setNestedScrollingEnabled(false);

        bindStudentData();
        setupPresentHistory();

        absent.setOnClickListener(v -> {
            AbsentHistoryFragment absentFragment = new AbsentHistoryFragment();
            absentFragment.setArguments(copyArgs());

            getParentFragmentManager().beginTransaction()
                                      .replace(R.id.fragment_container, absentFragment)
                                      .addToBackStack(null)
                                      .commit();
        });

        violation.setOnClickListener(v -> {
            ViolationHistoryFragment violationFragment = new ViolationHistoryFragment();
            violationFragment.setArguments(copyArgs());

            getParentFragmentManager().beginTransaction()
                                      .replace(R.id.fragment_container, violationFragment)
                                      .addToBackStack(null)
                                      .commit();
        });
    }

    private void bindStudentData() {
        Bundle bundle = getArguments();
        if (bundle == null)
            return;

        String name = bundle.getString("student_name", "");
        String id = bundle.getString("student_id", "");
        boolean isPresent = bundle.getBoolean("student_present", true);
        String studentViolation = bundle.getString("student_violation", "").trim();

        boolean hasViolation =
                !studentViolation.isEmpty() && !studentViolation.equalsIgnoreCase("NO VIOLATION") &&
                        !studentViolation.equalsIgnoreCase("NONE");

        txtStudentName.setText(name);
        txtStudentId.setText(id);
        txtStatus.setText(isPresent ? "PRESENT" : "ABSENT");

        txtPresentCount.setText(isPresent ? "1" : "0");
        txtAbsentCount.setText(isPresent ? "0" : "1");
        txtViolationCount.setText(hasViolation ? "1" : "0");
    }

    private void setupPresentHistory() {
        historyList = new ArrayList<>();

        Bundle args = getArguments();
        if (args != null) {
            boolean isPresent = args.getBoolean("student_present", true);

            if (isPresent) {
                String presentDate = args.getString("present_date", "Mar 10, 2026");
                String presentDescription = args.getString("present_description",
                                                           "Marked Present in Class");
                String presentStatus = args.getString("present_status", "PRESENT");

                historyList.add(new PresentHistoryModel(presentDate,
                                                        presentDescription,
                                                        presentStatus));
            }
        }

        historyAdapter = new PresentHistoryAdapter(historyList);
        historyRecyclerView.setAdapter(historyAdapter);
    }

    private Bundle copyArgs() {
        Bundle old = getArguments();
        Bundle data = new Bundle();

        if (old != null) {
            data.putString("student_name", old.getString("student_name", ""));
            data.putString("student_id", old.getString("student_id", ""));
            data.putBoolean("student_present", old.getBoolean("student_present", true));
            data.putString("student_violation", old.getString("student_violation", "NO VIOLATION"));

            data.putString("present_date", old.getString("present_date", "Mar 10, 2026"));
            data.putString("present_description",
                           old.getString("present_description", "Marked Present in Class"));
            data.putString("present_status", old.getString("present_status", "PRESENT"));

            data.putString("absent_date", old.getString("absent_date", "Mar 9, 2026"));
            data.putString("absent_description",
                           old.getString("absent_description", "Marked Absent in Class"));
            data.putString("absent_status", old.getString("absent_status", "UNEXCUSED"));
        }

        return data;
    }
}