package com.example.playandroid.acticle;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.base.BaseView;
import com.example.playandroid.entity.Article;

import java.util.List;

public interface ArticleContract {
    interface Presenter extends BasePresenter {
        void getArticles(int pageIndex);
    }
    
    interface OnView extends BaseView<Presenter> {
        void onSuccess(int pageCount,List<Article> articles);
        void onFail(Exception e);
        void onLoadMoreSuccess(List<Article> articles);
        void onLoadMoreFailure(Exception e);
    }
}
