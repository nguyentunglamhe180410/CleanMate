package com.example.MovieInABox.models.viewmodels.authen;

public class ForgotPasswordModel {
    private String email;

    public ForgotPasswordModel() {}

    public ForgotPasswordModel(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
