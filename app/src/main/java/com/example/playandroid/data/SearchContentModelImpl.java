package com.example.playandroid.data;

import android.util.Log;

import com.example.playandroid.entity.Article;
import com.example.playandroid.util.HtmlFilter;
import com.example.playandroid.util.network.Call;
import com.example.playandroid.util.network.Callback;
import com.example.playandroid.util.network.FormBody;
import com.example.playandroid.util.network.HttpClient;
import com.example.playandroid.util.network.Request;
import com.example.playandroid.util.network.RequestBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.playandroid.util.URLConstant.SEARCH_CONTENT;

public class SearchContentModelImpl implements SearchContentModel {
    private static final String TAG = "SearchContentModelImpl";
    private List<Article> mArticles = new ArrayList<>();
    
    @Override
    public void getSearchContent(String keyWord, final OnListener onListener) {
        RequestBody body = new FormBody.Builder()
                .add("k",keyWord)
                .build();
        
        Request request = new Request.Builder()
                .url(String.format(SEARCH_CONTENT,0))
                .post(body)
                .build();
        
        HttpClient client = new HttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Exception e) {
                onListener.onSearchContentFailure(e);
            }

            @Override
            public void onResponse(String response) {
                try {
                    handleJson(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onListener.onSearchContentSuccess(mArticles);
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
            //标题这里要过滤掉Html标签
            String title = HtmlFilter.delHTMLTag(articleJson.getString("title"));
            String author = articleJson.getString("author");
            String link = articleJson.getString("link");
            String time = articleJson.getString("niceDate");

            Article article = new Article(id,title,author,link,time);
            mArticles.add(article);
        }
    }
}
