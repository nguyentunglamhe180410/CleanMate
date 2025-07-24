package com.example.MovieInABox.activity.customerActivity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MovieInABox.adapter.BookingAdapter;
import com.example.MovieInABox.R;
import com.example.MovieInABox.data.model.dto.dto;
import com.example.MovieInABox.data.repository.BookingRepository;
import com.example.MovieInABox.data.repository.CustomerRepository;
import com.example.MovieInABox.data.service.BookingService;

import java.sql.SQLException;
import java.util.List;

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
            bookingService = new BookingService(repo, new CustomerRepository());
        } catch (Exception e) {
            Toast.makeText(this, "Không khởi tạo được service: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        recyclerView = findViewById(R.id.recyclerViewBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookingAdapter(this, id -> confirmComplete(id.getBookingId()));
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
                List<dto.BookingDTO> bookings = bookingService.getBookingsByUserIdDto(userId, null);
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
                boolean ok = bookingService.doneCustomerAccept(bookingId);
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


