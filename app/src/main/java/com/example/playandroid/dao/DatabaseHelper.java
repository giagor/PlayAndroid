package com.example.playandroid.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    
    private static final String CREATE_ARTICLE = "create table Article(" 
            + "id integer primary key,"
            + "title text," 
            + "author text,"
            + "link text," 
            + "time text)";
    
    private static final String SEARCH_HISTORY_TABLE = "create table SearchHistory("
            + "id integer primary key autoincrement,"        
            + "query_content text)";
    
    public DatabaseHelper(@Nullable Context context, @Nullable String name,
                          @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ARTICLE);
        db.execSQL(SEARCH_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Article");
        db.execSQL("drop table if exists SearchHistory");
        onCreate(db);
    }
}
