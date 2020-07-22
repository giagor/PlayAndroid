package com.example.playandroid.entity;

import android.print.PrinterId;

public class Article {
    private long id;
    private String title;
    private String author;
    private String link;
    private String time;

    public Article() {
    }

    public Article(long id, String title, String author, String link,String time) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.link = link;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
