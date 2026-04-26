package com.example.faculty_app.mainapp.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;
import androidx.fragment.app.Fragment;

import com.example.faculty_app.R;
import com.example.faculty_app.auth.LoginActivity;
import com.example.faculty_app.core.auth.SessionManager;
import com.example.faculty_app.mainapp.classes.AllClassesFragment;
import com.example.faculty_app.mainapp.classes.CurrentClassWithListFragment;
import com.example.faculty_app.mainapp.misc.NotificationFragment;
import com.example.faculty_app.mainapp.misc.ProfileFragment;
import com.google.android.material.imageview.ShapeableImageView;

public class HomePageActivity extends AppCompatActivity {

    private LinearLayout navHome, navClass, navProfile, navLogout;
    private ShapeableImageView btnNotification, profileImg;
    private TextView profName;
    private final Runnable logoutListener = () -> {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        SessionManager.getInstance().addLogoutListener(logoutListener);

        // Standard Edge-to-Edge Padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Views
        navHome = findViewById(R.id.navHome);
        navClass = findViewById(R.id.navClass);
        navProfile = findViewById(R.id.navProfile);
        navLogout = findViewById(R.id.navLogout);
        btnNotification = findViewById(R.id.btnNotification);
        profileImg = findViewById(R.id.profileImg);
        profName = findViewById(R.id.profName);

        // Default Page
        if (savedInstanceState == null) {
            loadFragment(new CurrentClassWithListFragment());
            setActiveTab(navHome);
        }

        // --- Click Listeners ---

        navHome.setOnClickListener(v -> {
            loadFragment(new CurrentClassWithListFragment());
            setActiveTab(navHome);
        });

        navClass.setOnClickListener(v -> {
            loadFragment(new AllClassesFragment());
            setActiveTab(navClass);
        });

        navProfile.setOnClickListener(v -> {
            loadFragment(new ProfileFragment());
            setActiveTab(navProfile);
        });

        btnNotification.setOnClickListener(v -> {
            loadFragment(new NotificationFragment());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SessionManager.getInstance().removeLogoutListener(logoutListener);
    }

    /**
     * Simplified Fragment Loader
     * Automatically hides the header if the fragment is the Profile page
     */
    public void loadFragment(Fragment fragment) {
        // Logic: If the fragment is 'profile', hide the header. Otherwise, show it.
        int visibility = (fragment instanceof ProfileFragment) ? View.GONE : View.VISIBLE;

        profileImg.setVisibility(visibility);
        profName.setVisibility(visibility);
        btnNotification.setVisibility(visibility);

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
        if (item == null || item.getChildCount() < 2) return;
        ImageView icon = (ImageView) item.getChildAt(0);
        TextView label = (TextView) item.getChildAt(1);
        icon.setColorFilter(getColor(R.color.blue));
        label.setTextColor(getColor(R.color.blue));
        item.setBackgroundColor(Color.TRANSPARENT);
    }

    private void highlightNavItem(LinearLayout item) {
        if (item == null || item.getChildCount() < 2) return;
        ImageView icon = (ImageView) item.getChildAt(0);
        TextView label = (TextView) item.getChildAt(1);
        icon.setColorFilter(getColor(R.color.white));
        label.setTextColor(getColor(R.color.white));
        item.setBackgroundColor(getColor(R.color.blue));
    }
}