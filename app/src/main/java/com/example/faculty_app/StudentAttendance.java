package com.example.faculty_app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
    private TextView txtTotal, txtPresent, txtAbsent, btnEdit;
    private boolean isEditMode = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_attendance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtTotal = view.findViewById(R.id.txtTotal);
        txtPresent = view.findViewById(R.id.txtPresent);
        txtAbsent = view.findViewById(R.id.txtAbsent);
        btnEdit = view.findViewById(R.id.btnEdit);
        recyclerView = view.findViewById(R.id.studentRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadStudentData();
        adapter = new AttendanceAdapter(studentList);
        recyclerView.setAdapter(adapter);

        // --- FIXED EDIT/SAVE BUTTON LOGIC ---
        btnEdit.setOnClickListener(v -> {
            isEditMode = !isEditMode;
            if (isEditMode) {
                // UI changes for "Save & Submit" state
                btnEdit.setText("Save & Submit");
                btnEdit.setBackgroundResource(R.drawable.bg_save_button); // SOLID PURPLE
                btnEdit.setTextColor(Color.WHITE); // White text for purple bg
            } else {
                // UI changes for "Edit" state
                btnEdit.setText("Edit");
                btnEdit.setBackgroundResource(R.drawable.bg_edit_button); // OUTLINED PURPLE
                btnEdit.setTextColor(Color.parseColor("#7A67F8"));

                // Refresh stats on save
                updateStatsUI();
                Toast.makeText(getContext(), "Attendance Submitted Successfully!", Toast.LENGTH_SHORT).show();
            }
            adapter.setEditMode(isEditMode);
        });

        updateStatsUI();
    }

    private void loadStudentData() {
        studentList = new ArrayList<>();
        studentList.add(new AttendanceModel("Alice Johnson", "211-442", "NO VIOLATION", true));
        studentList.add(new AttendanceModel("Bob Smith", "211-445", "NO ID", false));
        studentList.add(new AttendanceModel("Charlie Brown", "211-448", "NO VIOLATION", true));
        studentList.add(new AttendanceModel("Diana Prince", "211-450", "NO VIOLATION", true));
        studentList.add(new AttendanceModel("Edward Norton", "211-455", "IMPROPER UNIFORM", false));
    }

    private void updateStatsUI() {
        int present = 0, absent = 0;
        for (AttendanceModel s : studentList) {
            if (s.isPresent()) present++; else absent++;
        }
        txtTotal.setText(String.valueOf(studentList.size()));
        txtPresent.setText(String.valueOf(present));
        txtAbsent.setText(String.valueOf(absent));
    }

    public static class AttendanceModel {
        private String name, id, violation;
        private boolean isPresent;
        public AttendanceModel(String name, String id, String violation, boolean isPresent) {
            this.name = name; this.id = id; this.violation = violation; this.isPresent = isPresent;
        }
        public String getName() { return name; }
        public String getId() { return id; }
        public String getViolation() { return violation; }
        public void setViolation(String v) { this.violation = v; }
        public boolean isPresent() { return isPresent; }
        public void setPresent(boolean present) { isPresent = present; }
    }

    public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
        private final List<AttendanceModel> list;
        private boolean isEditMode = false;

        public AttendanceAdapter(List<AttendanceModel> list) { this.list = list; }
        public void setEditMode(boolean mode) { this.isEditMode = mode; notifyDataSetChanged(); }

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

            if (isEditMode) {
                holder.statusChip.setVisibility(View.GONE);
                holder.txtViolation.setVisibility(View.GONE);
                holder.layoutButtons.setVisibility(View.VISIBLE);
                holder.spinnerViolation.setVisibility(View.VISIBLE);

                String[] violations = {"NO VIOLATION", "NO ID", "IMPROPER UNIFORM"};
                ArrayAdapter<String> spinAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, violations);
                holder.spinnerViolation.setAdapter(spinAdapter);

                for(int i=0; i<violations.length; i++) {
                    if(violations[i].equals(student.getViolation())) holder.spinnerViolation.setSelection(i);
                }

                holder.spinnerViolation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id) { student.setViolation(violations[pos]); }
                    @Override public void onNothingSelected(AdapterView<?> p) {}
                });

                updateToggleUI(holder, student.isPresent());
                holder.btnPresent.setOnClickListener(v -> { student.setPresent(true); updateToggleUI(holder, true); });
                holder.btnAbsent.setOnClickListener(v -> { student.setPresent(false); updateToggleUI(holder, false); });
            } else {
                holder.statusChip.setVisibility(View.VISIBLE);
                holder.txtViolation.setVisibility(View.VISIBLE);
                holder.layoutButtons.setVisibility(View.GONE);
                holder.spinnerViolation.setVisibility(View.GONE);
                holder.txtViolation.setText(student.getViolation());
                holder.statusChip.setText(student.isPresent() ? "Present" : "Absent");
                holder.statusChip.setTextColor(student.isPresent() ? Color.parseColor("#47C85A") : Color.parseColor("#FF5A5A"));
            }
        }

        private void updateToggleUI(ViewHolder h, boolean present) {
            if (present) {
                h.btnPresent.setBackgroundResource(R.drawable.bg_present_chip);
                h.btnPresent.setTextColor(Color.parseColor("#47C85A"));
                h.btnAbsent.setBackgroundResource(R.drawable.bg_status_unselected);
                h.btnAbsent.setTextColor(Color.GRAY);
            } else {
                h.btnPresent.setBackgroundResource(R.drawable.bg_status_unselected);
                h.btnPresent.setTextColor(Color.GRAY);
                h.btnAbsent.setBackgroundResource(R.drawable.bg_absent_selected);
                h.btnAbsent.setTextColor(Color.parseColor("#FF5A5A"));
            }
        }

        @Override public int getItemCount() { return list.size(); }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView name, id, txtViolation, statusChip, btnPresent, btnAbsent;
            Spinner spinnerViolation;
            LinearLayout layoutButtons;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.txtStudentName);
                id = itemView.findViewById(R.id.txtStudentId);
                txtViolation = itemView.findViewById(R.id.txtViolation);
                statusChip = itemView.findViewById(R.id.txtStatus);
                btnPresent = itemView.findViewById(R.id.btnPresent);
                btnAbsent = itemView.findViewById(R.id.btnAbsent);
                spinnerViolation = itemView.findViewById(R.id.spinnerViolation);
                layoutButtons = itemView.findViewById(R.id.layoutAttendanceButtons);
            }
        }
    }
}