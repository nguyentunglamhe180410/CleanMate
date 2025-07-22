package com.example.cleanmate;

import android.app.DownloadManager;
import android.content.SharedPreferences;
import android.view.PixelCopy;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanmate.R;
import com.example.cleanmate.data.model.Booking;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BookingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookingAdapter adapter;
    private final OkHttpClient client = new OkHttpClient();
    private final String BASE_URL = "https://localhost:60391/api";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        recyclerView = findViewById(R.id.recyclerViewBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new BookingAdapter(this, booking -> confirmComplete(booking.getBookingId()));
        recyclerView.setAdapter(adapter);
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userId = prefs.getString("userId", null);
        if (userId != null) {
            loadBookings(userId);
        } else {
            Toast.makeText(this, "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadBookings(String userId) {
        String url = BASE_URL + "/bookings/get-bookings?userId=" + userId;

        Request request = new Request.Builder().url(url).get().build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Booking>>() {}.getType();
                    List<Booking> bookings = gson.fromJson(json, listType);

                    runOnUiThread(() -> adapter.setData(bookings));
                }
            }
        });
    }

    private void confirmComplete(int bookingId) {
        String url = BASE_URL + "/bookings/" + bookingId + "/confirm-complete-work";

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create("", null))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        Toast.makeText(BookingActivity.this, "Đã xác nhận hoàn thành!", Toast.LENGTH_SHORT).show();
                        loadBookings("user-id-abc"); // reload lại danh sách
                    });
                }
            }
        });
    }
}

