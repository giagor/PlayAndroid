package com.example.playandroid.util;

public class URLConstant {
    //------------------------------------------GET------------------------------------------
    //首页文章列表
    public static final String ARTICLE_URL = "https://www.wanandroid.com/article/list/0/json";
    //获得一级项目
    public static final String PROJECT_URL = "https://www.wanandroid.com/project/tree/json";
    //获取子项目
    public static final String CHILD_PROJECT_URL = "https://www.wanandroid.com/project/list/%1$s/" +
            "json?cid=%2$s";
    //获得搜索热词
    public static final String HOT_WORD = "https://www.wanandroid.com//hotkey/json";
    //获得一级体系
    public static final String FRAME = "https://www.wanandroid.com/tree/json";
    
    //---------------------------------------POST----------------------------------------------
    //根据搜索关键词获取搜索内容
    public static final String SEARCH_CONTENT = "https://www.wanandroid.com/article/query/%1$s/json";
}
