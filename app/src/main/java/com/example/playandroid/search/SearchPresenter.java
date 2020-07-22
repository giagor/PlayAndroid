package com.example.playandroid.search;

import com.example.playandroid.data.HotWordModel;
import com.example.playandroid.data.HotWordModelImpl;
import com.example.playandroid.entity.HotWord;

import java.util.List;

public class SearchPresenter implements SearchContract.Presenter,HotWordModel.OnListener {
    private SearchContract.OnView mView;
    private HotWordModel mHotWordModel;
    
    public SearchPresenter(SearchContract.OnView view) {
        mView = view;
        view.setPresenter(this);
        mHotWordModel = new HotWordModelImpl();
    }

    @Override
    public void getHotWords() {
        mHotWordModel.getHotWords(this);
    }

    @Override
    public void start() {
        getHotWords();
    }

    @Override
    public void onSuccess(List<HotWord> hotWords) {
        mView.onSuccess(hotWords);
    }

    @Override
    public void onFailure(Exception e) {
        mView.onFailure(e);
    }
}
