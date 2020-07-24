package com.example.playandroid.dao;

import android.database.sqlite.SQLiteDatabase;

import com.example.playandroid.entity.Article;

import java.util.List;

public interface ArticleDao {
    void insertArticles(SQLiteDatabase db,List<Article> articles);
    void deleteAllArticles(SQLiteDatabase db);
    void getAllArticles(SQLiteDatabase db,OnListener listener);
    
    interface OnListener{
        void getArticlesFromDaoSuccess(List<Article> articles);
    }
}
