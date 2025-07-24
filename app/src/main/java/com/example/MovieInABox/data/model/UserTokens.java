package com.example.MovieInABox.data.model;

public class UserTokens {

    private String userid;
    private String loginprovider;
    private String name;
    private String value;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLoginprovider() {
        return loginprovider;
    }

    public void setLoginprovider(String loginprovider) {
        this.loginprovider = loginprovider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
