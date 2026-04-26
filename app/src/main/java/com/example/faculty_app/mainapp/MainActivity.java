package com.example.faculty_app.mainapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.faculty_app.R;
import com.example.faculty_app.auth.LoginActivity;
import com.example.faculty_app.core.auth.SessionManager;
import com.example.faculty_app.mainapp.home.HomePageActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        var accessToken = SessionManager.getInstance().getAccessToken();

        if (accessToken == null || accessToken.isEmpty()) {
            redirectToLogIn();
        } else {
            redirectToHome();
        }

        finish();
    }

    private void redirectToHome() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    private void redirectToLogIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}