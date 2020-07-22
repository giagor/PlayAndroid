package com.example.playandroid.data;

import com.example.playandroid.entity.HotWord;

import java.util.List;

/**
 * 获得搜索热词.
 * */
public interface HotWordModel {
    void getHotWords(OnListener onListener);
    
    interface OnListener {
        void onSuccess(List<HotWord> hotWords);
        void onFailure(Exception e);
    }
}
