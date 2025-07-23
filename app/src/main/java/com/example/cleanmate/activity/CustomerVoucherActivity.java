package com.example.cleanmate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanmate.R;
import com.example.cleanmate.adapter.VoucherAdapter;
import com.example.cleanmate.data.model.Voucher;
import com.example.cleanmate.data.model.UserVoucher;
import com.example.cleanmate.data.model.UserVoucherDisplay;
import com.example.cleanmate.data.repository.VoucherRepository;
import com.example.cleanmate.data.service.CustomerVoucherService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerVoucherActivity extends AppCompatActivity implements VoucherAdapter.OnVoucherClickListener {
    
    private CustomerVoucherService customerVoucherService;
    private String currentUserId;
    
    // UI Components
    private EditText etSearch;
    private Button btnAvailableVouchers;
    private Button btnMyVouchers;
    private Button btnApplyVoucher;
    private Button btnUseVoucher;
    private Button btnPrevPage;
    private Button btnNextPage;
    private TextView tvPageInfo;
    private RecyclerView rvVouchers;
    private View emptyState;
    private VoucherAdapter voucherAdapter;
    
    // Data
    private List<Voucher> allAvailableVouchers;
    private List<UserVoucherDisplay> allUserVouchers;
    private List<Voucher> filteredAvailableVouchers;
    private List<UserVoucherDisplay> filteredUserVouchers;
    private boolean isShowingAvailableVouchers = true;
    
    // Pagination
    private static final int ITEMS_PER_PAGE = 6;
    private int currentPage = 1;
    private int totalPages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_voucher);
        
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
        // loadAvailableVouchers() sẽ được gọi từ initializeService() khi service khởi tạo thành công
    }
    
    private void initializeViews() {
        etSearch = findViewById(R.id.et_search);
        btnAvailableVouchers = findViewById(R.id.btn_available_vouchers);
        btnMyVouchers = findViewById(R.id.btn_my_vouchers);
        btnApplyVoucher = findViewById(R.id.btn_apply_voucher);
        btnUseVoucher = findViewById(R.id.btn_use_voucher);
        btnPrevPage = findViewById(R.id.btn_prev_page);
        btnNextPage = findViewById(R.id.btn_next_page);
        tvPageInfo = findViewById(R.id.tv_page_info);
        rvVouchers = findViewById(R.id.rv_vouchers);
        emptyState = findViewById(R.id.empty_state);
        
        // Setup RecyclerView với GridLayoutManager
        voucherAdapter = new VoucherAdapter(new ArrayList<>(), this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2); // 2 cột
        rvVouchers.setLayoutManager(layoutManager);
        rvVouchers.setAdapter(voucherAdapter);
    }
    
    private void setupClickListeners() {
        btnAvailableVouchers.setOnClickListener(v -> showAvailableVouchers());
        btnMyVouchers.setOnClickListener(v -> showMyVouchers());
        btnApplyVoucher.setOnClickListener(v -> showApplyVoucherDialog());
        btnUseVoucher.setOnClickListener(v -> showUseVoucherDialog());
        btnPrevPage.setOnClickListener(v -> goToPreviousPage());
        btnNextPage.setOnClickListener(v -> goToNextPage());
    }
    
    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterVouchers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    
    private void filterVouchers(String searchText) {
        if (isShowingAvailableVouchers) {
            if (allAvailableVouchers != null) {
                filteredAvailableVouchers = allAvailableVouchers.stream()
                        .filter(voucher -> voucher.getVoucherCode().toLowerCase().contains(searchText.toLowerCase()))
                        .collect(Collectors.toList());
                updatePagination();
                displayCurrentPage();
            }
        } else {
            if (allUserVouchers != null) {
                filteredUserVouchers = allUserVouchers.stream()
                        .filter(voucher -> voucher.getVoucherCode().toLowerCase().contains(searchText.toLowerCase()))
                        .collect(Collectors.toList());
                updatePagination();
                displayCurrentPage();
            }
        }
    }
    
    private void initializeService() {
        Toast.makeText(this, "Đang khởi tạo service...", Toast.LENGTH_SHORT).show();
        
        // Chạy database connection trên background thread
        new Thread(() -> {
            try {
                VoucherRepository voucherRepo = new VoucherRepository();
                
                // Debug: Kiểm tra cấu trúc bảng
                System.out.println("DEBUG: Kiểm tra cấu trúc bảng...");
                voucherRepo.debugTableStructure();
                
                CustomerVoucherService service = new CustomerVoucherService(voucherRepo);
                
                // Chuyển về main thread để update UI
                runOnUiThread(() -> {
                    customerVoucherService = service;
                    System.out.println("DEBUG: CustomerVoucherService đã được khởi tạo thành công");
                    Toast.makeText(this, "Đã khởi tạo service thành công", Toast.LENGTH_SHORT).show();
                    loadAvailableVouchers();
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
    
    private void loadAvailableVouchers() {
        System.out.println("DEBUG: loadAvailableVouchers() được gọi");
        System.out.println("DEBUG: customerVoucherService = " + (customerVoucherService != null ? "NOT NULL" : "NULL"));
        
        if (customerVoucherService == null) {
            Toast.makeText(this, "CustomerVoucherService chưa được khởi tạo", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Chạy database query trên background thread
        new Thread(() -> {
            try {
                List<Voucher> vouchers = customerVoucherService.getAvailableVouchers();
                
                // Chuyển về main thread để update UI
                runOnUiThread(() -> {
                    if (vouchers == null) {
                        Toast.makeText(this, "Không thể tải danh sách voucher", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    allAvailableVouchers = vouchers;
                    filteredAvailableVouchers = new ArrayList<>(allAvailableVouchers);
                    Toast.makeText(this, "Đã tải " + allAvailableVouchers.size() + " voucher", Toast.LENGTH_SHORT).show();
                    
                    // Debug: In ra thông tin voucher đầu tiên
                    if (!allAvailableVouchers.isEmpty()) {
                        Voucher firstVoucher = allAvailableVouchers.get(0);
                        System.out.println("DEBUG: Voucher đầu tiên - Code: " + firstVoucher.getVoucherCode() + 
                                         ", Description: " + firstVoucher.getDescription());
                    }
                    
                    showAvailableVouchers();
                });
                
            } catch (SQLException e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Lỗi tải voucher: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Lỗi không xác định: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
            }
        }).start();
    }
    
    private void loadUserVouchers() {
        if (customerVoucherService == null) {
            Toast.makeText(this, "CustomerVoucherService chưa được khởi tạo", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Chạy database query trên background thread
        new Thread(() -> {
            try {
                List<UserVoucherDisplay> userVouchers = customerVoucherService.getUserVouchers(currentUserId);
                
                // Chuyển về main thread để update UI
                runOnUiThread(() -> {
                    allUserVouchers = userVouchers;
                    filteredUserVouchers = new ArrayList<>(allUserVouchers);
                    showMyVouchers();
                });
                
            } catch (SQLException e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Lỗi tải voucher của bạn: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Lỗi không xác định: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
            }
        }).start();
    }
    
    private void showAvailableVouchers() {
        isShowingAvailableVouchers = true;
        btnAvailableVouchers.setBackgroundResource(R.drawable.tab_button_active);
        btnMyVouchers.setBackgroundResource(R.drawable.tab_button_inactive);
        
        if (filteredAvailableVouchers != null) {
            updatePagination();
            displayCurrentPage();
        }
    }
    
    private void showMyVouchers() {
        isShowingAvailableVouchers = false;
        btnAvailableVouchers.setBackgroundResource(R.drawable.tab_button_inactive);
        btnMyVouchers.setBackgroundResource(R.drawable.tab_button_active);
        
        if (filteredUserVouchers != null) {
            updatePagination();
            displayCurrentPage();
        } else {
            loadUserVouchers();
        }
    }
    
    private void updatePagination() {
        int totalItems = isShowingAvailableVouchers ? 
                filteredAvailableVouchers.size() : filteredUserVouchers.size();
        totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);
        if (totalPages == 0) totalPages = 1;
        
        if (currentPage > totalPages) {
            currentPage = totalPages;
        }
        
        updatePaginationUI();
    }
    
    private void updatePaginationUI() {
        tvPageInfo.setText("Trang " + currentPage + " / " + totalPages);
        btnPrevPage.setEnabled(currentPage > 1);
        btnNextPage.setEnabled(currentPage < totalPages);
    }
    
    private void displayCurrentPage() {
        List<?> currentItems = isShowingAvailableVouchers ? 
                filteredAvailableVouchers : filteredUserVouchers;
        
        if (currentItems == null || currentItems.isEmpty()) {
            showEmptyState();
            return;
        }
        
        hideEmptyState();
        
        int startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, currentItems.size());
        
        List<?> pageItems = currentItems.subList(startIndex, endIndex);
        
        if (isShowingAvailableVouchers) {
            voucherAdapter.updateVouchers((List<Voucher>) pageItems);
        } else {
            voucherAdapter.updateUserVouchers((List<UserVoucherDisplay>) pageItems);
        }
    }
    
    private void showEmptyState() {
        emptyState.setVisibility(View.VISIBLE);
        rvVouchers.setVisibility(View.GONE);
    }
    
    private void hideEmptyState() {
        emptyState.setVisibility(View.GONE);
        rvVouchers.setVisibility(View.VISIBLE);
    }
    
    private void goToPreviousPage() {
        if (currentPage > 1) {
            currentPage--;
            displayCurrentPage();
            updatePaginationUI();
        }
    }
    
    private void goToNextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            displayCurrentPage();
            updatePaginationUI();
        }
    }
    
    private void showApplyVoucherDialog() {
        if (!isShowingAvailableVouchers || filteredAvailableVouchers == null || filteredAvailableVouchers.isEmpty()) {
            Toast.makeText(this, "Không có voucher nào để áp dụng", Toast.LENGTH_SHORT).show();
            return;
        }
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Áp Dụng Voucher");
        
        String[] voucherOptions = filteredAvailableVouchers.stream()
                .map(v -> v.getVoucherCode() + " - " + v.getDescription())
                .toArray(String[]::new);
        
        builder.setItems(voucherOptions, (dialog, which) -> {
            Voucher selectedVoucher = filteredAvailableVouchers.get(which);
            showQuantityDialog(selectedVoucher);
        });
        
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());
        builder.show();
    }
    
    private void showQuantityDialog(Voucher voucher) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn Số Lượng");
        
        final EditText input = new EditText(this);
        input.setHint("Nhập số lượng (1-10)");
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        
        builder.setPositiveButton("Áp Dụng", (dialog, which) -> {
            try {
                int quantity = Integer.parseInt(input.getText().toString());
                if (quantity > 0 && quantity <= 10) {
                    applyVoucherToUser(voucher.getVoucherId(), quantity);
                } else {
                    Toast.makeText(this, "Số lượng phải từ 1-10", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Vui lòng nhập số hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });
        
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());
        builder.show();
    }
    
    private void applyVoucherToUser(int voucherId, int quantity) {
        // Chạy database operation trên background thread
        new Thread(() -> {
            try {
                boolean success = customerVoucherService.applyVoucherToUser(currentUserId, voucherId, quantity);
                
                runOnUiThread(() -> {
                    if (success) {
                        Toast.makeText(this, "Áp dụng voucher thành công!", Toast.LENGTH_SHORT).show();
                        loadUserVouchers(); // Refresh danh sách voucher của user
                    } else {
                        Toast.makeText(this, "Áp dụng voucher thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
                
            } catch (SQLException e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Lỗi áp dụng voucher: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            } catch (IllegalArgumentException e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }
    
    private void showUseVoucherDialog() {
        if (isShowingAvailableVouchers || filteredUserVouchers == null || filteredUserVouchers.isEmpty()) {
            Toast.makeText(this, "Không có voucher nào để sử dụng", Toast.LENGTH_SHORT).show();
            return;
        }
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sử Dụng Voucher");
        
        String[] voucherOptions = filteredUserVouchers.stream()
                .filter(uv -> uv.getQuantity() > 0)
                .map(uv -> uv.getVoucherCode() + " - " + uv.getDescription() + " (Còn " + uv.getQuantity() + " lượt)")
                .toArray(String[]::new);
        
        if (voucherOptions.length == 0) {
            Toast.makeText(this, "Không có voucher nào còn lượt sử dụng", Toast.LENGTH_SHORT).show();
            return;
        }
        
        builder.setItems(voucherOptions, (dialog, which) -> {
            UserVoucherDisplay selectedUserVoucher = filteredUserVouchers.stream()
                    .filter(uv -> uv.getQuantity() > 0)
                    .collect(Collectors.toList())
                    .get(which);
            useVoucher(selectedUserVoucher.getVoucherCode());
        });
        
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());
        builder.show();
    }
    
    private void useVoucher(String voucherCode) {
        // Chạy database operation trên background thread
        new Thread(() -> {
            try {
                boolean success = customerVoucherService.useVoucher(currentUserId, voucherCode);
                
                runOnUiThread(() -> {
                    if (success) {
                        Toast.makeText(this, "Sử dụng voucher thành công!", Toast.LENGTH_SHORT).show();
                        loadUserVouchers(); // Refresh danh sách voucher của user
                    } else {
                        Toast.makeText(this, "Sử dụng voucher thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
                
            } catch (SQLException e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Lỗi sử dụng voucher: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            } catch (IllegalArgumentException e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }
    
    // VoucherAdapter.OnVoucherClickListener implementation
    @Override
    public void onVoucherClick(Voucher voucher) {
        try {
            System.out.println("DEBUG: onVoucherClick được gọi");
            if (voucher == null) {
                Toast.makeText(this, "Voucher không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            System.out.println("DEBUG: Voucher được click - Code: " + voucher.getVoucherCode());
            showVoucherDetails(voucher);
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi hiển thị voucher: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    
    @Override
    public void onUserVoucherClick(UserVoucherDisplay userVoucher) {
        try {
            if (userVoucher == null) {
                Toast.makeText(this, "Voucher không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            showUserVoucherDetails(userVoucher);
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi hiển thị voucher: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    
    @Override
    public void onUseNowClick(Voucher voucher) {
        showQuantityDialog(voucher);
    }
    
    @Override
    public void onUseNowClick(UserVoucherDisplay userVoucher) {
        if (userVoucher.getQuantity() > 0) {
            useVoucher(userVoucher.getVoucherCode());
        } else {
            Toast.makeText(this, "Voucher đã hết lượt sử dụng", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void showVoucherDetails(Voucher voucher) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Chi Tiết Voucher");
            
            String details = "Mã voucher: " + (voucher.getVoucherCode() != null ? voucher.getVoucherCode() : "N/A") + "\n" +
                    "Mô tả: " + (voucher.getDescription() != null ? voucher.getDescription() : "N/A") + "\n" +
                    "Giảm giá: " + (voucher.getDiscountPercentage() != null ? voucher.getDiscountPercentage() : 0) + "%\n" +
                    "Ngày hết hạn: " + (voucher.getExpireDate() != null ? voucher.getExpireDate() : "N/A") + "\n" +
                    "Trạng thái: " + (voucher.getStatus() != null ? voucher.getStatus() : "N/A");
            
            builder.setMessage(details);
            builder.setPositiveButton("Áp dụng", (dialog, which) -> showQuantityDialog(voucher));
            builder.setNegativeButton("Đóng", (dialog, which) -> dialog.cancel());
            builder.show();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi hiển thị chi tiết voucher: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    
    private void showUserVoucherDetails(UserVoucherDisplay userVoucher) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Chi Tiết Voucher Của Bạn");
            
            String details = "Mã voucher: " + (userVoucher.getVoucherCode() != null ? userVoucher.getVoucherCode() : "N/A") + "\n" +
                    "Mô tả: " + (userVoucher.getDescription() != null ? userVoucher.getDescription() : "N/A") + "\n" +
                    "Giảm giá: " + (userVoucher.getDiscountPercentage() != null ? userVoucher.getDiscountPercentage() : 0) + "%\n" +
                    "Ngày hết hạn: " + (userVoucher.getExpireDate() != null ? userVoucher.getExpireDate() : "N/A") + "\n" +
                    "Số lượng: " + (userVoucher.getQuantity() != null ? userVoucher.getQuantity() : 0) + "\n" +
                    "Trạng thái: " + (userVoucher.getStatus() != null ? userVoucher.getStatus() : "N/A");
            
            builder.setMessage(details);
            if (userVoucher.getQuantity() != null && userVoucher.getQuantity() > 0) {
                builder.setPositiveButton("Sử dụng", (dialog, which) -> useVoucher(userVoucher.getVoucherCode()));
            }
            builder.setNegativeButton("Đóng", (dialog, which) -> dialog.cancel());
            builder.show();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi hiển thị chi tiết voucher: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close repository if needed
    }
} 