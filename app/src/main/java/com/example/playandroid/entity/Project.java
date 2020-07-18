package com.example.playandroid.entity;

/**
 * 项目实体类(一级项目)
 * */
public class Project {
    private long id;
    private String name;

    public Project() {
    }

    public Project(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
