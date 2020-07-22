package com.example.playandroid.data;

import com.example.playandroid.entity.Article;

import java.util.List;

/**
 * 根据搜索关键词返回搜索内容.
 * */
public interface SearchContentModel {
    /**
     * @param keyWord 搜索的关键词.
     * */
    void getSearchContent(String keyWord,OnListener onListener);
    
    interface OnListener{
        void onSearchContentSuccess(List<Article> articles);
        void onSearchContentFailure(Exception e);
    }
}
