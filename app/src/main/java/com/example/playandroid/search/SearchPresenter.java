package com.example.playandroid.search;

import com.example.playandroid.data.HotWordModel;
import com.example.playandroid.data.HotWordModelImpl;
import com.example.playandroid.data.SearchContentModel;
import com.example.playandroid.data.SearchContentModelImpl;
import com.example.playandroid.entity.Article;
import com.example.playandroid.entity.HotWord;

import java.util.List;

public class SearchPresenter implements SearchContract.Presenter,HotWordModel.OnListener, 
        SearchContentModel.OnListener {
    private SearchContract.OnView mView;
    private HotWordModel mHotWordModel;
    private SearchContentModel mSearchContentModel;
    
    public SearchPresenter(SearchContract.OnView view) {
        mView = view;
        view.setPresenter(this);
        mHotWordModel = new HotWordModelImpl();
        mSearchContentModel = new SearchContentModelImpl();
    }

    @Override
    public void start() {
        getHotWords();
    }
    
    @Override
    public void getHotWords() {
        mHotWordModel.getHotWords(this);
    }

    @Override
    public void searchContents(String keyword) {
        mSearchContentModel.getSearchContent(keyword,this);
    }

    @Override
    public void onGetHotWordsSuccess(List<HotWord> hotWords) {
        mView.onGetHotWordsSuccess(hotWords);
    }

    @Override
    public void onGetHotWordsFailure(Exception e) {
        mView.onGetHotWordsFailure(e);
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
