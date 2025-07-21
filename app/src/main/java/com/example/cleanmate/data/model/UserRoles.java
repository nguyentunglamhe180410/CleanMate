package com.example.cleanmate.data.model;

public class UserRoles {

    private String userId;
    private String roleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public UserRoles(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
