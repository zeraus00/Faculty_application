package com.example.faculty_app.mainapp.attendance.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faculty_app.R;
import com.example.faculty_app.mainapp.attendance.data.local.models.PresentHistoryModel;

import java.util.List;

public class PresentHistoryAdapter extends RecyclerView.Adapter<PresentHistoryAdapter.PresentViewHolder> {

    private final List<PresentHistoryModel> presentList;

    public PresentHistoryAdapter(List<PresentHistoryModel> presentList) {
        this.presentList = presentList;
    }

    @NonNull
    @Override
    public PresentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_present_history, parent, false);
        return new PresentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PresentViewHolder holder, int position) {
        PresentHistoryModel item = presentList.get(position);
        holder.txtDate.setText(item.getDate());
        holder.txtDescription.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return presentList.size();
    }

    static class PresentViewHolder extends RecyclerView.ViewHolder {

        TextView txtDate, txtDescription;

        public PresentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtDescription = itemView.findViewById(R.id.txtDescription);
        }
    }
}