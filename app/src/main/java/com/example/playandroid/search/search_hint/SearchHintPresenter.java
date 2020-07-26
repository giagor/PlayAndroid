package com.example.playandroid.search.search_hint;

import android.database.sqlite.SQLiteDatabase;

import com.example.playandroid.dao.SearchHistoryDao;
import com.example.playandroid.dao.SearchHistoryDaoImpl;
import com.example.playandroid.data.HotWordModel;
import com.example.playandroid.data.HotWordModelImpl;
import com.example.playandroid.entity.HotWord;
import com.example.playandroid.entity.SearchHistory;

import java.util.List;

public class SearchHintPresenter implements SearchHintContract.Presenter,HotWordModel.OnListener
        ,SearchHistoryDao.OnListener {
    
    private SearchHintContract.OnView mView;
    private HotWordModel mModel;
    private SearchHistoryDao mDao;

    SearchHintPresenter(SearchHintContract.OnView view) {
        mView = view;
        mView.setPresenter(this);
        mModel = new HotWordModelImpl();
        mDao = new SearchHistoryDaoImpl();
    }

    @Override
    public void getHotWords() {
        mModel.getHotWords(this);
    }

    @Override
    public void insertHistory(SQLiteDatabase db, String queryContent) {
        mDao.insertHistory(db,queryContent);
    }

    @Override
    public void deleteAllHistories(SQLiteDatabase db) {
        mDao.deleteAllHistories(db);
    }

    @Override
    public void getAllHistories(SQLiteDatabase db) {
        mDao.getAllHistories(db,this);
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

    @Override
    public void getHistoriesFromDaoSuccess(List<SearchHistory> histories) {
        mView.getHistoriesFromDaoSuccess(histories);
    }
}
