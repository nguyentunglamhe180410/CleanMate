package com.example.cleanmate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cleanmate.R;
import com.example.cleanmate.data.model.User;
import com.example.cleanmate.data.repository.CustomerProfileRepository;
import com.example.cleanmate.data.repository.CustomerRepository;
import com.example.cleanmate.data.service.CustomerProfileService;

import java.sql.SQLException;

/**
 * CustomerProfileActivity - Quản lý thông tin cá nhân của Customer (khách hàng)
 * 
 * Chức năng:
 * - Hiển thị thông tin cá nhân: Họ tên, Email, SĐT, Ngày sinh, Giới tính
 * - Chỉnh sửa thông tin cá nhân
 * - Lưu thông tin vào database
 * - Không bao gồm chức năng xem Cleaner Profile (đó là chức năng khác)
 */
public class CustomerProfileActivity extends AppCompatActivity {
    
    private CustomerProfileService customerProfileService;
    private CustomerProfileService.CustomerProfile currentProfile;
    private String currentUserId;
    
    // UI Components - View Mode
    private ImageView ivProfileImage;
    private TextView tvProfileName;
    private TextView tvFullName;
    private TextView tvEmail;
    private TextView tvPhoneNumber;
    private TextView tvDob;
    private TextView tvGender;
    
    // UI Components - Edit Mode
    private EditText etFullName;
    private EditText etEmail;
    private EditText etPhoneNumber;
    private EditText etDob;
    
