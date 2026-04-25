package com.example.faculty_app.mainapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.faculty_app.R;

public class login extends AppCompatActivity {

    private Button btnlogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize button
        btnlogin = findViewById(R.id.btnlogin);
        EditText passwordView = findViewById(R.id.editTextTextPassword);
        ImageView TogglePassword = findViewById(R.id.ivTogglePassword);

        TogglePassword.setOnClickListener(v -> {
            if(passwordView.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_VARIATION_PASSWORD)) {
                passwordView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                TogglePassword.setImageResource(R.drawable.view);
            }
            else {
                passwordView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                TogglePassword.setImageResource(R.drawable.hide);
            }

            passwordView.setSelection(passwordView.getText().length());
        });

        // Set click listener
        btnlogin.setOnClickListener(v -> {
            Intent intent = new Intent(login.this, Home_Page.class);
            startActivity(intent);
        });

    }
}