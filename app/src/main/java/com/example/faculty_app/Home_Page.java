package com.example.faculty_app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;

public class Home_Page extends AppCompatActivity {

    private LinearLayout navHome, navClass, navProfile, navLogout;
    private ShapeableImageView btnNotification;

    private View headerView;
    private View profileImg;
    private View profName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        navHome = findViewById(R.id.navHome);
        navClass = findViewById(R.id.navClass);
        navProfile = findViewById(R.id.navProfile);
        navLogout = findViewById(R.id.navLogout);

        btnNotification = findViewById(R.id.btnNotification);
        headerView = findViewById(R.id.headerView);
        profileImg = findViewById(R.id.profileImg);
        profName = findViewById(R.id.profName);

        if (savedInstanceState == null) {
            showHeader();
            loadFragment(new home_fragment());
            setActiveTab(navHome);
        }

        btnNotification.setOnClickListener(v -> {
            showHeader();
            loadFragment(new Fragment_Notification());
        });

        navHome.setOnClickListener(v -> {
            showHeader();
            loadFragment(new home_fragment());
            setActiveTab(navHome);
        });

        navClass.setOnClickListener(v -> {
            showHeader();
            loadFragment(new AllClassesFragment());
            setActiveTab(navClass);
        });

        navProfile.setOnClickListener(v -> {
            hideHeader();
            loadFragment(new profile());
            setActiveTab(navProfile);
        });

        navLogout.setOnClickListener(v -> setActiveTab(navLogout));
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void showHeader() {
        if (headerView != null) headerView.setVisibility(View.VISIBLE);
        if (profileImg != null) profileImg.setVisibility(View.VISIBLE);
        if (profName != null) profName.setVisibility(View.VISIBLE);
        if (btnNotification != null) btnNotification.setVisibility(View.VISIBLE);
    }

    private void hideHeader() {
        if (headerView != null) headerView.setVisibility(View.GONE);
        if (profileImg != null) profileImg.setVisibility(View.GONE);
        if (profName != null) profName.setVisibility(View.GONE);
        if (btnNotification != null) btnNotification.setVisibility(View.GONE);
    }

    private void setActiveTab(LinearLayout active) {
        resetNavItem(navHome);
        resetNavItem(navClass);
        resetNavItem(navProfile);
        resetNavItem(navLogout);

        if (active != null) {
            highlightNavItem(active);
        }
    }

    private void resetNavItem(LinearLayout item) {
        if (item == null || item.getChildCount() < 2) return;

        ImageView icon = (ImageView) item.getChildAt(0);
        TextView label = (TextView) item.getChildAt(1);

        int blueColor = getColor(R.color.blue);
        icon.setColorFilter(blueColor);
        label.setTextColor(blueColor);
        item.setBackgroundColor(Color.TRANSPARENT);
    }

    private void highlightNavItem(LinearLayout item) {
        if (item == null || item.getChildCount() < 2) return;

        ImageView icon = (ImageView) item.getChildAt(0);
        TextView label = (TextView) item.getChildAt(1);

        int whiteColor = getColor(R.color.white);
        icon.setColorFilter(whiteColor);
        label.setTextColor(whiteColor);
        item.setBackgroundColor(getColor(R.color.blue));
    }

    public static class ClassModel {
        String code, name, details;

        public ClassModel(String code, String name, String details) {
            this.code = code;
            this.name = name;
            this.details = details;
        }
    }

    public static class ClassAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<ClassAdapter.ViewHolder> {
        private final java.util.List<ClassModel> list;

        public ClassAdapter(java.util.List<ClassModel> list) {
            this.list = list;
        }

        @androidx.annotation.NonNull
        @Override
        public ViewHolder onCreateViewHolder(@androidx.annotation.NonNull android.view.ViewGroup parent, int viewType) {
            View view = android.view.LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_class, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@androidx.annotation.NonNull ViewHolder holder, int position) {
            ClassModel item = list.get(position);
            holder.txtCode.setText(item.code);
            holder.txtName.setText(item.name);
            holder.txtDetails.setText(item.details);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public static class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            TextView txtCode, txtName, txtDetails;

            public ViewHolder(@androidx.annotation.NonNull View itemView) {
                super(itemView);
                txtCode = itemView.findViewById(R.id.ClassCode);
                txtName = itemView.findViewById(R.id.ClasName);
                txtDetails = itemView.findViewById(R.id.ClassDetails);
            }
        }
    }
}