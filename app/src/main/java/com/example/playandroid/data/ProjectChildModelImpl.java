package com.example.playandroid.data;

import com.example.playandroid.entity.ProjectChild;
import com.example.playandroid.util.network.Call;
import com.example.playandroid.util.network.Callback;
import com.example.playandroid.util.network.HttpClient;
import com.example.playandroid.util.network.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.playandroid.util.URLConstant.CHILD_PROJECT_URL;

public class ProjectChildModelImpl implements ProjectChildModel {
    private List<ProjectChild> mProjectChildren = new ArrayList<>();
    
    @Override
    public void getProjectChildren(final OnListener onListener,long id) {
        Request request = new Request.Builder()
                .url(String.format(CHILD_PROJECT_URL,1,id))
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
                onListener.onSuccess(mProjectChildren);
            }
        });
    }
    
    private void handleJson(String response) throws JSONException {
        mProjectChildren.clear();
        
        JSONObject jsonObject = new JSONObject(response);
        
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray datas = data.getJSONArray("datas");
        
        for(int i=0;i<datas.length();i++){
            JSONObject child = datas.getJSONObject(i);
            
            String picUrl = child.getString("envelopePic");
            String link = child.getString("link");
            String title = child.getString("title");
            String desc = child.getString("desc");
            String author = child.getString("author");
            String date = child.getString("niceDate");
            
            ProjectChild projectChild = new ProjectChild(picUrl,link,title,desc,author,date);
            mProjectChildren.add(projectChild);
        }
    }
}
