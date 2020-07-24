package com.example.playandroid.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.playandroid.entity.Article;

import java.util.ArrayList;
import java.util.List;

import static com.example.playandroid.util.Constants.DatabaseConstant.ARTICLE_TABLE;

public class ArticleDaoImpl implements ArticleDao {

    /**
     * 插入列表的文章的数据.
     */
    @Override
    public void insertArticles(SQLiteDatabase db, List<Article> articles) {
        ContentValues values = new ContentValues();
        for (Article article : articles) {
            values.put("id", article.getId());
            values.put("title", article.getTitle());
            values.put("author", article.getAuthor());
            values.put("link", article.getLink());
            values.put("time", article.getTime());
            db.insert(ARTICLE_TABLE, null, values);
            values.clear();
        }
    }

    /**
     * 删除表中所有的数据.
     */
    @Override
    public void deleteAllArticles(SQLiteDatabase db) {
        db.delete(ARTICLE_TABLE, null, null);
    }

    /**
     * 读取数据.
     */
    @Override
    public void getAllArticles(SQLiteDatabase db,OnListener listener) {
        Cursor cursor = db.query(ARTICLE_TABLE,null,null,null,
                null,null,null);
        List<Article> articles = new ArrayList<>();
        //遍历获取数据
        if(cursor.moveToFirst()){
            do{
                long id = cursor.getLong(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                String link = cursor.getString(cursor.getColumnIndex("link"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                Article article = new Article(id,title,author,link,time);
                articles.add(article);
            }while (cursor.moveToNext());
        }
        cursor.close();
        listener.getArticlesFromDaoSuccess(articles);
    }
}
