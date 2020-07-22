package com.example.playandroid.entity;

import java.util.List;

/**
 * 子体系(二级体系).
 */
public class FrameChild extends FlowLayoutBean{
    private long id;
    private String name;

    public FrameChild() {
    }

    public FrameChild(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return null;
    }

}

