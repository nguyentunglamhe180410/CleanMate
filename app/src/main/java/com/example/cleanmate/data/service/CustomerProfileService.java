package com.example.cleanmate.data.service;

import com.example.cleanmate.data.model.User;
import com.example.cleanmate.data.model.CleanerProfile;
import com.example.cleanmate.data.repository.CustomerRepository;
import com.example.cleanmate.data.repository.CustomerProfileRepository;

import java.sql.SQLException;
import java.util.Objects;

public class CustomerProfileService {
    private final CustomerRepository customerRepository;
    private final CustomerProfileRepository customerProfileRepository;

    public CustomerProfileService(CustomerRepository customerRepository, CustomerProfileRepository customerProfileRepository) {
        this.customerRepository = customerRepository;
        this.customerProfileRepository = customerProfileRepository;
    }

    /**
     * Lấy profile của customer hiện tại
     * Tương tự như GET / trong ASP.NET CustomerProfileController
     */
    public CustomerProfile getProfile(String userId) throws SQLException {
        System.out.println("DEBUG: CustomerProfileService.getProfile() - userId = " + userId);
        
        if (userId == null || userId.trim().isEmpty()) {
            System.out.println("DEBUG: UserId is null or empty");
            throw new IllegalArgumentException("UserId không được để trống");
        }

        System.out.println("DEBUG: Calling customerProfileRepository.getUserById()");
        User user = customerProfileRepository.getUserById(userId);
        System.out.println("DEBUG: getUserById result = " + (user != null ? "NOT NULL" : "NULL"));
        
        if (user == null) {
            System.out.println("DEBUG: User not found in database");
            throw new IllegalArgumentException("Không tìm thấy người dùng với ID: " + userId);
        }

        System.out.println("DEBUG: Creating CustomerProfile with user data:");
        System.out.println("DEBUG: - UserId: " + user.getUserId());
        System.out.println("DEBUG: - FullName: " + user.getFullName());
        System.out.println("DEBUG: - Email: " + user.getEmail());
        System.out.println("DEBUG: - PhoneNumber: " + user.getPhoneNumber());

        return new CustomerProfile(
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getProfileImage(),
                user.getDob(),
                user.getGender()
        );
    }

    /**
     * Cập nhật profile của customer
     * Tương tự như PUT /edit trong ASP.NET CustomerProfileController
     */
    public boolean updateProfile(CustomerProfile profile) throws SQLException {
        Objects.requireNonNull(profile, "Profile không được null");
        Objects.requireNonNull(profile.getUserId(), "UserId không được null");

        User existingUser = customerProfileRepository.getUserById(profile.getUserId());
        if (existingUser == null) {
            throw new IllegalArgumentException("Không tìm thấy người dùng với ID: " + profile.getUserId());
        }

        // Cập nhật các trường có thể thay đổi
        if (profile.getFullName() != null) {
            existingUser.setFullName(profile.getFullName());
        }
        if (profile.getEmail() != null) {
            existingUser.setEmail(profile.getEmail());
        }
        if (profile.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(profile.getPhoneNumber());
        }
        if (profile.getAvatarUrl() != null) {
            existingUser.setProfileImage(profile.getAvatarUrl());
        }
        if (profile.getDob() != null) {
            existingUser.setDob(profile.getDob());
        }
        if (profile.getGender() != null) {
            existingUser.setGender(profile.getGender());
        }

        return customerProfileRepository.updateUser(existingUser);
    }

    /**
     * Lấy profile của cleaner
     * Tương tự như GET /{cleanerId} trong ASP.NET CustomerProfileController
     */
    public CleanerProfile getCleanerProfile(String cleanerId) throws SQLException {
        if (cleanerId == null || cleanerId.trim().isEmpty()) {
            throw new IllegalArgumentException("CleanerId không được để trống");
        }

        CleanerProfile cleanerProfile = customerProfileRepository.getCleanerProfile(cleanerId);
        if (cleanerProfile == null) {
            throw new IllegalArgumentException("Không tìm thấy nhân viên với ID: " + cleanerId);
        }

        return cleanerProfile;
    }

    /**
     * DTO cho Customer Profile - mở rộng từ CustomerService.CustomerProfile
     */
    public static class CustomerProfile {
        private final String userId;
        private final String fullName;
        private final String email;
        private final String phoneNumber;
        private final String avatarUrl;
        private final String dob;
        private final Boolean gender;

        public CustomerProfile(String userId, String fullName, String email, String phoneNumber, String avatarUrl) {
            this(userId, fullName, email, phoneNumber, avatarUrl, null, null);
        }

        public CustomerProfile(String userId, String fullName, String email, String phoneNumber, String avatarUrl, String dob, Boolean gender) {
            this.userId = userId;
            this.fullName = fullName;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.avatarUrl = avatarUrl;
            this.dob = dob;
            this.gender = gender;
        }

        // Getters
        public String getUserId() { return userId; }
        public String getFullName() { return fullName; }
        public String getEmail() { return email; }
        public String getPhoneNumber() { return phoneNumber; }
        public String getAvatarUrl() { return avatarUrl; }
        public String getDob() { return dob; }
        public Boolean getGender() { return gender; }

        // Builder pattern để tạo instance mới với thay đổi
        public CustomerProfile withFullName(String fullName) {
            return new CustomerProfile(userId, fullName, email, phoneNumber, avatarUrl, dob, gender);
        }

        public CustomerProfile withEmail(String email) {
            return new CustomerProfile(userId, fullName, email, phoneNumber, avatarUrl, dob, gender);
        }

        public CustomerProfile withPhoneNumber(String phoneNumber) {
            return new CustomerProfile(userId, fullName, email, phoneNumber, avatarUrl, dob, gender);
        }

        public CustomerProfile withAvatarUrl(String avatarUrl) {
            return new CustomerProfile(userId, fullName, email, phoneNumber, avatarUrl, dob, gender);
        }

        public CustomerProfile withDob(String dob) {
            return new CustomerProfile(userId, fullName, email, phoneNumber, avatarUrl, dob, gender);
        }

        public CustomerProfile withGender(Boolean gender) {
            return new CustomerProfile(userId, fullName, email, phoneNumber, avatarUrl, dob, gender);
        }
    }
} 