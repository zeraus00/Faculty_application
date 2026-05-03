package com.example.faculty_app.mainapp.classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faculty_app.R;
import com.example.faculty_app.mainapp.attendance.ClassAttendanceFragment;
import com.example.faculty_app.mainapp.classes.data.local.models.ClassDto;
import com.example.faculty_app.mainapp.home.HomePageActivity;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {
    private final List<ClassDto> list;

    public ClassAdapter(List<ClassDto> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ClassAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.item_class, parent, false);
        return new ClassAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassAdapter.ViewHolder holder, int position) {
        ClassDto m = list.get(position);
        holder.c.setText(m.code);
        holder.n.setText(m.name);
        holder.d.setText(m.details);

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("classId", m.id);

            HomePageActivity activity = (HomePageActivity) v.getContext();

            Fragment classAttendanceFragment = new ClassAttendanceFragment();
            classAttendanceFragment.setArguments(bundle);
            activity.loadFragment(classAttendanceFragment);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView c, n, d;

        public ViewHolder(View itemView) {
            super(itemView);
            c = itemView.findViewById(R.id.ClassCode);
            n = itemView.findViewById(R.id.ClasName);
            d = itemView.findViewById(R.id.ClassDetails);
        }
    }
}