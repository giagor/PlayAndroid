package com.example.playandroid.entity;

/**
 * 搜索热词.
 * */
public class HotWord {
    private long id;
    private String name;

    public HotWord() {
    }

    public HotWord(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
