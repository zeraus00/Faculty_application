package com.example.faculty_app;

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

public class StudentListFragment extends Fragment {

    public StudentListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.studentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<StudentModel> studentList = new ArrayList<>();

        studentList.add(new StudentModel("Alice Johnson", "211-442", "NO VIOLATION", "Present"));
        studentList.add(new StudentModel("Alice Johnson", "211-442", "NO ID", "Absent"));
        studentList.add(new StudentModel("Alice Johnson", "211-442", "NO VIOLATION", "Present"));
        studentList.add(new StudentModel("Alice Johnson", "211-442", "NO VIOLATION", "Absent"));
        studentList.add(new StudentModel("Alice Johnson", "211-442", "NO ID", "Present"));

        StudentAdapter adapter = new StudentAdapter(studentList);
        recyclerView.setAdapter(adapter);

        return view;
    }


    public static class StudentModel {
        String name, id, violation, status;

        public StudentModel(String name, String id, String violation, String status) {
            this.name = name;
            this.id = id;
            this.violation = violation;
            this.status = status;
        }
    }


    public static class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

        private final List<StudentModel> list;

        public StudentAdapter(List<StudentModel> list) {
            this.list = list;
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

            StudentModel item = list.get(position);

            holder.name.setText(item.name);
            holder.id.setText(item.id);
            holder.violation.setText(item.violation);
            holder.status.setText(item.status);


            if (item.status.equalsIgnoreCase("Present")) {
                holder.status.setBackgroundResource(R.drawable.bg_present_chip);
                holder.status.setTextColor(0xFF47C85A);
            } else {
                holder.status.setBackgroundResource(R.drawable.bg_absent_chip);
                holder.status.setTextColor(0xFFFF5A5A);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
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