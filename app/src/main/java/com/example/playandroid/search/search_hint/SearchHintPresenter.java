package com.example.playandroid.search.search_hint;

import com.example.playandroid.data.HotWordModel;
import com.example.playandroid.data.HotWordModelImpl;
import com.example.playandroid.entity.HotWord;

import java.util.List;

public class SearchHintPresenter implements SearchHintContract.Presenter,HotWordModel.OnListener {
    
    private SearchHintContract.OnView mView;
    private HotWordModel mModel;

    SearchHintPresenter(SearchHintContract.OnView view) {
        mView = view;
        mView.setPresenter(this);
        mModel = new HotWordModelImpl();
    }

    @Override
    public void getHotWords() {
        mModel.getHotWords(this);
    }

    @Override
    public void start() {
        getHotWords();
    }

    @Override
    public void onGetHotWordsSuccess(List<HotWord> hotWords) {
        mView.onGetHotWordsSuccess(hotWords);
    }

    @Override
    public void onGetHotWordsFailure(Exception e) {
        mView.onGetHotWordsFailure(e);
    }
}
