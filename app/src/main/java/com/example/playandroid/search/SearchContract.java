package com.example.playandroid.search;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.base.BaseView;
import com.example.playandroid.entity.Article;
import com.example.playandroid.entity.HotWord;

import java.util.List;

public interface SearchContract{
    interface OnView extends BaseView<Presenter> {
        void onGetHotWordsSuccess(List<HotWord> hotWords);
        void onGetHotWordsFailure(Exception e);
        void onSearchContentSuccess(List<Article> articles);
        void onSearchContentFailure(Exception e);
    }
    
    interface Presenter extends BasePresenter{
        void getHotWords();
        void searchContents(String keyword);
    }
}
