package com.example.cleanmate.activity.customerActivity;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanmate.adapter.BookingAdapter;
import com.example.cleanmate.R;
import com.example.cleanmate.data.model.Booking;
import com.example.cleanmate.data.repository.BookingRepository;
import com.example.cleanmate.data.service.BookingService;
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

    // 1. Khởi tạo service/repo
    private BookingService bookingService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // 2. Khởi tạo repo và service
        try {
            BookingRepository repo = new BookingRepository(); // kết nối trực tiếp đến Azure DB
            bookingService = new BookingService(repo);
        } catch (Exception e) {
            Toast.makeText(this, "Không khởi tạo được service: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        recyclerView = findViewById(R.id.recyclerViewBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookingAdapter(this, id -> confirmComplete(id));
        recyclerView.setAdapter(adapter);

        String userId = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                .getString("userId", null);
        if (userId != null) {
            loadBookings(userId);
        } else {
            Toast.makeText(this, "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadBookings(String userId) {
        // 3. Chạy trên background thread
        new Thread(() -> {
            try {
                List<Booking> bookings = bookingService.getBookingsByUser(userId);
                runOnUiThread(() -> adapter.setData(bookings));
            } catch (SQLException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this,
                        "Load thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void confirmComplete(int bookingId) {
        new Thread(() -> {
            try {
                boolean ok = bookingService.confirmComplete(bookingId);
                runOnUiThread(() -> {
                    if (ok) {
                        Toast.makeText(this, "Đã xác nhận hoàn thành!", Toast.LENGTH_SHORT).show();
                        // reload danh sách
                        String userId = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                                .getString("userId", null);
                        if (userId != null) loadBookings(userId);
                    } else {
                        Toast.makeText(this, "Xác nhận thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }
}


