package com.example.playandroid.data;

import com.example.playandroid.entity.Article;

import java.util.List;

public interface ArticleModel {
    void getArticles(OnListener onListener);

    interface OnListener {
        /**
         * 成功得到文章列表
         */
        void onSuccess(List<Article> articles);

        /**
         * 没能得到文章列表
         */
        void onFail(Exception e);
    }
}
