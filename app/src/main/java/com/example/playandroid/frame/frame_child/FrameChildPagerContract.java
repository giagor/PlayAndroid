package com.example.playandroid.frame.frame_child;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.base.BaseView;
import com.example.playandroid.entity.Article;

import java.util.List;

public interface FrameChildPagerContract {
    interface Presenter extends BasePresenter {
        void getFrameArticles(long id);
    }
    
    interface OnView extends BaseView<Presenter> {
        void onGetFrameArticlesSuccess(List<Article> articles);
        void onGetFrameArticlesFailure(Exception e);
    }
}
