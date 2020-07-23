package com.example.playandroid.acticle;

import com.example.playandroid.data.ArticleModel;
import com.example.playandroid.data.ArticleModelImpl;
import com.example.playandroid.entity.Article;

import java.util.List;

public class ArticlePresenter implements ArticleContract.Presenter, ArticleModel.OnListener {
    
    private ArticleModel mArticleModel;
    private ArticleContract.OnView mArticleView;

    public ArticlePresenter(ArticleContract.OnView articleView) {
        mArticleView = articleView;
        articleView.setPresenter(this);
        mArticleModel = new ArticleModelImpl();
    }

    @Override
    public void start() {
    }

    @Override
    public void onGetArticlesSuccess(List<Article> articles) {
        mArticleView.onSuccess(articles);
    }

    @Override
    public void onGetArticlesFailure(Exception e) {
        mArticleView.onFail(e);
    }

    @Override
    public void onLoadMoreSuccess(List<Article> articles) {
        
    }

    @Override
    public void onLoadMoreFailure(Exception e) {
        
    }

    @Override
    public void getArticles(int pageIndex) {
        mArticleModel.getArticles(this,pageIndex);
    }

   
}
