package com.example.playandroid.entity;

public class ProjectChild {
    private String picUrl;
    private String link;
    private String title;
    private String desc;
    private String author;
    private String date;

    public ProjectChild() {
    }

    public ProjectChild(String picUrl, String link, String title, String desc, String author, String date) {
        this.picUrl = picUrl;
        this.link = link;
        this.title = title;
        this.desc = desc;
        this.author = author;
        this.date = date;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
