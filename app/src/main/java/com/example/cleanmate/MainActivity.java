package com.example.cleanmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;
import java.util.List;
import java.util.ArrayList;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cleanmate.activity.CustomerProfileActivity;
import com.example.cleanmate.activity.CustomerVoucherActivity;
import com.example.cleanmate.activity.CustomerFeedbackActivity;
import com.example.cleanmate.activity.cleanerActivity.CleanerListActivity;
import com.example.cleanmate.activity.customerActivity.BookingActivity;
import com.example.cleanmate.data.repository.CustomerRepository;

public class MainActivity extends AppCompatActivity {
    
    private Spinner spinnerUserSelect;
    private TextView tvLoadingUsers;
    private String selectedUserId = "user-001"; // Default
    private CustomerRepository customerRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize repository
        try {
            customerRepository = new CustomerRepository();
            System.out.println("DEBUG: CustomerRepository initialized successfully");
        } catch (Exception e) {
            System.err.println("ERROR: Failed to initialize CustomerRepository: " + e.getMessage());
            e.printStackTrace();
            Toast.makeText(this, "Không thể kết nối database, sử dụng dữ liệu mẫu", Toast.LENGTH_LONG).show();
            // Set to null, will use fallback in loadUsersFromDatabase
            customerRepository = null;
        }
        
        System.out.println("DEBUG: Repository status - customerRepository = " + (customerRepository != null ? "initialized" : "null"));

        // Initialize user selection spinner
        spinnerUserSelect = findViewById(R.id.spinner_user_select);
        tvLoadingUsers = findViewById(R.id.tv_loading_users);
        
        // Load users - if repository is null, use fallback immediately
        if (customerRepository == null) {
            System.out.println("DEBUG: Repository is null, using fallback immediately");
            forceLoadFallbackUsers();
        } else {
            loadUsersFromDatabase();
        }

        // Initialize buttons
        Button btnCustomerProfile = findViewById(R.id.btn_customer_profile);
        Button btnCustomerVoucher = findViewById(R.id.btn_customer_voucher);
        Button btnCustomerFeedback = findViewById(R.id.btn_customer_feedback);
        Button btnCleanerList = findViewById(R.id.btn_cleaner_list);
        Button btnBooking = findViewById(R.id.btn_booking);

