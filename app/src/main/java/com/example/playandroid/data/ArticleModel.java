package com.example.playandroid.data;

import com.example.playandroid.entity.Article;

import java.util.List;

/**
 * 获得首页文章列表的数据.
 */
public interface ArticleModel {
    /**
     * 刚进入页面时获取文章或加载更多文章.
     * */
    void getOrLoadMoreArticles(OnListener onListener, int pageIndex);
    
    /**
     * 刷新文章.
     * */
    void refreshArticles(OnListener onListener, int pageIndex);

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

        /**
         * 刷新成功.
         * */
        void onRefreshSuccess(int pageCount,List<Article> articles);
        
        /**
         * 刷新失败.
         * */
        void onRefreshFailure(Exception e);
    }
}
