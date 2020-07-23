package com.example.playandroid.frame.frame_child;

import com.example.playandroid.data.FrameArticleModel;
import com.example.playandroid.data.FrameArticleModelImpl;
import com.example.playandroid.data.FrameModel;
import com.example.playandroid.data.FrameModelImpl;
import com.example.playandroid.entity.Article;

import java.util.List;

public class FrameChildPagerPresenter implements FrameChildPagerContract.Presenter, 
        FrameArticleModel.OnListener {
    private FrameChildPagerContract.OnView mView;
    private FrameArticleModel mModel;

    public FrameChildPagerPresenter(FrameChildPagerContract.OnView view) {
        mView = view;
        mView.setPresenter(this);
        mModel = new FrameArticleModelImpl();
    }

    @Override
    public void getFrameArticles(long id) {
        mModel.getFrameArticles(this,id);
    }

    @Override
    public void start() {
    
    }

    @Override
    public void onGetFrameArticlesSuccess(List<Article> articles) {
        mView.onGetFrameArticlesSuccess(articles);
    }

    @Override
    public void onGetFrameArticlesFailure(Exception e) {
        mView.onGetFrameArticlesFailure(e);
    }
}
