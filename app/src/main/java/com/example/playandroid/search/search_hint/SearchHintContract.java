package com.example.playandroid.search.search_hint;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.base.BaseView;
import com.example.playandroid.entity.HotWord;

import java.util.List;

public interface SearchHintContract {
    interface OnView extends BaseView<Presenter>{
        void onGetHotWordsSuccess(List<HotWord> hotWords);
        void onGetHotWordsFailure(Exception e);
    }
    
    interface Presenter extends BasePresenter{
        void getHotWords();
    }
    
}
