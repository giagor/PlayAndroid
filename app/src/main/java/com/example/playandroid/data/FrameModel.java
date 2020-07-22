package com.example.playandroid.data;

import com.example.playandroid.entity.Frame;

import java.util.List;

/**
 * 获得知识体系(1级)的数据.
 * */
public interface FrameModel {
    void getFrames(OnListener onListener);
    
    interface OnListener{
       void onGetFrameSuccess(List<Frame> frames);
       void onGetFrameFailure(Exception e);
    }
}
