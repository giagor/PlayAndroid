package com.example.playandroid.entity;

public class article {
    private long id;
    private String title;
    private String author;
    private String link;

    public article() {
    }

    public article(long id, String title, String author, String link) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.link = link;
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
