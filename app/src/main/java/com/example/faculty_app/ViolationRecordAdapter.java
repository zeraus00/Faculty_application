package com.example.faculty_app;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViolationRecordAdapter extends RecyclerView.Adapter<ViolationRecordAdapter.ViolationViewHolder> {

    public interface OnViolationActionListener {
        void onMarkSettledClicked(ViolationRecordModel item, int position);
    }

    private final List<ViolationRecordModel> violationList;
    private final OnViolationActionListener listener;

    public ViolationRecordAdapter(List<ViolationRecordModel> violationList, OnViolationActionListener listener) {
        this.violationList = violationList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViolationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_violation_record, parent, false);
        return new ViolationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViolationViewHolder holder, int position) {
        ViolationRecordModel item = violationList.get(position);

        holder.txtViolationTitle.setText(item.getTitle());
        holder.txtViolationDate.setText(item.getDate());

        if ("UNSETTLED".equalsIgnoreCase(item.getStatus())) {
            holder.txtViolationStatus.setText("UNSETTLED");
            holder.txtViolationStatus.setBackgroundResource(R.drawable.bg_violation_unsettled);
            holder.txtViolationStatus.setTextColor(Color.parseColor("#D28A2E"));
            holder.btnMarkSettled.setVisibility(View.VISIBLE);
        } else {
            holder.txtViolationStatus.setText("SETTLED");
            holder.txtViolationStatus.setBackgroundResource(R.drawable.bg_violation_settled);
            holder.txtViolationStatus.setTextColor(Color.parseColor("#2FA84F"));
            holder.btnMarkSettled.setVisibility(View.GONE);
        }

        holder.btnMarkSettled.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMarkSettledClicked(item, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return violationList.size();
    }

    public static class ViolationViewHolder extends RecyclerView.ViewHolder {
        TextView txtViolationTitle, txtViolationDate, txtViolationStatus, btnMarkSettled;

        public ViolationViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViolationTitle = itemView.findViewById(R.id.txtViolationTitle);
            txtViolationDate = itemView.findViewById(R.id.txtViolationDate);
            txtViolationStatus = itemView.findViewById(R.id.txtViolationStatus);
            btnMarkSettled = itemView.findViewById(R.id.btnMarkSettled);
        }
    }
}