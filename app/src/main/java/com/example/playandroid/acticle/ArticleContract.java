package com.example.playandroid.acticle;

import android.database.sqlite.SQLiteDatabase;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.base.BaseView;
import com.example.playandroid.dao.ArticleDao;
import com.example.playandroid.entity.Article;

import java.util.List;

public interface ArticleContract {
    interface Presenter extends BasePresenter {
        //网络获取
        void getArticles(int pageIndex);
        void refreshArticles(int pageIndex);

        //dao操作
        void insertArticles(SQLiteDatabase db, List<Article> articles);
        void deleteAllArticles(SQLiteDatabase db);
        void getAllArticles(SQLiteDatabase db);
    }
    
    interface OnView extends BaseView<Presenter> {
        //从网络获取
        void onSuccess(int pageCount,List<Article> articles);
        void onFail(Exception e);
        void onLoadMoreSuccess(List<Article> articles);
        void onLoadMoreFailure(Exception e);
        void onRefreshSuccess(int pageCount,List<Article> articles);
        void onRefreshFailure(Exception e);

        //从dao获取
        void getArticlesFromDaoSuccess(List<Article> articles);
    }
}
