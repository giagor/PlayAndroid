package com.example.playandroid.data;

import com.example.playandroid.entity.Frame;
import com.example.playandroid.entity.FrameChild;
import com.example.playandroid.util.network.Call;
import com.example.playandroid.util.network.Callback;
import com.example.playandroid.util.network.HttpClient;
import com.example.playandroid.util.network.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.playandroid.util.URLConstant.FRAME;

public class FrameModelImpl implements FrameModel {
    private List<Frame> mFrames = new ArrayList<>();

    @Override
    public void getFrames(final OnListener onListener) {
        Request request = new Request.Builder()
                .url(FRAME)
                .build();
        HttpClient client = new HttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Exception e) {
                onListener.onGetFrameFailure(e);
            }

            @Override
            public void onResponse(String response) {
                try {
                    handleJson(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onListener.onGetFrameSuccess(mFrames);
            }
        });
    }

    private void handleJson(String response) throws JSONException {
        mFrames.clear();

        JSONObject jsonObject = new JSONObject(response);
        JSONArray data = jsonObject.getJSONArray("data");

        for (int i = 0; i < data.length(); i++) {
            List<FrameChild> frameChildren = new ArrayList<>();
            JSONObject dataChild = data.getJSONObject(i);
            long id = dataChild.getLong("id");
            String frameName = dataChild.getString("name");
            JSONArray children = dataChild.getJSONArray("children");
            for (int j = 0; j < children.length(); j++) {
                JSONObject frameChildJson = children.getJSONObject(j);
                long childId = frameChildJson.getLong("id");
                String name = frameChildJson.getString("name");
                frameChildren.add(new FrameChild(childId, name));
            }
            Frame frame = new Frame(id, frameName, frameChildren);
            mFrames.add(frame);
        }
    }

}
