package com.example.cleanmate.models.viewmodels.authen;

public class ChangePasswordModel {
    private String currentPassword;
    private String newPassword;

    public ChangePasswordModel() {}

    public ChangePasswordModel(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
