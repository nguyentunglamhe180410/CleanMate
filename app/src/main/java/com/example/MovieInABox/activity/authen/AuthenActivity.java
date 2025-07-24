package com.example.MovieInABox.activity.authen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MovieInABox.MainActivity;
import com.example.MovieInABox.R;
import com.example.MovieInABox.data.model.User;
import com.example.MovieInABox.data.model.viewmodels.authen.AuthResult;
import com.example.MovieInABox.data.model.viewmodels.authen.LoginModel;
import com.example.MovieInABox.data.model.viewmodels.authen.RegisterModel;
import com.example.MovieInABox.data.service.AuthService;

public class AuthenActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword, edtPhone, edtFullName;
    private Button btnLogin, btnRegister;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authen);

        authService = new AuthService();

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtPhone = findViewById(R.id.edtPhone);
        edtFullName = findViewById(R.id.edtFullName);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v -> handleLogin());
        btnRegister.setOnClickListener(v -> handleRegister());
    }

    private void handleLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        LoginModel model = new LoginModel(email, password);
        new Thread(() -> {
            AuthResult result = authService.login(model);
            runOnUiThread(() -> {
                if (result.isSuccess()) {
                    User user = result.getUser();
                    saveUser(user);
                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AuthenActivity.this, MainActivity.class));
                } else {
                    String errorMessage = result.getErrors() != null && !result.getErrors().isEmpty()
                            ? result.getErrors().get(0)
                            : "Đã xảy ra lỗi!";
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    private void handleRegister() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String fullName = edtFullName.getText().toString().trim();

        RegisterModel model = new RegisterModel();
        model.setEmail(email);
        model.setPassword(password);
        model.setPhoneNumber(phone);
        model.setFullName(fullName);

        new Thread(() -> {
            AuthResult result = authService.registerCustomer(model);
            runOnUiThread(() -> {
                if (result.isSuccess()) {
                    Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    edtPassword.setText("");
                } else {
                    String errorMessage = result.getErrors() != null && !result.getErrors().isEmpty()
                            ? result.getErrors().get(0)
                            : "Đã xảy ra lỗi!";
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
                }
            });
        }).start();
    }

    private void saveUser(User user) {
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userId", user.getUserId());
        editor.putString("email", user.getEmail());
        editor.putString("fullName", user.getFullName());
        editor.putString("phoneNumber", user.getPhoneNumber());
        editor.apply();
    }
}
