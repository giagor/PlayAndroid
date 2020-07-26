package com.example.playandroid.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.playandroid.util.Constants.DatabaseConstant.SEARCH_HISTORY_TABLE;

public class SearchHistoryDaoImpl implements SearchHistoryDao {
    @Override
    public void insertHistory(SQLiteDatabase db, String queryContent) {
        ContentValues values = new ContentValues();
        values.put("query_content",queryContent);
        db.insert(SEARCH_HISTORY_TABLE,null,values);
    }

    @Override
    public void deleteAllHistories(SQLiteDatabase db) {
        db.delete(SEARCH_HISTORY_TABLE,null,null);
    }

    @Override
    public void getAllHistories(SQLiteDatabase db, OnListener listener) {
        List<String> histories = new ArrayList<>();
        Cursor cursor = db.query(SEARCH_HISTORY_TABLE,null,null,null
                ,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String str = cursor.getString(cursor.getColumnIndex("query_content"));
                histories.add(str);
            }while (cursor.moveToNext());
        }
        cursor.close();
        
        listener.getHistoriesFromDaoSuccess(histories);
    }
}
