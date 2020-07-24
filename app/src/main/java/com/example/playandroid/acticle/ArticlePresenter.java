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

   
}
