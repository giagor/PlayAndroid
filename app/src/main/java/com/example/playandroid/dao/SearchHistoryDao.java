package com.example.playandroid.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public interface SearchHistoryDao {
    /**
     * 插入一条历史搜索记录.
     */
    void insertHistory(SQLiteDatabase db, String queryContent);

    /**
     * 清空搜索历史.
     */
    void deleteAllHistories(SQLiteDatabase db);

    /**
     * 获得所有的历史搜索记录.
     */
    void getAllHistories(SQLiteDatabase db, OnListener listener);

    interface OnListener {
        /**
         * 从数据库中获取历史搜索记录.
         */
        void getHistoriesFromDaoSuccess(List<String> histories);
    }
}
