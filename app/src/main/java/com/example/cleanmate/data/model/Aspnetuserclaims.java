package com.example.cleanmate.data.model;

public class Aspnetuserclaims {

    private Integer id;
    private String userid;
    private Object claimtype;
    private Object claimvalue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Object getClaimtype() {
        return claimtype;
    }

    public void setClaimtype(Object claimtype) {
        this.claimtype = claimtype;
    }

    public Object getClaimvalue() {
        return claimvalue;
    }

    public void setClaimvalue(Object claimvalue) {
        this.claimvalue = claimvalue;
    }

}
