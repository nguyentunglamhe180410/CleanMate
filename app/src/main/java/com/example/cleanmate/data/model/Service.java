package com.example.cleanmate.data.model;

public class Service {

    private Integer serviceid;
    private String name;
    private String
 description;

    public Service() {
    }

    public Service(Integer serviceid, String name, String description) {
        this.serviceid = serviceid;
        this.name = name;
        this.description = description;
    }

    public Integer getServiceid() {
        return serviceid;
    }

    public void setServiceid(Integer serviceid) {
        this.serviceid = serviceid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
