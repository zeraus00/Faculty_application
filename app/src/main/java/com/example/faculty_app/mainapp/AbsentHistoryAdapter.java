package com.example.faculty_app.mainapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faculty_app.R;

import java.util.List;

public class AbsentHistoryAdapter extends RecyclerView.Adapter<AbsentHistoryAdapter.AbsentViewHolder> {

    public interface OnAbsentActionListener {
        void onApproveClicked(AbsentHistoryModel item, int position);
        void onDenyClicked(AbsentHistoryModel item, int position);
    }

    private final List<AbsentHistoryModel> absentList;
    private final OnAbsentActionListener listener;

    public AbsentHistoryAdapter(List<AbsentHistoryModel> absentList, OnAbsentActionListener listener) {
        this.absentList = absentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AbsentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_absent_history, parent, false);
        return new AbsentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsentViewHolder holder, int position) {
        AbsentHistoryModel item = absentList.get(position);

        holder.txtHistoryDate.setText(item.getDate());
        holder.txtHistoryDesc.setText(item.getDescription());

        String status = item.getStatus();

        if ("PENDING APPEAL".equalsIgnoreCase(status)) {
            holder.txtStatusBadge.setText("PENDING\nAPPEAL");
            holder.txtStatusBadge.setBackgroundResource(R.drawable.bg_absent_status_pending);
            holder.txtStatusBadge.setTextColor(Color.parseColor("#D08A41"));
            holder.layoutActionButtons.setVisibility(View.VISIBLE);

        } else if ("UNEXCUSED".equalsIgnoreCase(status)) {
            holder.txtStatusBadge.setText("UNEXCUSED");
            holder.txtStatusBadge.setBackgroundResource(R.drawable.bg_absent_status_unexcused);
            holder.txtStatusBadge.setTextColor(Color.parseColor("#D65C5C"));
            holder.layoutActionButtons.setVisibility(View.GONE);

        } else if ("APPROVED".equalsIgnoreCase(status)) {
            holder.txtStatusBadge.setText("APPROVED");
            holder.txtStatusBadge.setBackgroundResource(R.drawable.bg_absent_status_approved);
            holder.txtStatusBadge.setTextColor(Color.parseColor("#2FA84F"));
            holder.layoutActionButtons.setVisibility(View.GONE);

        } else {
            holder.txtStatusBadge.setText(status);
            holder.layoutActionButtons.setVisibility(View.GONE);
        }

        holder.btnApproveExcuse.setOnClickListener(v -> {
            if (listener != null) {
                listener.onApproveClicked(item, position);
            }
        });

        holder.btnDeny.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDenyClicked(item, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return absentList.size();
    }

    public static class AbsentViewHolder extends RecyclerView.ViewHolder {

        TextView txtHistoryDate, txtHistoryDesc, txtStatusBadge;
        TextView btnApproveExcuse, btnDeny;
        View layoutActionButtons;

        public AbsentViewHolder(@NonNull View itemView) {
            super(itemView);

            txtHistoryDate = itemView.findViewById(R.id.txtHistoryDate);
            txtHistoryDesc = itemView.findViewById(R.id.txtHistoryDesc);
            txtStatusBadge = itemView.findViewById(R.id.txtStatusBadge);
            btnApproveExcuse = itemView.findViewById(R.id.btnApproveExcuse);
            btnDeny = itemView.findViewById(R.id.btnDeny);
            layoutActionButtons = itemView.findViewById(R.id.layoutActionButtons);
        }
    }
}