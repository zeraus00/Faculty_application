package com.example.faculty_app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudentAttendance extends Fragment {

    private RecyclerView recyclerView;
    private AttendanceAdapter adapter;
    private List<AttendanceModel> studentList;
    private TextView txtTotal, txtPresent, txtAbsent;

    public StudentAttendance() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_attendance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Views
        txtTotal = view.findViewById(R.id.txtTotal);
        txtPresent = view.findViewById(R.id.txtPresent);
        txtAbsent = view.findViewById(R.id.txtAbsent);
        recyclerView = view.findViewById(R.id.studentRecyclerView);

        // Setup RecyclerView with a LayoutManager
        if (getContext() != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        // Load Data
        loadStudentData();

        // Set Adapter
        adapter = new AttendanceAdapter(studentList);
        recyclerView.setAdapter(adapter);

        // Update Stats
        updateStatsUI();
    }

    private void loadStudentData() {
        studentList = new ArrayList<>();
        studentList.add(new AttendanceModel("Alice Johnson", "211-442", "NO VIOLATION", "Present"));
        studentList.add(new AttendanceModel("Bob Smith", "211-445", "NO UNIFORM", "Absent"));
        studentList.add(new AttendanceModel("Charlie Brown", "211-448", "NO VIOLATION", "Present"));
        studentList.add(new AttendanceModel("Diana Prince", "211-450", "NO VIOLATION", "Present"));
        studentList.add(new AttendanceModel("Edward Norton", "211-455", "LATE", "Absent"));
    }

    private void updateStatsUI() {
        int presentCount = 0;
        int absentCount = 0;

        if (studentList == null) return;

        for (AttendanceModel student : studentList) {
            if (student.getStatus().equalsIgnoreCase("Present")) {
                presentCount++;
            } else {
                absentCount++;
            }
        }

        if (txtTotal != null) txtTotal.setText(String.valueOf(studentList.size()));
        if (txtPresent != null) txtPresent.setText(String.valueOf(presentCount));
        if (txtAbsent != null) txtAbsent.setText(String.valueOf(absentCount));
    }

    // INNER CLASS: DATA MODEL
    public static class AttendanceModel {
        private final String name, id, violation, status;

        public AttendanceModel(String name, String id, String violation, String status) {
            this.name = name;
            this.id = id;
            this.violation = violation;
            this.status = status;
        }

        public String getName() { return name; }
        public String getId() { return id; }
        public String getViolation() { return violation; }
        public String getStatus() { return status; }
    }

    // INNER CLASS: RECYCLERVIEW ADAPTER
    public static class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
        private final List<AttendanceModel> list;

        public AttendanceAdapter(List<AttendanceModel> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendance, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            AttendanceModel student = list.get(position);
            holder.name.setText(student.getName());
            holder.id.setText(student.getId());
            holder.violation.setText(student.getViolation());
            holder.status.setText(student.getStatus());

            if (student.getStatus().equalsIgnoreCase("Absent")) {
                holder.status.setTextColor(Color.parseColor("#FF5A5A")); // Red
            } else {
                holder.status.setTextColor(Color.parseColor("#47C85A")); // Green
            }
        }

        @Override
        public int getItemCount() {
            return list != null ? list.size() : 0;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView name, id, violation, status;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.txtStudentName);
                id = itemView.findViewById(R.id.txtStudentId);
                violation = itemView.findViewById(R.id.txtViolation);
                status = itemView.findViewById(R.id.txtStatus);
            }
        }
    }
}