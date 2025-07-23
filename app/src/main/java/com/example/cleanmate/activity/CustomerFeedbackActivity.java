package com.example.cleanmate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanmate.R;
import com.example.cleanmate.adapter.FeedbackAdapter;
import com.example.cleanmate.data.model.Feedback;
import com.example.cleanmate.data.repository.FeedbackRepository;
import com.example.cleanmate.data.service.CustomerFeedbackService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerFeedbackActivity extends AppCompatActivity implements FeedbackAdapter.OnFeedbackClickListener {
    
    private CustomerFeedbackService customerFeedbackService;
    private String currentUserId;
    
    // UI Components
    private EditText etSearch;
    private Button btnAddFeedback;
    private Button btnMyFeedbacks;
    private RecyclerView rvFeedbacks;
    private View emptyState;
    private FeedbackAdapter feedbackAdapter;
    
    // Data
    private List<Feedback> allFeedbacks;
    private List<Feedback> filteredFeedbacks;
    private boolean isShowingMyFeedbacks = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_feedback);
        
        // Lấy userId từ Intent
        currentUserId = getIntent().getStringExtra("USER_ID");
        if (currentUserId == null) {
            Toast.makeText(this, "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
        initializeViews();
        setupClickListeners();
        setupSearch();
        initializeService();
        loadFeedbacks();
    }
    
    private void initializeViews() {
        etSearch = findViewById(R.id.et_search);
        btnAddFeedback = findViewById(R.id.btn_add_feedback);
        btnMyFeedbacks = findViewById(R.id.btn_my_feedbacks);
        rvFeedbacks = findViewById(R.id.rv_feedbacks);
        emptyState = findViewById(R.id.empty_state);
        
        // Setup RecyclerView
        feedbackAdapter = new FeedbackAdapter(new ArrayList<>(), this);
        rvFeedbacks.setLayoutManager(new LinearLayoutManager(this));
        rvFeedbacks.setAdapter(feedbackAdapter);
    }
    
    private void setupClickListeners() {
        btnAddFeedback.setOnClickListener(v -> showAddFeedbackDialog());
        btnMyFeedbacks.setOnClickListener(v -> showMyFeedbacks());
    }
    
    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterFeedbacks(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    
    private void filterFeedbacks(String searchText) {
        if (allFeedbacks != null) {
            filteredFeedbacks = allFeedbacks.stream()
                    .filter(feedback -> feedback.getContent().toLowerCase().contains(searchText.toLowerCase()))
                    .toList();
            displayFeedbacks();
        }
    }
    
    private void initializeService() {
        Toast.makeText(this, "Đang khởi tạo service...", Toast.LENGTH_SHORT).show();
        
        // Chạy database connection trên background thread
        new Thread(() -> {
            try {
                FeedbackRepository feedbackRepo = new FeedbackRepository();
                
                // Debug: Kiểm tra cấu trúc bảng
                System.out.println("DEBUG: Kiểm tra cấu trúc bảng Feedback...");
                feedbackRepo.debugTableStructure();
                
                CustomerFeedbackService service = new CustomerFeedbackService(feedbackRepo);
                
                // Chuyển về main thread để update UI
                runOnUiThread(() -> {
                    customerFeedbackService = service;
                    System.out.println("DEBUG: CustomerFeedbackService đã được khởi tạo thành công");
                    Toast.makeText(this, "Đã khởi tạo service thành công", Toast.LENGTH_SHORT).show();
                    loadFeedbacks();
                });
                
            } catch (ClassNotFoundException e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Lỗi driver database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    finish();
                });
            } catch (SQLException e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Lỗi kết nối database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    finish();
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Lỗi khởi tạo service: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    finish();
                });
            }
        }).start();
    }
    
    private void loadFeedbacks() {
        System.out.println("DEBUG: loadFeedbacks() được gọi");
        System.out.println("DEBUG: customerFeedbackService = " + (customerFeedbackService != null ? "NOT NULL" : "NULL"));
        
        if (customerFeedbackService == null) {
            Toast.makeText(this, "CustomerFeedbackService chưa được khởi tạo", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Chạy database query trên background thread
        new Thread(() -> {
            try {
                // Tạm thời tạo dummy data vì chưa có method getAllFeedbacks
                List<Feedback> feedbacks = createDummyFeedbacks();
                
                // Chuyển về main thread để update UI
                runOnUiThread(() -> {
                    if (feedbacks == null) {
                        Toast.makeText(this, "Không thể tải danh sách feedback", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    allFeedbacks = feedbacks;
                    filteredFeedbacks = new ArrayList<>(allFeedbacks);
                    Toast.makeText(this, "Đã tải " + allFeedbacks.size() + " feedback", Toast.LENGTH_SHORT).show();
                    showMyFeedbacks();
                });
                
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Lỗi tải feedback: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
            }
        }).start();
    }
    
    private List<Feedback> createDummyFeedbacks() {
        List<Feedback> feedbacks = new ArrayList<>();
        
        // Tạo dummy feedbacks cho testing
        Feedback f1 = new Feedback();
        f1.setFeedbackId(1);
        f1.setBookingId(101);
        f1.setUserId(currentUserId);
        f1.setCleanerId("cleaner-001");
        f1.setRating(4.5);
        f1.setContent("Dịch vụ rất tốt, cleaner làm việc chuyên nghiệp và sạch sẽ!");
        f1.setCreatedAt(java.sql.Timestamp.valueOf("2024-01-15 10:30:00"));
        
        Feedback f2 = new Feedback();
        f2.setFeedbackId(2);
        f2.setBookingId(102);
        f2.setUserId(currentUserId);
        f2.setCleanerId("cleaner-002");
        f2.setRating(5.0);
        f2.setContent("Tuyệt vời! Nhà cửa sạch bong sau khi dọn dẹp.");
        f2.setCreatedAt(java.sql.Timestamp.valueOf("2024-01-20 14:15:00"));
        
        feedbacks.add(f1);
        feedbacks.add(f2);
        
        return feedbacks;
    }
    
    private void showMyFeedbacks() {
        isShowingMyFeedbacks = true;
        btnMyFeedbacks.setBackgroundResource(R.drawable.tab_button_active);
        
        if (filteredFeedbacks != null) {
            displayFeedbacks();
        }
    }
    
    private void displayFeedbacks() {
        if (filteredFeedbacks == null || filteredFeedbacks.isEmpty()) {
            showEmptyState();
            return;
        }
        
        hideEmptyState();
        feedbackAdapter.updateFeedbacks(filteredFeedbacks);
    }
    
    private void showEmptyState() {
        emptyState.setVisibility(View.VISIBLE);
        rvFeedbacks.setVisibility(View.GONE);
    }
    
    private void hideEmptyState() {
        emptyState.setVisibility(View.GONE);
        rvFeedbacks.setVisibility(View.VISIBLE);
    }
    
    private void showAddFeedbackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm Feedback Mới");
        
        // Tạo custom view cho dialog
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_feedback, null);
        builder.setView(dialogView);
        
        // Lấy các view từ dialog
        TextView tvRatingLabel = dialogView.findViewById(R.id.tv_rating_label);
        EditText etBookingId = dialogView.findViewById(R.id.et_booking_id);
        EditText etCleanerId = dialogView.findViewById(R.id.et_cleaner_id);
        RatingBar ratingBar = dialogView.findViewById(R.id.rating_bar);
        EditText etContent = dialogView.findViewById(R.id.et_content);
        
        // Setup rating label
        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            String[] labels = {"", "Rất không hài lòng", "Không hài lòng", "Bình thường", "Hài lòng", "Rất hài lòng"};
            int index = (int) rating;
            if (index >= 0 && index < labels.length) {
                tvRatingLabel.setText(labels[index]);
            }
        });
        
        builder.setPositiveButton("Thêm", (dialog, which) -> {
            try {
                int bookingId = Integer.parseInt(etBookingId.getText().toString());
                String cleanerId = etCleanerId.getText().toString().trim();
                float rating = ratingBar.getRating();
                String content = etContent.getText().toString().trim();
                
                if (cleanerId.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập Cleaner ID", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                if (content.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập nội dung feedback", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                addFeedback(bookingId, cleanerId, rating, content);
                
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Vui lòng nhập Booking ID hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });
        
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());
        builder.show();
    }
    
    private void addFeedback(int bookingId, String cleanerId, double rating, String content) {
        try {
            boolean success = customerFeedbackService.addFeedback(bookingId, currentUserId, cleanerId, rating, content);
            if (success) {
                Toast.makeText(this, "Thêm feedback thành công!", Toast.LENGTH_SHORT).show();
                loadFeedbacks(); // Refresh danh sách
            } else {
                Toast.makeText(this, "Thêm feedback thất bại", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Toast.makeText(this, "Lỗi thêm feedback: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    // FeedbackAdapter.OnFeedbackClickListener implementation
    @Override
    public void onFeedbackClick(Feedback feedback) {
        showFeedbackDetails(feedback);
    }
    
    @Override
    public void onEditClick(Feedback feedback) {
        showEditFeedbackDialog(feedback);
    }
    
    @Override
    public void onDeleteClick(Feedback feedback) {
        showDeleteConfirmationDialog(feedback);
    }
    
    private void showFeedbackDetails(Feedback feedback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chi Tiết Feedback");
        
        String details = "Booking ID: " + feedback.getBookingId() + "\n" +
                "Cleaner ID: " + feedback.getCleanerId() + "\n" +
                "Rating: " + feedback.getRating() + "/5.0\n" +
                "Nội dung: " + feedback.getContent() + "\n" +
                "Ngày tạo: " + feedback.getCreatedAt();
        
        builder.setMessage(details);
        builder.setPositiveButton("Chỉnh Sửa", (dialog, which) -> showEditFeedbackDialog(feedback));
        builder.setNegativeButton("Đóng", (dialog, which) -> dialog.cancel());
        builder.show();
    }
    
    private void showEditFeedbackDialog(Feedback feedback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chỉnh Sửa Feedback");
        
        // Tạo custom view cho dialog
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_feedback, null);
        builder.setView(dialogView);
        
        // Lấy các view từ dialog
        TextView tvRatingLabel = dialogView.findViewById(R.id.tv_rating_label);
        RatingBar ratingBar = dialogView.findViewById(R.id.rating_bar);
        EditText etContent = dialogView.findViewById(R.id.et_content);
        
        // Set giá trị hiện tại
        float currentRating = feedback.getRating().floatValue();
        ratingBar.setRating(currentRating);
        etContent.setText(feedback.getContent());
        
        // Setup rating label
        String[] labels = {"", "Rất không hài lòng", "Không hài lòng", "Bình thường", "Hài lòng", "Rất hài lòng"};
        int index = (int) currentRating;
        if (index >= 0 && index < labels.length) {
            tvRatingLabel.setText(labels[index]);
        }
        
        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            int newIndex = (int) rating;
            if (newIndex >= 0 && newIndex < labels.length) {
                tvRatingLabel.setText(labels[newIndex]);
            }
        });
        
        builder.setPositiveButton("Cập Nhật", (dialog, which) -> {
            float rating = ratingBar.getRating();
            String content = etContent.getText().toString().trim();
            
            if (content.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập nội dung feedback", Toast.LENGTH_SHORT).show();
                return;
            }
            
            updateFeedback(feedback.getFeedbackId(), rating, content);
        });
        
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());
        builder.show();
    }
    
    private void updateFeedback(int feedbackId, double rating, String content) {
        try {
            boolean success = customerFeedbackService.updateFeedback(feedbackId, currentUserId, rating, content);
            if (success) {
                Toast.makeText(this, "Cập nhật feedback thành công!", Toast.LENGTH_SHORT).show();
                loadFeedbacks(); // Refresh danh sách
            } else {
                Toast.makeText(this, "Cập nhật feedback thất bại", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Toast.makeText(this, "Lỗi cập nhật feedback: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    private void showDeleteConfirmationDialog(Feedback feedback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác Nhận Xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa feedback này?");
        
        builder.setPositiveButton("Xóa", (dialog, which) -> deleteFeedback(feedback.getFeedbackId()));
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());
        builder.show();
    }
    
    private void deleteFeedback(int feedbackId) {
        try {
            boolean success = customerFeedbackService.deleteFeedback(feedbackId, currentUserId);
            if (success) {
                Toast.makeText(this, "Xóa feedback thành công!", Toast.LENGTH_SHORT).show();
                loadFeedbacks(); // Refresh danh sách
            } else {
                Toast.makeText(this, "Xóa feedback thất bại", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Toast.makeText(this, "Lỗi xóa feedback: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close repository if needed
    }
} 