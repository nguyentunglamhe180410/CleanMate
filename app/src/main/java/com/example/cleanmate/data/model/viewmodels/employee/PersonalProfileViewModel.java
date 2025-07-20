package com.example.cleanmate.data.model.viewmodels.employee;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PersonalProfileViewModel {
    private String userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String avatarUrl;
    private String idCardNumber;
    private String bankName;
    private String bankNo;
    private boolean gender;
    private LocalDate dob;
    private String activeAreas;
    private Boolean isAvailable;
    private Integer experienceYears;
    private Double averageRating;
    private BigDecimal balance;

    public PersonalProfileViewModel() {}

    public PersonalProfileViewModel(BigDecimal balance, Double averageRating, Integer experienceYears, Boolean isAvailable, String activeAreas, LocalDate dob, boolean gender, String bankNo, String bankName, String idCardNumber, String avatarUrl, String phoneNumber, String email, String fullName, String userId) {
        this.balance = balance;
        this.averageRating = averageRating;
        this.experienceYears = experienceYears;
        this.isAvailable = isAvailable;
        this.activeAreas = activeAreas;
        this.dob = dob;
        this.gender = gender;
        this.bankNo = bankNo;
        this.bankName = bankName;
        this.idCardNumber = idCardNumber;
        this.avatarUrl = avatarUrl;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.fullName = fullName;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getActiveAreas() {
        return activeAreas;
    }

    public void setActiveAreas(String activeAreas) {
        this.activeAreas = activeAreas;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


}