        // Set click listeners
        btnCustomerProfile.setOnClickListener(v -> openCustomerProfile());
        btnCustomerVoucher.setOnClickListener(v -> openCustomerVoucher());
        btnCustomerFeedback.setOnClickListener(v -> openCustomerFeedback());
        btnCleanerList.setOnClickListener(v -> openCleanerList());
        btnBooking.setOnClickListener(v -> openBooking());
    }
    
    private void loadUsersFromDatabase() {
        // Show loading indicator
        tvLoadingUsers.setVisibility(View.VISIBLE);
        spinnerUserSelect.setEnabled(false);
        
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                try {
                    if (customerRepository == null) {
                        System.out.println("DEBUG: CustomerRepository is null, using fallback");
                        throw new Exception("CustomerRepository not initialized");
                    }
                    
                    System.out.println("DEBUG: Loading users from database...");
                    List<String> userIds = customerRepository.getAllCustomerIds();
                    System.out.println("DEBUG: Found " + userIds.size() + " users: " + userIds);
                    
                    // Kiểm tra nếu danh sách rỗng
                    if (userIds == null || userIds.isEmpty()) {
                        System.out.println("DEBUG: Database returned empty list, using fallback");
                        throw new Exception("No users found in database");
                    }
                    
                    return userIds;
                } catch (Exception e) {
                    System.out.println("ERROR: Failed to load users: " + e.getMessage());
                    e.printStackTrace();
                    
                    // Fallback to hardcoded list if database fails
                    List<String> fallbackUsers = new ArrayList<>();
                    fallbackUsers.add("user-001");
                    fallbackUsers.add("user-002");
                    fallbackUsers.add("user-003");
                    fallbackUsers.add("user-004");
                    fallbackUsers.add("user-005");
                    System.out.println("DEBUG: Using fallback users: " + fallbackUsers);
                    return fallbackUsers;
                }
            }

            @Override
            protected void onPostExecute(List<String> userIds) {
                // Hide loading indicator
                tvLoadingUsers.setVisibility(View.GONE);
                spinnerUserSelect.setEnabled(true);
                
                if (userIds == null || userIds.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Không thể tải danh sách người dùng", Toast.LENGTH_LONG).show();
                    return;
                }
                
                setupUserSpinner(userIds);
                Toast.makeText(MainActivity.this, "Đã tải " + userIds.size() + " người dùng", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
    
    private void setupUserSpinner(List<String> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy user nào", Toast.LENGTH_SHORT).show();
            return;
        }

        System.out.println("DEBUG: Setting up spinner with " + userIds.size() + " users: " + userIds);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userIds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserSelect.setAdapter(adapter);
        
        // Set default selection
        selectedUserId = userIds.get(0);
        System.out.println("DEBUG: Default selected user: " + selectedUserId);
        
        spinnerUserSelect.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                selectedUserId = userIds.get(position);
                System.out.println("DEBUG: User selected: " + selectedUserId);
                Toast.makeText(MainActivity.this, "Đã chọn user: " + selectedUserId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                if (!userIds.isEmpty()) {
                    selectedUserId = userIds.get(0);
                    System.out.println("DEBUG: No user selected, using default: " + selectedUserId);
                }
            }
        });
        
        System.out.println("DEBUG: Spinner setup completed");
    }

    private void openCustomerProfile() {
        // Sử dụng user được chọn từ spinner
        Intent intent = new Intent(this, CustomerProfileActivity.class);
        intent.putExtra("USER_ID", selectedUserId);
        startActivity(intent);
    }

    private void openCustomerVoucher() {
        // Sử dụng user được chọn từ spinner
        Intent intent = new Intent(this, CustomerVoucherActivity.class);
        intent.putExtra("USER_ID", selectedUserId);
        startActivity(intent);
    }

    private void openCustomerFeedback() {
        // Sử dụng user được chọn từ spinner
        Intent intent = new Intent(this, CustomerFeedbackActivity.class);
        intent.putExtra("USER_ID", selectedUserId);
        startActivity(intent);
    }

    private void openCleanerList() {
        Intent intent = new Intent(this, CleanerListActivity.class);
        startActivity(intent);
    }

    private void openBooking() {
        Intent intent = new Intent(this, BookingActivity.class);
        startActivity(intent);
    }
    
    // Method để refresh user list (có thể dùng để test)
    public void refreshUserList() {
        loadUsersFromDatabase();
    }
    
    // Method để test database connection
    public void testDatabaseConnection() {
        if (customerRepository != null) {
            try {
                customerRepository.debugTableStructure();
                Toast.makeText(this, "Database connection successful", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Database connection failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "CustomerRepository is null", Toast.LENGTH_SHORT).show();
        }
    }
    
    // Method để force load fallback users
    public void forceLoadFallbackUsers() {
        System.out.println("DEBUG: Force loading fallback users");
        List<String> fallbackUsers = new ArrayList<>();
        fallbackUsers.add("user-001");
        fallbackUsers.add("user-002");
        fallbackUsers.add("user-003");
        fallbackUsers.add("user-004");
        fallbackUsers.add("user-005");
        
        tvLoadingUsers.setVisibility(View.GONE);
        spinnerUserSelect.setEnabled(true);
        setupUserSpinner(fallbackUsers);
        Toast.makeText(this, "Đã tải " + fallbackUsers.size() + " người dùng mẫu", Toast.LENGTH_SHORT).show();
    }
    
    // Method để kiểm tra trạng thái spinner
    public void checkSpinnerStatus() {
        System.out.println("DEBUG: Checking spinner status");
        System.out.println("DEBUG: spinnerUserSelect = " + (spinnerUserSelect != null ? "not null" : "null"));
        if (spinnerUserSelect != null) {
            System.out.println("DEBUG: spinnerUserSelect.isEnabled() = " + spinnerUserSelect.isEnabled());
            System.out.println("DEBUG: spinnerUserSelect.getAdapter() = " + (spinnerUserSelect.getAdapter() != null ? "not null" : "null"));
            if (spinnerUserSelect.getAdapter() != null) {
                System.out.println("DEBUG: spinnerUserSelect.getAdapter().getCount() = " + spinnerUserSelect.getAdapter().getCount());
            }
        }
        System.out.println("DEBUG: selectedUserId = " + selectedUserId);
    }
}