package com.example.MovieInABox.data.model;

public class User {

    private String userId;
    private Boolean gender;
    private String dob;
    private String createdDate;
    private String bankName;
    private String bankNo;
    private String profileImage;
    private String fullName;
    private String cccd;
    private String userName;
    private String email;
    private Boolean emailConfirmed;
    private String passwordHash;
    private String phoneNumber;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(Boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User() {
    }

    public User(String userId, String createddate, String bankName, String bankNo, String fullName, String cccd, String username, String email, Boolean emailConfirmed, String passwordHash, String phoneNumber) {
        this.userId = userId;
        this.createdDate = createddate;
        this.bankName = bankName;
        this.bankNo = bankNo;
        this.fullName = fullName;
        this.cccd = cccd;
        this.userName = username;
        this.email = email;
        this.emailConfirmed = emailConfirmed;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
    }

    public User(String userId, String createddate, String fullName, String username, String email, Boolean emailConfirmed, String passwordHash, String phoneNumber) {
        this.userId = userId;
        this.createdDate = createddate;
        this.fullName = fullName;
        this.userName = username;
        this.email = email;
        this.emailConfirmed = emailConfirmed;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
    }
}
