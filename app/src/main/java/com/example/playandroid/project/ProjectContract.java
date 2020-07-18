package com.example.playandroid.project;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.base.BaseView;
import com.example.playandroid.entity.Project;

import java.util.List;

public interface ProjectContract {
    interface Presenter extends BasePresenter{
        void getProjects();
    }
    
    interface OnView extends BaseView<Presenter>{
        void onSuccess(List<Project>projects);
        void onFail(Exception e);
    }
}
