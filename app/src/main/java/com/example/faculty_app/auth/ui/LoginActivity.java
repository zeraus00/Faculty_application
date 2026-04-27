package com.example.faculty_app.auth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.faculty_app.R;
import com.example.faculty_app.auth.data.repositories.AuthRepository;
import com.example.faculty_app.auth.data.repositories.callbacks.AuthRepositoryCallback;
import com.example.faculty_app.auth.domain.AuthenticationException;
import com.example.faculty_app.auth.data.local.SessionManager;
import com.example.faculty_app.shared.BaseResult;

public class LoginActivity extends AppCompatActivity {

    SessionManager sessionManager;

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

        sessionManager = SessionManager.getInstance();

        // Initialize button
        Button btnlogin = findViewById(R.id.btn_login);
        EditText emailView = findViewById(R.id.et_login_email);
        EditText passwordView = findViewById(R.id.et_login_password);
        CheckBox rememberMeView = findViewById(R.id.checkbox_login_remember_me);
        ImageView TogglePassword = findViewById(R.id.toggle_login_password);

        TogglePassword.setOnClickListener(v -> {
            if (passwordView.getInputType() ==
                    (InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_VARIATION_PASSWORD)) {
                passwordView.setInputType(
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                TogglePassword.setImageResource(R.drawable.view);
            }
            else {
                passwordView.setInputType(
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                TogglePassword.setImageResource(R.drawable.hide);
            }

            passwordView.setSelection(passwordView.getText().length());
        });

        // Set click listener
        btnlogin.setOnClickListener(v -> {
            String email = emailView.getText().toString();
            String password = passwordView.getText().toString();
            boolean rememberMe = rememberMeView.isChecked();

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(LoginActivity.this,
                               "Email and password cannot be empty.",
                               Toast.LENGTH_LONG).show();
                return;
            }

            requestSignInCode(email, password, rememberMe);
        });

    }

    private void requestSignInCode(String email, String password, boolean rememberMe) {
        log("Requesting code...");
        AuthRepository.getInstance()
                      .requestSignInCode(email,
                                         password,
                                         rememberMe,
                                         new AuthRepositoryCallback<Void>() {
                                             @Override
                                             public void onResult(BaseResult<Void> result) {
                                                 runOnUiThread(() -> {
                                                     if (result instanceof BaseResult.Fail) {
                                                         var fail = (BaseResult.Fail<?,
                                                                 AuthenticationException>) result;
                                                         var exception = fail.getException();
                                                         Toast.makeText(LoginActivity.this,
                                                                        exception.getMessage(),
                                                                        Toast.LENGTH_LONG).show();
                                                         log(exception.getMessage(),
                                                             (Exception) exception.getCause());
                                                         return;
                                                     }

                                                     log("Success requesting code.");
                                                     Intent intent = new Intent(LoginActivity.this,
                                                                                EmailVerificationActivity.class);
                                                     startActivity(intent);
                                                     finish();
                                                 });
                                             }
                                         });
    }

    private void log(String message) {
        Log.d("LOGIN_ACTIVITY", message);
    }

    private void log(String message, Exception e) {
        Log.e("LOGIN_ACTIVITY", message, e);
    }
}