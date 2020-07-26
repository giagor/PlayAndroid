package com.example.playandroid.search.search_content;

import android.util.Log;

import com.example.playandroid.data.SearchContentModel;
import com.example.playandroid.data.SearchContentModelImpl;
import com.example.playandroid.entity.Article;

import java.util.List;

public class SearchContentPresenter implements SearchContentContract.Presenter,
        SearchContentModel.OnListener {
   
    private static final String TAG = "SearchContentPresenter";
    private SearchContentContract.OnView mView;
    private SearchContentModel mModel;

    public SearchContentPresenter(SearchContentContract.OnView view) {
        mView = view;
        mView.setPresenter(this);
        mModel = new SearchContentModelImpl();
    }

    @Override
    public void searchContents(String keyword) {
        mModel.getSearchContent(keyword,this);
    }

    @Override
    public void start() {
    }

    @Override
    public void onSearchContentSuccess(List<Article> articles) {
        mView.onSearchContentSuccess(articles);
    }

    @Override
    public void onSearchContentFailure(Exception e) {
        mView.onSearchContentFailure(e);
    }
}
