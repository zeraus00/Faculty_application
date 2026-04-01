package com.example.faculty_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Home_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.classesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 2. Create Dummy Data
        List<ClassModel> classList = new ArrayList<>();
        classList.add(new ClassModel("#606 · DS-3202", "Machine Learning", "7:00 AM - 9:00 AM | AV 308b"));
        classList.add(new ClassModel("#606 · DS-3202", "Software Engineering", "7:00 AM - 9:00 AM | AV 308b"));
        classList.add(new ClassModel("#606 · DS-3202", "Operating System", "7:00 AM - 9:00 AM | AV 308b"));

        // 3. Set the Adapter
        ClassAdapter adapter = new ClassAdapter(classList);
        recyclerView.setAdapter(adapter);
    }

    // --- INNER CLASSES ---

    // Data Model
    public static class ClassModel {
        String code, name, details;
        public ClassModel(String code, String name, String details) {
            this.code = code;
            this.name = name;
            this.details = details;
        }
    }

    // Adapter Logic
    public static class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {
        private final List<ClassModel> list;

        public ClassAdapter(List<ClassModel> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflate your item_class layout
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ClassModel item = list.get(position);
            holder.txtCode.setText(item.code);
            holder.txtName.setText(item.name);
            holder.txtDetails.setText(item.details);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        // ViewHolder Logic
        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtCode, txtName, txtDetails;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                // Matching the IDs from your item_class.xml
                txtCode = itemView.findViewById(R.id.ClassCode);
                txtName = itemView.findViewById(R.id.ClasName);
                txtDetails = itemView.findViewById(R.id.ClassDetails);
            }
        }
    }
}