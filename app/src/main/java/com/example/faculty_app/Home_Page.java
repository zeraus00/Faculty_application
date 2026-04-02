package com.example.faculty_app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class Home_Page extends AppCompatActivity {

    private LinearLayout navHome, navClass, navProfile, navLogout;
    private ShapeableImageView btnNotification;

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

        // Initialize Navigation Buttons
        navHome = findViewById(R.id.navHome);
        navClass = findViewById(R.id.navClass);
        navProfile = findViewById(R.id.navProfile);
        navLogout = findViewById(R.id.navLogout);
        btnNotification = findViewById(R.id.btnNotification);

        // Load the fragment on start
        if(savedInstanceState == null) {
            loadFragment(new home_fragment());
            setActiveTab(navHome);
        }

        // Notification Click Listener
        btnNotification.setOnClickListener(v -> {
            loadFragment(new Fragment_Notification());
        });

        //Click Listeners for navigation
        navHome.setOnClickListener(v -> {
            loadFragment(new home_fragment());
            setActiveTab(navHome);
        });

        navClass.setOnClickListener(v -> setActiveTab(navClass));
        navProfile.setOnClickListener(v -> setActiveTab(navProfile));
        navLogout.setOnClickListener(v -> setActiveTab(navLogout));
    }

    // helper methods
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void setActiveTab(LinearLayout active) {
        resetNavItem(navHome);
        resetNavItem(navClass);
        resetNavItem(navProfile);
        resetNavItem(navLogout);
        if (active != null) highlightNavItem(active);
    }

    private void resetNavItem(LinearLayout item) {
        if (item == null) return;
        ImageView icon = (ImageView) item.getChildAt(0);
        TextView label = (TextView) item.getChildAt(1);

        int blueColor = getColor(R.color.blue);
        icon.setColorFilter(blueColor);
        label.setTextColor(blueColor);
        item.setBackgroundColor(Color.TRANSPARENT);
    }

    private void highlightNavItem(LinearLayout item) {
        if (item == null) return;
        ImageView icon = (ImageView) item.getChildAt(0);
        TextView label = (TextView) item.getChildAt(1);

        int whiteColor = getColor(R.color.white);
        icon.setColorFilter(whiteColor);
        label.setTextColor(whiteColor);
        item.setBackgroundColor(getColor(R.color.blue));
    }

    // Inner classes (data)
    public static class ClassModel {
        String code, name, details;
        public ClassModel(String code, String name, String details) {
            this.code = code;
            this.name = name;
            this.details = details;
        }
    }

    public static class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {
        private final List<ClassModel> list;

        public ClassAdapter(List<ClassModel> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false);
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
        public int getItemCount(){
            return list.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtCode, txtName, txtDetails;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtCode = itemView.findViewById(R.id.ClassCode);
                txtName = itemView.findViewById(R.id.ClasName);
                txtDetails = itemView.findViewById(R.id.ClassDetails);
            }
        }
    }
}