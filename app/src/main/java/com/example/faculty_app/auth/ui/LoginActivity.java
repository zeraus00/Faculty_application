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
import com.example.faculty_app.core.api.axis.dto.HttpCallback;
import com.example.faculty_app.core.api.axis.dto.response.VoidResponse;
import com.example.faculty_app.auth.data.remote.api.AuthApi;
import com.example.faculty_app.auth.data.local.SessionManager;
import com.example.faculty_app.auth.data.remote.models.request.SignInCodeRequest;

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

            var request = assembleRequest(email, password, rememberMe);
            requestSignInCode(request);
        });

    }

    private SignInCodeRequest assembleRequest(String email, String password, boolean rememberMe) {
        var request = new SignInCodeRequest();
        request.identifier = email;
        request.password = password;
        request.isPersistentAuth = rememberMe;

        return request;
    }

    private void requestSignInCode(SignInCodeRequest request) {
        log("Requesting code...");
        AuthApi.requestSignInCode(request, new HttpCallback<VoidResponse>() {
            @Override
            public void onSuccess(VoidResponse response) {
                runOnUiThread(() -> {
                    if (response.success) {
                        log("Success requesting code.");
                        sessionManager.setEmail(request.identifier);
                        sessionManager.setRememberMe(request.isPersistentAuth);
                    }
                    else {
                        log("Failed requesting code.");
                        Toast.makeText(LoginActivity.this, response.message, Toast.LENGTH_LONG)
                             .show();
                        sessionManager.clear();
                        return;
                    }

                    try {
                        Intent intent = new Intent(LoginActivity.this,
                                                   EmailVerificationActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        logError("Failed opening email verification screen.", e);
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            public void onError(String message) {
                runOnUiThread(() -> {
                    log("Request failed:" + message);
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void log(String message) {
        Log.d("LOGIN_ACTIVITY", message);
    }

    private void logError(String message, Exception e) {
        Log.e("LOGIN_ACTIVITY", message, e);
    }
}