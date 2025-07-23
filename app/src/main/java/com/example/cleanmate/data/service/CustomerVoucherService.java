package com.example.cleanmate.data.service;

import com.example.cleanmate.data.model.Voucher;
import com.example.cleanmate.data.model.UserVoucher;
import com.example.cleanmate.data.model.UserVoucherDisplay;
import com.example.cleanmate.data.repository.VoucherRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomerVoucherService {
    private final VoucherRepository voucherRepository;

    public CustomerVoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    /**
     * Lấy tất cả voucher có sẵn cho customer
     * Tương tự như GET / trong ASP.NET VoucherController
     */
    public List<Voucher> getAvailableVouchers() throws SQLException {
        return voucherRepository.getAllVoucher().stream()
                .filter(voucher -> "Active".equals(voucher.getStatus()))
                .collect(Collectors.toList());
    }

    /**
     * Lấy voucher theo ID
     * Tương tự như GET /{id} trong ASP.NET VoucherController
     */
    public Voucher getVoucherById(int voucherId) throws SQLException {
        if (voucherId <= 0) {
            throw new IllegalArgumentException("VoucherId phải lớn hơn 0");
        }

        Voucher voucher = voucherRepository.getVoucherById(voucherId);
        if (voucher == null) {
            throw new IllegalArgumentException("Không tìm thấy voucher với ID: " + voucherId);
        }

        return voucher;
    }

    /**
     * Lấy voucher của user cụ thể
     * Tương tự như GET /user/{userId} trong ASP.NET VoucherController
     */
    public List<UserVoucherDisplay> getUserVouchers(String userId) throws SQLException {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("UserId không được để trống");
        }

        return voucherRepository.getUserVoucherDisplayByUserId(userId);
    }

    /**
     * Lấy voucher theo code
     * Tương tự như GET /code/{code} trong ASP.NET VoucherController
     */
    public Voucher getVoucherByCode(String voucherCode) throws SQLException {
        if (voucherCode == null || voucherCode.trim().isEmpty()) {
            throw new IllegalArgumentException("VoucherCode không được để trống");
        }

        // Kiểm tra voucher code có tồn tại không
        if (!voucherRepository.voucherCodeExists(voucherCode)) {
            throw new IllegalArgumentException("Không tìm thấy voucher với code: " + voucherCode);
        }

        // Lấy voucher theo code (cần implement method này trong repository)
        // return voucherRepository.getVoucherByCode(voucherCode);
        
        // Tạm thời return null, cần implement method trong repository
        return null;
    }

    /**
     * Áp dụng voucher cho user
     * Tương tự như POST /apply trong ASP.NET VoucherController
     */
    public boolean applyVoucherToUser(String userId, int voucherId, int quantity) throws SQLException {
        Objects.requireNonNull(userId, "UserId không được null");
        
        if (voucherId <= 0) {
            throw new IllegalArgumentException("VoucherId phải lớn hơn 0");
        }
        
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity phải lớn hơn 0");
        }

        // Kiểm tra voucher có tồn tại không
        if (!voucherRepository.voucherExists(voucherId)) {
            throw new IllegalArgumentException("Không tìm thấy voucher với ID: " + voucherId);
        }

        // Kiểm tra voucher có active không
        Voucher voucher = voucherRepository.getVoucherById(voucherId);
        if (!"Active".equals(voucher.getStatus())) {
            throw new IllegalArgumentException("Voucher không còn hoạt động");
        }

        // Áp dụng voucher cho user
        voucherRepository.assignVoucherToUser(userId, voucherId, quantity);
        return true;
    }

    /**
     * Sử dụng voucher
     * Tương tự như POST /use trong ASP.NET VoucherController
     */
    public boolean useVoucher(String userId, String voucherCode) throws SQLException {
        Objects.requireNonNull(userId, "UserId không được null");
        Objects.requireNonNull(voucherCode, "VoucherCode không được null");

        // Lấy user voucher theo code
        UserVoucher userVoucher = voucherRepository.getUserVoucherByCode(userId, voucherCode);
        if (userVoucher == null) {
            throw new IllegalArgumentException("Không tìm thấy voucher với code: " + voucherCode);
        }

        // Kiểm tra voucher còn sử dụng được không
        if (userVoucher.getQuantity() <= 0) {
            throw new IllegalArgumentException("Voucher đã hết lượt sử dụng");
        }

        // Cập nhật quantity
        userVoucher.setQuantity(userVoucher.getQuantity() - 1);
        voucherRepository.updateUserVoucher(userVoucher);
        
        return true;
    }

    /**
     * DTO cho hiển thị voucher
     */
    public static class VoucherDisplay {
        private final Integer voucherId;
        private final String description;
        private final String discountPercentage;
        private final String expireDate;
        private final String voucherCode;
        private final String status;
        private final boolean isEventVoucher;

        public VoucherDisplay(Integer voucherId, String description, String discountPercentage, 
                            String expireDate, String voucherCode, String status, boolean isEventVoucher) {
            this.voucherId = voucherId;
            this.description = description;
            this.discountPercentage = discountPercentage;
            this.expireDate = expireDate;
            this.voucherCode = voucherCode;
            this.status = status;
            this.isEventVoucher = isEventVoucher;
        }

        public Integer getVoucherId() { return voucherId; }
        public String getDescription() { return description; }
        public String getDiscountPercentage() { return discountPercentage; }
        public String getExpireDate() { return expireDate; }
        public String getVoucherCode() { return voucherCode; }
        public String getStatus() { return status; }
        public boolean isEventVoucher() { return isEventVoucher; }

        public static VoucherDisplay fromVoucher(Voucher voucher) {
            return new VoucherDisplay(
                voucher.getVoucherId(),
                voucher.getDescription(),
                voucher.getDiscountPercentage().toString(),
                voucher.getExpireDate(),
                voucher.getVoucherCode(),
                voucher.getStatus(),
                voucher.getIsEventVoucher()
            );
        }
    }
} 