package com.example.faculty_app.mainapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.faculty_app.R;
import com.example.faculty_app.auth.ui.LoginActivity;
import com.example.faculty_app.auth.data.local.SessionManager;
import com.example.faculty_app.auth.data.repositories.AuthRepository;
import com.example.faculty_app.auth.data.remote.models.response.Tokens;
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

        var hasAccessToken = accessToken != null && !accessToken.isEmpty();

        if (hasAccessToken) {
            redirectToHome();
        }
        else {
            refreshOnDemand();
        }
    }

    private void refreshOnDemand() {
        AuthRepository.getInstance().refreshAsync(new AuthRepository.RefreshCallback() {
            @Override
            public void onSuccess(Tokens tokens) {
                runOnUiThread(() -> {
                    redirectToHome();
                    finish();
                });
            }

            @Override
            public void onFail(String message) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Failed authentication", Toast.LENGTH_LONG)
                         .show();
                    log(message);
                    redirectToLogIn();
                    finish();
                });
            }
        });
    }

    private void redirectToHome() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    private void redirectToLogIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void log(String message) {
        Log.d("MAIN_ACTIVITY", message);
    }
}