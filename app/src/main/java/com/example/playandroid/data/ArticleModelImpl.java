package com.example.playandroid.data;

import com.example.playandroid.entity.Article;
import com.example.playandroid.util.URLConstant;
import com.example.playandroid.util.network.Call;
import com.example.playandroid.util.network.Callback;
import com.example.playandroid.util.network.HttpClient;
import com.example.playandroid.util.network.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArticleModelImpl implements ArticleModel{
    private List<Article> mArticles = new ArrayList<>();
    
    @Override
    public void getArticles(final OnListener onListener,int pageIndex) {
        Request request = new Request.Builder()
                .url(String.format(URLConstant.ARTICLE_URL,pageIndex))
                .build();
        HttpClient httpClient = new HttpClient();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Exception e) {
                onListener.onGetArticlesFailure(e);
            }

            @Override
            public void onResponse(String response) {
                try {
                    handleJson(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onListener.onGetArticlesSuccess(mArticles);
            }
        });
    }
    
    private void handleJson(String response) throws JSONException {
        mArticles.clear();
        
        JSONObject jsonObject = new JSONObject(response);
        
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray datas = data.getJSONArray("datas");
        
        for(int i = 0;i<datas.length();i++){
            JSONObject articleJson = datas.getJSONObject(i);
            
            long id = articleJson.getLong("id");
            String title = articleJson.getString("title");
            String author = articleJson.getString("author");
            String link = articleJson.getString("link");
            String time = articleJson.getString("niceDate");
            
            Article article = new Article(id,title,author,link,time);
            mArticles.add(article);
        }
    }
}
