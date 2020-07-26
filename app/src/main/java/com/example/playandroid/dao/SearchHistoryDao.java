package com.example.playandroid.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public interface SearchHistoryDao {
    void insertHistory(SQLiteDatabase db,String queryContent);
    void deleteAllHistories(SQLiteDatabase db);
    
    interface OnListener{
        void getHistoriesFromDaoSuccess(List<String> histories);
    }
}
