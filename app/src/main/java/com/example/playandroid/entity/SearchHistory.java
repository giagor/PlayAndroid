package com.example.playandroid.entity;

/**
 * 搜索历史的实体类.
 */
public class SearchHistory extends FlowLayoutBean {
    /**
     * 搜索历史在数据库中的id.
     */
    private long id;
    
    private String name;

    public SearchHistory(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
