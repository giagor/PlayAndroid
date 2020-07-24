package com.example.playandroid.data;

import com.example.playandroid.entity.Article;

import java.util.List;

/**
 * 获得首页文章列表的数据.
 */
public interface ArticleModel {
    void getArticles(OnListener onListener, int pageIndex);

    interface OnListener {
        /**
         * 成功得到文章列表
         */
        void onGetArticlesSuccess(int pageCount,List<Article> articles);

        /**
         * 没能得到文章列表
         */
        void onGetArticlesFailure(Exception e);

        /**
         * 加载更多成功.
         */
        void onLoadMoreSuccess(List<Article> articles);

        /**
         * 加载更多失败.
         */
        void onLoadMoreFailure(Exception e);
    }
}
