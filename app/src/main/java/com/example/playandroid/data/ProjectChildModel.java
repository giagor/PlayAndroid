package com.example.playandroid.data;

import com.example.playandroid.entity.ProjectChild;

import java.util.List;

public interface ProjectChildModel {
    void getProjectChildren(OnListener onListener,int id);
    
    interface OnListener{
        void onSuccess(List<ProjectChild>projectChildren);
        void onFailure(Exception e);
    }
}
