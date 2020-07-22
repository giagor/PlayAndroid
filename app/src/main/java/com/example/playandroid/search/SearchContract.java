package com.example.playandroid.search;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.base.BaseView;
import com.example.playandroid.entity.HotWord;

import java.util.List;

public interface SearchContract{
    interface OnView extends BaseView<Presenter> {
        void onSuccess(List<HotWord> hotWords);
        void onFailure(Exception e);
    }
    
    interface Presenter extends BasePresenter{
        void getHotWords();
    }
}
