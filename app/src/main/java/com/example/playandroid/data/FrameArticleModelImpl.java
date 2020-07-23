package com.example.playandroid.data;

import com.example.playandroid.entity.Article;
import com.example.playandroid.util.network.Call;
import com.example.playandroid.util.network.Callback;
import com.example.playandroid.util.network.HttpClient;
import com.example.playandroid.util.network.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.playandroid.util.URLConstant.FRAME_ARTICLE;

public class FrameArticleModelImpl implements FrameArticleModel {
    private List<Article> mArticles = new ArrayList<>(); 
    
    @Override
    public void getFrameArticles(final OnListener onListener, long id) {
        Request request = new Request.Builder()
                .url(String.format(FRAME_ARTICLE,0,id))
                .build();
        HttpClient client = new HttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Exception e) {
                onListener.onGetFrameArticlesFailure(e);
            }

            @Override
            public void onResponse(String response) {
                try {
                    handlerJson(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onListener.onGetFrameArticlesSuccess(mArticles);
            }
        });
    }
    
    private void handlerJson(String response) throws JSONException{
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
