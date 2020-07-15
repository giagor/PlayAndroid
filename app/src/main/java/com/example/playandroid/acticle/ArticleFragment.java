package com.example.playandroid.acticle;

import android.os.Bundle;

import com.example.playandroid.entity.Article;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ArticleFragment extends Fragment implements ArticleContract.OnView{
    private ArticleContract.Presenter mArticlePresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ArticlePresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mArticlePresenter.start();
    }

    @Override
    public void setPresenter(ArticleContract.Presenter presenter) {
        mArticlePresenter = presenter;
    }

    @Override
    public void onSuccess(List<Article> articles) {
        
    }

    @Override
    public void onFail(Exception e) {

    }
}
