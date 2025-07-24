package com.example.MovieInABox.activity.authen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MovieInABox.R;
import com.example.MovieInABox.data.model.ApiResponse;
import com.example.MovieInABox.data.service.ApiService;
import com.example.MovieInABox.data.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {

    private EditText editTextOtp;
    private Button btnVerify;
    private ProgressDialog progressDialog;
    private String email; // To store the email passed from ForgotPasswordActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        initUi();

        // Retrieve the email from the intent
        email = getIntent().getStringExtra("email");

        initListener();
    }

    private void initUi() {
        editTextOtp = findViewById(R.id.edittext_otp);
        btnVerify = findViewById(R.id.btn_verify);
        progressDialog = new ProgressDialog(this);
    }

    private void initListener() {
        btnVerify.setOnClickListener(v -> validateAndSubmit());
    }

    private void validateAndSubmit() {
        String otp = editTextOtp.getText().toString().trim();

        if (otp.isEmpty()) {
            Toast.makeText(OtpActivity.this, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        sendVerifyOtpRequest(email, otp);
    }

    private void sendVerifyOtpRequest(String email, String otp) {
        progressDialog.setMessage("Đang kiểm tra mã OTP...");
        progressDialog.show();

        UserService userService = ApiService.createService(UserService.class);
        Call<ApiResponse> call = userService.verifyOtp(email, otp);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse res = response.body();
                    if (res.getCode() == 200) {
                        String accessToken = res.getAccessToken();
                        Intent intent = new Intent(OtpActivity.this, ChangePasswordActivity.class);
                        intent.putExtra("accessToken", accessToken);
                        startActivity(intent);
                    } else {
                        Toast.makeText(OtpActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OtpActivity.this, "Đã xảy ra lỗi, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OtpActivity.this, "Lỗi kết nối mạng, vui lòng thử lại", Toast.LENGTH_SHORT).show();
            }
        });
    }
} 