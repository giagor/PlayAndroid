package com.example.playandroid.data;

import com.example.playandroid.entity.HotWord;
import com.example.playandroid.util.network.Call;
import com.example.playandroid.util.network.Callback;
import com.example.playandroid.util.network.HttpClient;
import com.example.playandroid.util.network.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.playandroid.util.URLConstant.HOT_WORD;

public class HotWordModelImpl implements HotWordModel {
    private List<HotWord> mHotWords = new ArrayList<>();

    @Override
    public void getHotWords(final OnListener onListener) {
        Request request = new Request.Builder()
                .url(HOT_WORD)
                .build();
        HttpClient httpClient = new HttpClient();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Exception e) {
                onListener.onFailure(e);
            }

            @Override
            public void onResponse(String response) {
                try {
                    handleJson(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onListener.onSuccess(mHotWords);
            }
        });
    }

    private void handleJson(String response) throws JSONException {
        mHotWords.clear();

        JSONObject jsonObject = new JSONObject(response);
        JSONArray data = jsonObject.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject hotWordJson = data.getJSONObject(i);
            long id = hotWordJson.getLong("id");
            String name = hotWordJson.getString("name");
            HotWord hotWord = new HotWord(id,name);
            mHotWords.add(hotWord);
        }
    }
}
