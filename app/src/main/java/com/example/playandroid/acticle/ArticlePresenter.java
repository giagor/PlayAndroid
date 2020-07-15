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
    public void onSuccess(List<Article> articles) {
        mArticleView.onSuccess(articles);
    }

    @Override
    public void onFail(Exception e) {
        mArticleView.onFail(e);
    }

    @Override
    public void getArticles() {
        mArticleModel.getArticles(this);
    }
}
