package com.example.playandroid.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 知识体系(一级).
 */
public class Frame implements Serializable {
    private long id;
    private String name;
    private List<FrameChild> frameChildren;

    public Frame() {
    }

    public Frame(long id, String name, List<FrameChild> frameChildren) {
        this.id = id;
        this.name = name;
        this.frameChildren = frameChildren;
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

    public List<FrameChild> getFrameChildren() {
        return frameChildren;
    }

    public void setFrameChildren(List<FrameChild> frameChildren) {
        this.frameChildren = frameChildren;
    }
}
