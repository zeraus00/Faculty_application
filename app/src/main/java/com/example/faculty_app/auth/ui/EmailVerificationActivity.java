package com.example.faculty_app.auth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.faculty_app.R;
import com.example.faculty_app.auth.data.repositories.AuthRepository;
import com.example.faculty_app.auth.domain.AuthenticationException;
import com.example.faculty_app.auth.data.local.SessionManager;
import com.example.faculty_app.shared.RepositoryCallback;
import com.example.faculty_app.shared.RepositoryResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmailVerificationActivity extends AppCompatActivity {
    SessionManager sessionManager;
    private CountDownTimer timer;
    private Button btnResend;
    private TextView timerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_email_verification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sessionManager = SessionManager.getInstance();
        String email = sessionManager.getEmail();

        if (email == null) {
            Toast.makeText(EmailVerificationActivity.this,
                           "Something went wrong. Please try again later.",
                           Toast.LENGTH_LONG).show();
            redirectToLogin();
            return;
        }

        TextView emailView = findViewById(R.id.txt_email_verification_email);
        timerView = findViewById(R.id.txt_email_verification_timer);
        AppCompatButton btnSubmit = findViewById(R.id.btn_email_verification_submit);
        btnResend = findViewById(R.id.btn_email_verification_resend);
        ImageButton btnBack = findViewById(R.id.btn_email_verification_back);
        EditText digit1 = findViewById(R.id.et_email_verification_digit_1);
        EditText digit2 = findViewById(R.id.et_email_verification_digit_2);
        EditText digit3 = findViewById(R.id.et_email_verification_digit_3);
        EditText digit4 = findViewById(R.id.et_email_verification_digit_4);
        EditText digit5 = findViewById(R.id.et_email_verification_digit_5);
        EditText digit6 = findViewById(R.id.et_email_verification_digit_6);

        startTimer();
        emailView.setText(email);

        btnSubmit.setOnClickListener((view) -> {
            String code = assembleCode(new ArrayList<>(Arrays.asList(digit1,
                                                                     digit2,
                                                                     digit3,
                                                                     digit4,
                                                                     digit5,
                                                                     digit6)));

            boolean rememberMe = sessionManager.getRememberMe();
            verifyCode(email, code, rememberMe);
        });

        btnResend.setOnClickListener((view) -> {
            //  todo: handle resend.
            stopTimer();
            startTimer();
        });

        btnBack.setOnClickListener((view) -> {
            stopTimer();
            redirectToLogin();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    private String assembleCode(List<EditText> ets) {
        StringBuilder code = new StringBuilder();

        for (EditText et : new ArrayList<>(ets)) {
            code.append(et.getText().toString());
        }

        return code.toString();
    }

    private void verifyCode(String email, String code, boolean rememberMe) {
        log("Verifying sign in request code.");
        AuthRepository.getInstance()
                      .verifyCode(email, code, rememberMe, new RepositoryCallback<Void>() {
                          @Override
                          public void onResult(RepositoryResult<Void> result) {
                              runOnUiThread(() -> {
                                  if (result instanceof RepositoryResult.Fail) {
                                      var fail = (RepositoryResult.Fail<?,
                                              AuthenticationException>) result;
                                      var exception = fail.getException();

                                      log(exception.getMessage(), exception.getCause());
                                      Toast.makeText(EmailVerificationActivity.this,
                                                     exception.getMessage(),
                                                     Toast.LENGTH_LONG).show();
                                      return;
                                  }

                                  log("Success verifying code.");
                                  redirectToSuccessScreen();
                              });
                          }
                      });
    }

    private void startTimer() {
        stopTimer();
        btnResend.setEnabled(false);
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onFinish() {
                timerView.setText("00:00");
                btnResend.setEnabled(true);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                timerView.setText(String.format("00:%s", String.format("%02d", seconds)));
            }
        };
        timer.start();
    }

    private void stopTimer() {
        if (timer != null)
            timer.cancel();
    }

    private void redirectToLogin() {
        Intent intent = new Intent(EmailVerificationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void redirectToSuccessScreen() {
        Intent intent = new Intent(EmailVerificationActivity.this, EmailVerifiedActivity.class);
        startActivity(intent);
        finish();
    }

    private void log(String message) {
        Log.d("EMAIL_VERIFICATION_ACTIVITY", message);
    }

    private void log(String message, @Nullable Throwable t) {
        Log.e("EMAIL_VERIFICATION_ACTIVITY", message, t);
    }
}