package com.example.MovieInABox.data.model;

public class Service {

    private Integer serviceId;
    private String name;
    private String description;

    public Service() {
    }

    public Service(Integer serviceid, String name, String description) {
        this.serviceId = serviceid;
        this.name = name;
        this.description = description;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
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
