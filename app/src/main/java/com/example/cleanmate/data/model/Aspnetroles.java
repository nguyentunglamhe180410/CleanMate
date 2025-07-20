package com.example.cleanmate.data.model;

public class Aspnetroles {

    private String id;
    private String name;
    private String normalizedname;
    private Object concurrencystamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNormalizedname() {
        return normalizedname;
    }

    public void setNormalizedname(String normalizedname) {
        this.normalizedname = normalizedname;
    }

    public Object getConcurrencystamp() {
        return concurrencystamp;
    }

    public void setConcurrencystamp(Object concurrencystamp) {
        this.concurrencystamp = concurrencystamp;
    }

}
