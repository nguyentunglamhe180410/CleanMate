package com.example.cleanmate.data.model;

public class Aspnetusers {

    private String id;
    private Boolean gender;
    private String dob;
    private String createddate;
    private String bankname;
    private String bankno;
    private String profileimage;
    private String fullname;
    private String cccd;
    private String username;
    private String email;
    private Boolean emailconfirmed;
    private String passwordhash;
    private String phonenumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankno() {
        return bankno;
    }

    public void setBankno(String bankno) {
        this.bankno = bankno;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailconfirmed() {
        return emailconfirmed;
    }

    public void setEmailconfirmed(Boolean emailconfirmed) {
        this.emailconfirmed = emailconfirmed;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Aspnetusers() {
    }

    public Aspnetusers(String id, String createddate, String bankname, String bankno, String fullname, String cccd, String username, String email, Boolean emailconfirmed, String passwordhash, String phonenumber) {
        this.id = id;
        this.createddate = createddate;
        this.bankname = bankname;
        this.bankno = bankno;
        this.fullname = fullname;
        this.cccd = cccd;
        this.username = username;
        this.email = email;
        this.emailconfirmed = emailconfirmed;
        this.passwordhash = passwordhash;
        this.phonenumber = phonenumber;
    }

    public Aspnetusers(String id, String createddate, String fullname, String username, String email, Boolean emailconfirmed, String passwordhash, String phonenumber) {
        this.id = id;
        this.createddate = createddate;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.emailconfirmed = emailconfirmed;
        this.passwordhash = passwordhash;
        this.phonenumber = phonenumber;
    }
}
