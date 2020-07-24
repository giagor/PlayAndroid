package com.example.playandroid.acticle;

import android.database.sqlite.SQLiteDatabase;

import com.example.playandroid.dao.ArticleDao;
import com.example.playandroid.dao.ArticleDaoImpl;
import com.example.playandroid.data.ArticleModel;
import com.example.playandroid.data.ArticleModelImpl;
import com.example.playandroid.entity.Article;

import java.util.List;

public class ArticlePresenter implements ArticleContract.Presenter, ArticleModel.OnListener, ArticleDao.OnListener {
    
    private ArticleModel mArticleModel;
    private ArticleContract.OnView mArticleView;
    private ArticleDao mDao;

    public ArticlePresenter(ArticleContract.OnView articleView) {
        mArticleView = articleView;
        articleView.setPresenter(this);
        mArticleModel = new ArticleModelImpl();
        mDao = new ArticleDaoImpl();
    }

    @Override
    public void start() {
    }

    @Override
    public void onGetArticlesSuccess(int pageCount,List<Article> articles) {
        mArticleView.onSuccess(pageCount,articles);
    }

    @Override
    public void onGetArticlesFailure(Exception e) {
        mArticleView.onFail(e);
    }

    @Override
    public void onLoadMoreSuccess(List<Article> articles) {
        mArticleView.onLoadMoreSuccess(articles);
    }

    @Override
    public void onLoadMoreFailure(Exception e) {
        
    }

    @Override
    public void getArticles(int pageIndex) {
        mArticleModel.getArticles(this,pageIndex);
    }

    @Override
    public void insertArticles(SQLiteDatabase db, List<Article> articles) {
        mDao.insertArticles(db,articles);
    }

    @Override
    public void deleteAllArticles(SQLiteDatabase db) {
        mDao.deleteAllArticles(db);
    }

    @Override
    public void getAllArticles(SQLiteDatabase db) {
        mDao.getAllArticles(db,this);
    }


    @Override
    public void getArticlesFromDaoSuccess(List<Article> articles) {
        mArticleView.getArticlesFromDaoSuccess(articles);
    }
}
