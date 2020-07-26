package com.example.playandroid.search.search_hint;

import android.database.sqlite.SQLiteDatabase;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.base.BaseView;
import com.example.playandroid.entity.HotWord;
import com.example.playandroid.entity.SearchHistory;

import java.util.List;

public interface SearchHintContract {
    interface OnView extends BaseView<Presenter>{
        //网络获取
        void onGetHotWordsSuccess(List<HotWord> hotWords);
        void onGetHotWordsFailure(Exception e);
        
        //dao操作
        void getHistoriesFromDaoSuccess(List<SearchHistory> histories);
    }
    
    interface Presenter extends BasePresenter{
        //网络获取
        void getHotWords();
        
        //dao操作
        void insertHistory(SQLiteDatabase db, String queryContent);
        void deleteAllHistories(SQLiteDatabase db);
        void getAllHistories(SQLiteDatabase db);
    }
    
}
