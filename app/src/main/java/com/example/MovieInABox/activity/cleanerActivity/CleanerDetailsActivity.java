package com.example.MovieInABox.activity.cleanerActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MovieInABox.R;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CleanerDetailsActivity extends AppCompatActivity {

    private ProgressBar loadingSpinner;
    private TextView fullName, employeeId, emailValue, phoneValue, experienceValue, areaValue, statusValue;
    private ImageView avatar;
    private OkHttpClient client = new OkHttpClient();
    private String cleanerId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_details);

        // Get cleanerId from Intent
        cleanerId = getIntent().getStringExtra("cleanerId");

        // Bind UI
        loadingSpinner = findViewById(R.id.loadingSpinner);
        fullName = findViewById(R.id.fullName);
        employeeId = findViewById(R.id.employeeId);
        emailValue = findViewById(R.id.emailValue);
        phoneValue = findViewById(R.id.phoneValue);
        experienceValue = findViewById(R.id.experienceValue);
        areaValue = findViewById(R.id.areaValue);
        statusValue = findViewById(R.id.statusValue);
        avatar = findViewById(R.id.avatar);

        ImageButton closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> finish());

        fetchCleanerDetails();
    }

    private void fetchCleanerDetails() {
        loadingSpinner.setVisibility(View.VISIBLE);

        String url = "https://yourserver.com/employeelist/" + cleanerId + "/detail";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> loadingSpinner.setVisibility(View.GONE));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonResponse = response.body().string();

                try {
                    JSONObject obj = new JSONObject(jsonResponse);
                    String name = obj.optString("fullName");
                    String id = obj.optString("cleanerId");
                    String email = obj.optString("email");
                    String phone = obj.optString("phoneNumber");
                    int exp = obj.optInt("experienceYear", 0);
                    String area = obj.optString("area");
                    boolean available = obj.optBoolean("available", false);

                    runOnUiThread(() -> {
                        fullName.setText(name);
                        employeeId.setText("Mã nhân viên: " + id);
                        emailValue.setText(email);
                        phoneValue.setText(phone);
                        experienceValue.setText(exp == 0 ? "Chưa có kinh nghiệm" : exp + " năm");
                        areaValue.setText(area);
                        statusValue.setText(available ? "Khả dụng" : "Không khả dụng");
                        statusValue.setTextColor(getResources().getColor(available ? android.R.color.holo_green_dark : android.R.color.holo_red_dark));

                        avatar.setImageResource(R.drawable.user_image);
                        loadingSpinner.setVisibility(View.GONE);
                    });
                } catch (Exception e) {
                    runOnUiThread(() -> loadingSpinner.setVisibility(View.GONE));
                }
            }
        });
    }
}
