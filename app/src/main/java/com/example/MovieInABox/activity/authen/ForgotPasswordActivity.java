package com.example.MovieInABox.activity.authen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MovieInABox.R;
import com.example.MovieInABox.model.ApiResponse;
import com.example.MovieInABox.service.ApiService;
import com.example.MovieInABox.service.UserService;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private TextInputLayout textInputLayoutEmail;
    private Button btnSubmit;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initUi();
        initListener();
    }

    private void initUi() {
        editTextEmail = findViewById(R.id.edittext_forgot_email);
        textInputLayoutEmail = findViewById(R.id.tiplayout_forgot_email);
        btnSubmit = findViewById(R.id.btn_submit);
        progressDialog = new ProgressDialog(this);
    }

    private void initListener() {
        btnSubmit.setOnClickListener(v -> validateAndSubmit());
    }

    private void validateAndSubmit() {
        String email = editTextEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            textInputLayoutEmail.setError("Email không được để trống");
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputLayoutEmail.setError("Email không hợp lệ");
            return;
        } else {
            textInputLayoutEmail.setErrorEnabled(false);
        }

        sendForgotPasswordRequest(email);
    }

    private void sendForgotPasswordRequest(String email) {
        progressDialog.setMessage("Đang gửi yêu cầu...");
        progressDialog.show();

        UserService userService = ApiService.createService(UserService.class);
        Call<ApiResponse> call = userService.forgotPassword(email);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse res = response.body();
                    Toast.makeText(ForgotPasswordActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();

                    // Navigate to OtpActivity
                    Intent intent = new Intent(ForgotPasswordActivity.this, OtpActivity.class);
                    intent.putExtra("email", email); // Pass email to the next screen
                    startActivity(intent);
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Đã xảy ra lỗi, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, "Lỗi kết nối mạng, vui lòng thử lại", Toast.LENGTH_SHORT).show();
            }
        });
    }
} 