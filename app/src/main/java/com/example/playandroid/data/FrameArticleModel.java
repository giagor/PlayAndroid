package com.example.playandroid.data;

import com.example.playandroid.entity.Article;

import java.util.List;

/**
 * 获取知识体系下的文章.
 */
public interface FrameArticleModel {

    void getFrameArticles(OnListener onListener,long id);
    
    interface OnListener{
        void onGetFrameArticlesSuccess(List<Article>articles);
        void onGetFrameArticlesFailure(Exception e);
    }
}
