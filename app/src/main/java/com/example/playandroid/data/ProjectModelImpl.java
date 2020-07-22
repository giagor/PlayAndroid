package com.example.playandroid.data;

import com.example.playandroid.entity.Project;
import com.example.playandroid.util.network.Call;
import com.example.playandroid.util.network.Callback;
import com.example.playandroid.util.network.HttpClient;
import com.example.playandroid.util.network.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.playandroid.util.URLConstant.PROJECT_URL;

public class ProjectModelImpl implements ProjectModel{
    private List<Project> mProjects = new ArrayList<>();

    @Override
    public void getProjects(final OnListener onListener) {
        Request request = new Request.Builder()
                .url(PROJECT_URL)
                .build();
        HttpClient httpClient = new HttpClient();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Exception e) {
                
            }

            @Override
            public void onResponse(String response) {
                try {
                    handleJson(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onListener.onGetProjectSuccess(mProjects);
            }
        });
    }
    
    private void handleJson(String response) throws JSONException {
        mProjects.clear();
        
        JSONObject jsonObject = new JSONObject(response);

        JSONArray data = jsonObject.getJSONArray("data");
        for(int i = 0;i < data.length();i++){
            JSONObject projectJson = data.getJSONObject(i);
            
            long id = projectJson.getLong("id");
            String name = projectJson.getString("name");
            
            Project project = new Project(id,name);
            mProjects.add(project);
        }
    }
}
