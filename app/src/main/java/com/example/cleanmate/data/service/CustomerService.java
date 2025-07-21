package com.example.cleanmate.data.service;

import com.example.cleanmate.data.model.User;
import com.example.cleanmate.data.model.dto.*;
import com.example.cleanmate.data.repository.CustomerRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    /** Returns list of all customers. */
    public List<dto.CustomerListItemDTO> getCustomerList() throws SQLException {
        return repository.getCustomerList();
    }

    /** Locks the user account; returns true if successful. */
    public boolean lockUserAccount(String userId) {
        try {
            return repository.lockUserAccount(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Unlocks the user account; returns true if successful. */
    public boolean unlockUserAccount(String userId) {
        try {
            return repository.unlockUserAccount(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Returns basic profile info for UI display. */
    public CustomerProfile getCustomerProfile(String userId) throws SQLException {
        User u = repository.getUserById(userId);
        if (u == null) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        return new CustomerProfile(
                u.getUserId(),
                u.getFullName(),
                u.getEmail(),
                u.getPhoneNumber(),
                u.getProfileImage()
        );
    }

    /** Updates the user's profile and returns true if update succeeded. */
    public boolean updateCustomerProfile(CustomerProfile profile) {
        Objects.requireNonNull(profile.getUserId(), "userId must not be null");
        try {
            User u = repository.getUserById(profile.getUserId());
            if (u == null) throw new IllegalArgumentException("User not found: " + profile.getUserId());

            if (profile.getFullName() != null)    u.setFullName(profile.getFullName());
            if (profile.getEmail() != null)       u.setEmail(profile.getEmail());
            if (profile.getPhoneNumber() != null) u.setPhoneNumber(profile.getPhoneNumber());
            if (profile.getAvatarUrl() != null)   u.setProfileImage(profile.getAvatarUrl());

            return repository.updateUser(u);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /** Returns detailed customer info. */
    public dto.CustomerDetailDTO getCustomerDetail(String userId) throws SQLException {
        dto.CustomerDetailDTO detail = repository.getCustomerDetail(userId);
        if (detail == null) {
            throw new IllegalArgumentException("Customer detail not found for: " + userId);
        }
        return detail;
    }

    /** Simple DTO for profile editing. */
    public static class CustomerProfile {
        private final String userId;
        private final String fullName;
        private final String email;
        private final String phoneNumber;
        private final String avatarUrl;

        public CustomerProfile(String userId, String fullName, String email, String phoneNumber, String avatarUrl) {
            this.userId      = userId;
            this.fullName    = fullName;
            this.email       = email;
            this.phoneNumber = phoneNumber;
            this.avatarUrl   = avatarUrl;
        }

        public String getUserId()      { return userId; }
        public String getFullName()    { return fullName; }
        public String getEmail()       { return email; }
        public String getPhoneNumber() { return phoneNumber; }
        public String getAvatarUrl()   { return avatarUrl; }
    }
}

