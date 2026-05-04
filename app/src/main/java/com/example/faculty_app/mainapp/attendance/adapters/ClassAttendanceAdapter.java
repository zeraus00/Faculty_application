package com.example.faculty_app.mainapp.attendance.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faculty_app.R;
import com.example.faculty_app.mainapp.attendance.PresentHistoryFragment;
import com.example.faculty_app.mainapp.attendance.data.local.models.classattendance.AttendanceItemModel;

import java.util.ArrayList;

public class ClassAttendanceAdapter extends RecyclerView.Adapter<ClassAttendanceAdapter.ViewHolder> {
    private final ArrayList<AttendanceItemModel> attendance;
    private final Fragment fragment;
    private boolean isEditMode = false;

    public ClassAttendanceAdapter(Fragment fragment, ArrayList<AttendanceItemModel> attendance) {
        this.fragment = fragment;
        this.attendance = attendance;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_attendance, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        var attendanceItem = attendance.get(position);
        var isPresent = attendanceItem.getStatus().equalsIgnoreCase("present");

        holder.name.setText(attendanceItem.getName());
        holder.studentNumber.setText(attendanceItem.getStudentNumber());

        holder.itemView.setOnClickListener(v -> {
            PresentHistoryFragment presentHistoryFragment = new PresentHistoryFragment();

            //  todo: update fragment data here.

            fragment.getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, presentHistoryFragment)
                    .addToBackStack(null)
                    .commit();
        });

        if (isEditMode) {

            holder.statusChip.setVisibility(View.GONE);
            holder.txtViolation.setVisibility(View.GONE);
            holder.layoutButtons.setVisibility(View.VISIBLE);
            holder.spinnerViolation.setVisibility(View.VISIBLE);

            String[] violations = {"NO VIOLATION", "NO ID", "IMPROPER UNIFORM"};
            ArrayAdapter<String> spinAdapter = new ArrayAdapter<>(
                    fragment.requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    violations
            );
            holder.spinnerViolation.setAdapter(spinAdapter);

            holder.spinnerViolation.setOnItemSelectedListener(null);

            for (int i = 0; i < violations.length; i++) {
                if (violations[i].equals(attendanceItem.getViolation())) {
                    holder.spinnerViolation.setSelection(i, false);
                    break;
                }
            }

            holder.spinnerViolation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    attendanceItem.setViolation(violations[pos]);   //  todo: set new violation
                    // instead
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            updateToggleUI(holder, isPresent);

            holder.btnPresent.setOnClickListener(v -> {
                if (!"present".equalsIgnoreCase(attendanceItem.getStatus())) {
                    attendanceItem.setStatus("present");
                    notifyItemChanged(holder.getAdapterPosition());
                }
            });

            holder.btnAbsent.setOnClickListener(v -> {
                if (!"absent".equalsIgnoreCase(attendanceItem.getStatus())) {
                    attendanceItem.setStatus("absent");
                    notifyItemChanged(holder.getAdapterPosition());
                }
            });
        }
        else {
            holder.statusChip.setVisibility(View.VISIBLE);
            holder.txtViolation.setVisibility(View.VISIBLE);
            holder.layoutButtons.setVisibility(View.GONE);
            holder.spinnerViolation.setVisibility(View.GONE);

            holder.txtViolation.setText(attendanceItem.getViolation());
            holder.statusChip.setText(isPresent ? "Present" : "Absent");
            holder.statusChip.setTextColor(isPresent ?
                                           Color.parseColor("#47C85A") :
                                           Color.parseColor("#FF5A5A"));
        }
    }

    private void updateToggleUI(ClassAttendanceAdapter.ViewHolder h, boolean present) {
        if (present) {
            h.btnPresent.setBackgroundResource(R.drawable.bg_present_chip);
            h.btnPresent.setTextColor(Color.parseColor("#47C85A"));
            h.btnAbsent.setBackgroundResource(R.drawable.bg_status_unselected);
            h.btnAbsent.setTextColor(Color.GRAY);
        }
        else {
            h.btnPresent.setBackgroundResource(R.drawable.bg_status_unselected);
            h.btnPresent.setTextColor(Color.GRAY);
            h.btnAbsent.setBackgroundResource(R.drawable.bg_absent_selected);
            h.btnAbsent.setTextColor(Color.parseColor("#FF5A5A"));
        }
    }

    @Override
    public int getItemCount() {
        return attendance.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, studentNumber, txtViolation, statusChip, btnPresent, btnAbsent;
        Spinner spinnerViolation;
        LinearLayout layoutButtons;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtStudentName);
            studentNumber = itemView.findViewById(R.id.txtStudentNumber);
            txtViolation = itemView.findViewById(R.id.txtViolation);
            statusChip = itemView.findViewById(R.id.txtStatus);
            btnPresent = itemView.findViewById(R.id.btnPresent);
            btnAbsent = itemView.findViewById(R.id.btnAbsent);
            spinnerViolation = itemView.findViewById(R.id.spinnerViolation);
            layoutButtons = itemView.findViewById(R.id.layoutAttendanceButtons);
        }
    }

    public void setEditMode(boolean mode) {
        this.isEditMode = mode;
        notifyDataSetChanged();
    }
}
