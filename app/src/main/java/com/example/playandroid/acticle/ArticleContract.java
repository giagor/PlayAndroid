package com.example.playandroid.acticle;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.base.BaseView;
import com.example.playandroid.entity.Article;

import java.util.List;

public interface ArticleContract {
    interface Presenter extends BasePresenter {
        void getArticles();
    }
    
    interface OnView extends BaseView<Presenter> {
        void onSuccess(List<Article> acticles);
        void onFail(Exception e);
    }
}
