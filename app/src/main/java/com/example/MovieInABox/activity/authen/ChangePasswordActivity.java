package com.example.MovieInABox.activity.authen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MovieInABox.R;
import com.example.MovieInABox.model.ApiResponse;
import com.example.MovieInABox.service.ApiService;
import com.example.MovieInABox.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText editTextNewPassword, editTextConfirmPassword;
    private Button btnChangePassword;
    private ProgressDialog progressDialog;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initUi();

        // Retrieve the accessToken from the intent
        accessToken = getIntent().getStringExtra("accessToken");

        initListener();
    }

    private void initUi() {
        editTextNewPassword = findViewById(R.id.edittext_new_password);
        editTextConfirmPassword = findViewById(R.id.edittext_confirm_password);
        btnChangePassword = findViewById(R.id.btn_change_password);
        progressDialog = new ProgressDialog(this);
    }

    private void initListener() {
        btnChangePassword.setOnClickListener(v -> validateAndSubmit());
    }

    private void validateAndSubmit() {
        String newPassword = editTextNewPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(newPassword)) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        sendChangePasswordRequest(newPassword);
    }

    private void sendChangePasswordRequest(String newPassword) {
        progressDialog.setMessage("Đang đổi mật khẩu...");
        progressDialog.show();

        UserService userService = ApiService.createService(UserService.class);
        Call<ApiResponse> call = userService.changePassword("Bearer " + accessToken, newPassword);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse res = response.body();
                    Toast.makeText(ChangePasswordActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                    if (res.getCode() == 200) {
                        Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // Close the current activity
                    }
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Đã xảy ra lỗi, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ChangePasswordActivity.this, "Lỗi kết nối mạng, vui lòng thử lại", Toast.LENGTH_SHORT).show();
            }
        });
    }
} 