package com.example.playandroid.search.search_content;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.base.BaseView;
import com.example.playandroid.entity.Article;

import java.util.List;

public interface SearchContentContract {
    interface Presenter extends BasePresenter {
        void searchContents(String keyword);
    }
    
    interface OnView extends BaseView<Presenter>{
        void onSearchContentSuccess(List<Article> articles);
        void onSearchContentFailure(Exception e);
    }
}
