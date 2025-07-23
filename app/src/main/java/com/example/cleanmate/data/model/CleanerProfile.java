package com.example.cleanmate.data.model;

import java.sql.Timestamp;

public class CleanerProfile {
    private String cleanerId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private Integer experienceYears;
    private String activeAreas;
    private String bankName;
    private String bankNo;
    private Double balance;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public CleanerProfile() {
    }

    public CleanerProfile(String cleanerId, String fullName, String email, String phoneNumber,
                         String address, Integer experienceYears, String activeAreas,
                         String bankName, String bankNo, Double balance, String status,
                         Timestamp createdAt, Timestamp updatedAt) {
        this.cleanerId = cleanerId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.experienceYears = experienceYears;
        this.activeAreas = activeAreas;
        this.bankName = bankName;
        this.bankNo = bankNo;
        this.balance = balance;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters
    public String getCleanerId() {
        return cleanerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public String getActiveAreas() {
        return activeAreas;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public Double getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setCleanerId(String cleanerId) {
        this.cleanerId = cleanerId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public void setActiveAreas(String activeAreas) {
        this.activeAreas = activeAreas;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "CleanerProfile{" +
                "cleanerId='" + cleanerId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", experienceYears=" + experienceYears +
                ", activeAreas='" + activeAreas + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankNo='" + bankNo + '\'' +
                ", balance=" + balance +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
