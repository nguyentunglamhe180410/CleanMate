package com.example.cleanmate.data.ui;

public class CleanerUI {
    private String cleanerId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String area;
    private int experienceYear;
    private boolean available;
    private String avatar;

    public CleanerUI(String cleanerId, String fullName, String email, String phoneNumber,
                   String area, int experienceYear, boolean available, String avatar) {
        this.cleanerId = cleanerId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.area = area;
        this.experienceYear = experienceYear;
        this.available = available;
        this.avatar = avatar;
    }

    public String getCleanerId() { return cleanerId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getArea() { return area; }
    public int getExperienceYear() { return experienceYear; }
    public boolean isAvailable() { return available; }
    public String getAvatar() { return avatar; }

    public void setAvailable(boolean available) { this.available = available; }
}
