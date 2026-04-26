package com.example.faculty_app.mainapp.misc;

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

import com.example.faculty_app.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    // Required empty public constructor
    public NotificationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment (fragment_notification.xml)
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        // Initialize RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.rvNotifications);

        if (getContext() != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        // Data specifically for notifications
        List<NotifModel> list = new ArrayList<>();
        list.add(new NotifModel("Absent Appeal", "Alice Johnson (CS-301) submitted an Absent Appeal", "10 mins ago"));
        list.add(new NotifModel("Absent Appeal", "Alice Johnson (CS-301) submitted an Absent Appeal", "1 hour ago"));
        list.add(new NotifModel("Absent Appeal", "Bob Smith (CS-301) submitted an Absent Appeal", "Yesterday"));

        // Set the Adapter
        recyclerView.setAdapter(new NotifAdapter(list));

        return view;
    }

    //  Inner Model Class
    public static class NotifModel {
        String title, content, time;
        public NotifModel(String title, String content, String time) {
            this.title = title;
            this.content = content;
            this.time = time;
        }
    }

    // Inner Adapter Class
    public static class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.ViewHolder> {
        private final List<NotifModel> list;

        public NotifAdapter(List<NotifModel> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            NotifModel item = list.get(position);
            holder.tvTitle.setText(item.title);
            holder.tvContent.setText(item.content);
            holder.tvTime.setText(item.time);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle, tvContent, tvTime;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.NotifTitle);
                tvContent = itemView.findViewById(R.id.NotifContent);
                tvTime = itemView.findViewById(R.id.NotifTime);
            }
        }
    }
}