    // Buttons
    private Button btnEditProfile;
    private Button btnSaveProfile;
    private Button btnCancelEdit;
    // private Button btnViewCleanerProfile; // Xóa vì Customer Profile không nên xem Cleaner Profile
    
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        
        // Lấy userId từ Intent (giả sử đã login)
        currentUserId = getIntent().getStringExtra("USER_ID");
        if (currentUserId == null) {
            Toast.makeText(this, "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
        System.out.println("DEBUG: onCreate() - currentUserId = " + currentUserId);
        
        initializeViews();
        setupClickListeners();
        createTestUserIfNeeded(); // Tạo test user nếu cần
        initializeService(); // Service sẽ tự động gọi loadProfile() khi khởi tạo thành công
    }
    
    private void initializeViews() {
        // View Mode Components
        ivProfileImage = findViewById(R.id.iv_profile_image);
        tvProfileName = findViewById(R.id.tv_profile_name);
        tvFullName = findViewById(R.id.tv_full_name);
        tvEmail = findViewById(R.id.tv_email);
        tvPhoneNumber = findViewById(R.id.tv_phone_number);
        tvDob = findViewById(R.id.tv_dob);
        tvGender = findViewById(R.id.tv_gender);
        
        // Edit Mode Components (initially hidden)
        etFullName = findViewById(R.id.et_full_name);
        etEmail = findViewById(R.id.et_email);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etDob = findViewById(R.id.et_dob);
        
        // Buttons
        btnEditProfile = findViewById(R.id.btn_edit_profile);
        btnSaveProfile = findViewById(R.id.btn_save_profile);
        btnCancelEdit = findViewById(R.id.btn_cancel_edit);
        // btnViewCleanerProfile = findViewById(R.id.btn_view_cleaner_profile); // Xóa vì không cần thiết
        
        // Back button
        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
        
        // Hide edit components initially
        hideEditComponents();
    }
    
    private void setupClickListeners() {
        btnEditProfile.setOnClickListener(v -> enableEditMode());
        btnSaveProfile.setOnClickListener(v -> saveProfile());
        btnCancelEdit.setOnClickListener(v -> cancelEdit());
        // Xóa btnViewCleanerProfile vì Customer Profile không nên xem Cleaner Profile
    }
    
    private void initializeService() {
        System.out.println("DEBUG: initializeService() được gọi");
        Toast.makeText(this, "Đang khởi tạo service...", Toast.LENGTH_SHORT).show();
        
        // Chạy database connection trên background thread
        new Thread(() -> {
            try {
                System.out.println("DEBUG: Bắt đầu khởi tạo repositories...");
                
                System.out.println("DEBUG: Tạo CustomerProfileRepository...");
                CustomerProfileRepository customerProfileRepo = new CustomerProfileRepository();
                System.out.println("DEBUG: CustomerProfileRepository đã được tạo");
                
                System.out.println("DEBUG: Tạo CustomerRepository...");
                CustomerRepository customerRepo = new CustomerRepository();
                System.out.println("DEBUG: CustomerRepository đã được tạo");
                
                // Debug: Kiểm tra cấu trúc bảng
                System.out.println("DEBUG: Kiểm tra cấu trúc bảng Customer...");
                customerRepo.debugTableStructure();
                
                System.out.println("DEBUG: Tạo CustomerProfileService...");
                CustomerProfileService service = new CustomerProfileService(customerRepo, customerProfileRepo);
                System.out.println("DEBUG: CustomerProfileService đã được tạo");
                
                // Chuyển về main thread để update UI
                runOnUiThread(() -> {
                    customerProfileService = service;
                    System.out.println("DEBUG: CustomerProfileService đã được khởi tạo thành công");
                    Toast.makeText(this, "Đã khởi tạo service thành công", Toast.LENGTH_SHORT).show();
                    loadProfile();
                });
                
            } catch (ClassNotFoundException e) {
                System.err.println("DEBUG: ClassNotFoundException: " + e.getMessage());
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(this, "Lỗi driver database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    finish();
                });
            } catch (SQLException e) {
                System.err.println("DEBUG: SQLException: " + e.getMessage());
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(this, "Lỗi kết nối database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    finish();
                });
            } catch (Exception e) {
                System.err.println("DEBUG: Exception: " + e.getMessage());
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(this, "Lỗi khởi tạo service: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println("DEBUG: Thử tạo test service...");
                    createTestService(); // Thử tạo test service nếu database lỗi
                });
            }
        }).start();
    }
    
    private void createTestUserIfNeeded() {
        // Kiểm tra user ID có hợp lệ không
        System.out.println("DEBUG: createTestUserIfNeeded() - currentUserId = " + currentUserId);
        
        // Kiểm tra xem user ID có format đúng không (user-001 đến user-005)
        if (currentUserId != null && currentUserId.matches("user-\\d{3}")) {
            Toast.makeText(this, "Sử dụng user: " + currentUserId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User ID không hợp lệ: " + currentUserId, Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * Tạo test service để debug (không cần database)
     */
    private void createTestService() {
        System.out.println("DEBUG: createTestService() - Tạo test service");
        try {
            // Tạo mock repositories (không cần database)
            CustomerProfileService service = new CustomerProfileService(null, null);
            customerProfileService = service;
            System.out.println("DEBUG: Test service đã được tạo");
            Toast.makeText(this, "Test service đã được tạo", Toast.LENGTH_SHORT).show();
            loadProfile();
        } catch (Exception e) {
            System.err.println("DEBUG: Lỗi tạo test service: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void loadProfile() {
        System.out.println("DEBUG: loadProfile() được gọi");
        System.out.println("DEBUG: customerProfileService = " + (customerProfileService != null ? "NOT NULL" : "NULL"));
        
        if (customerProfileService == null) {
            Toast.makeText(this, "CustomerProfileService chưa được khởi tạo", Toast.LENGTH_SHORT).show();
            System.out.println("DEBUG: Service null, thử tạo test service...");
            createTestService();
            return;
        }
        
        // Chạy database query trên background thread
        new Thread(() -> {
            try {
                CustomerProfileService.CustomerProfile profile = customerProfileService.getProfile(currentUserId);
                
                // Chuyển về main thread để update UI
                runOnUiThread(() -> {
                    if (profile == null) {
                        Toast.makeText(this, "Không thể tải profile", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    currentProfile = profile;
                    displayProfile(currentProfile);
                    Toast.makeText(this, "Đã tải profile thành công", Toast.LENGTH_SHORT).show();
                });
                
            } catch (SQLException e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Lỗi tải profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
            } catch (IllegalArgumentException e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    finish();
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Lỗi không xác định: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
            }
        }).start();
    }
    
    private void displayProfile(CustomerProfileService.CustomerProfile profile) {
        if (profile == null) return;
        
        // Update view mode components
        tvProfileName.setText(profile.getFullName());
        tvFullName.setText(profile.getFullName());
        tvEmail.setText(profile.getEmail());
        tvPhoneNumber.setText(profile.getPhoneNumber());
        tvDob.setText(profile.getDob() != null ? profile.getDob() : "Chưa cập nhật");
        
        if (profile.getGender() != null) {
            tvGender.setText(profile.getGender() ? "Nam" : "Nữ");
        } else {
            tvGender.setText("Chưa cập nhật");
        }
        
        // Update edit mode components
        etFullName.setText(profile.getFullName());
        etEmail.setText(profile.getEmail());
        etPhoneNumber.setText(profile.getPhoneNumber());
        etDob.setText(profile.getDob());
        
        // TODO: Load profile image if available
        // if (profile.getAvatarUrl() != null) {
        //     // Load image using Glide or Picasso
        // }
    }
    
    private void enableEditMode() {
        isEditMode = true;
        showEditComponents();
        setButtonVisibility(true);
    }
    
    private void setEditMode(boolean editMode) {
        isEditMode = editMode;
        
        if (editMode) {
            showEditComponents();
        } else {
            hideEditComponents();
        }
        
        setButtonVisibility(editMode);
    }
    
    private void showEditComponents() {
        // Hide view components
        tvFullName.setVisibility(View.GONE);
        tvEmail.setVisibility(View.GONE);
        tvPhoneNumber.setVisibility(View.GONE);
        tvDob.setVisibility(View.GONE);
        
        // Show edit components
        etFullName.setVisibility(View.VISIBLE);
        etEmail.setVisibility(View.VISIBLE);
        etPhoneNumber.setVisibility(View.VISIBLE);
        etDob.setVisibility(View.VISIBLE);
    }
    
    private void hideEditComponents() {
        // Show view components
        tvFullName.setVisibility(View.VISIBLE);
        tvEmail.setVisibility(View.VISIBLE);
        tvPhoneNumber.setVisibility(View.VISIBLE);
        tvDob.setVisibility(View.VISIBLE);
        
        // Hide edit components
        etFullName.setVisibility(View.GONE);
        etEmail.setVisibility(View.GONE);
        etPhoneNumber.setVisibility(View.GONE);
        etDob.setVisibility(View.GONE);
    }
    
    private void setButtonVisibility(boolean editMode) {
        btnEditProfile.setVisibility(editMode ? View.GONE : View.VISIBLE);
        btnSaveProfile.setVisibility(editMode ? View.VISIBLE : View.GONE);
        btnCancelEdit.setVisibility(editMode ? View.VISIBLE : View.GONE);
    }
    
    private void saveProfile() {
        // Validate input
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String dob = etDob.getText().toString().trim();
        
        if (fullName.isEmpty()) {
            etFullName.setError("Họ tên không được để trống");
            return;
        }
        
        if (email.isEmpty()) {
            etEmail.setError("Email không được để trống");
            return;
        }
        
        if (phoneNumber.isEmpty()) {
            etPhoneNumber.setError("Số điện thoại không được để trống");
            return;
        }
        
        // Create updated profile
        CustomerProfileService.CustomerProfile updatedProfile = currentProfile
                .withFullName(fullName)
                .withEmail(email)
                .withPhoneNumber(phoneNumber)
                .withDob(dob.isEmpty() ? null : dob);
        
        // Save to database
        try {
            boolean success = customerProfileService.updateProfile(updatedProfile);
            if (success) {
                currentProfile = updatedProfile;
                setEditMode(false);
                displayProfile(currentProfile); // Refresh display
                Toast.makeText(this, "Cập nhật profile thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Cập nhật profile thất bại", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Toast.makeText(this, "Lỗi cập nhật: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    private void cancelEdit() {
        displayProfile(currentProfile); // Restore original values
        setEditMode(false);
    }
    
    // Xóa các method liên quan đến Cleaner Profile vì Customer Profile chỉ quản lý thông tin Customer
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close repositories if needed
    }
} 