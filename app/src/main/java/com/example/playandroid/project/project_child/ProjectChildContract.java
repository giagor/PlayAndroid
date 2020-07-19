package com.example.playandroid.project.project_child;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.base.BaseView;
import com.example.playandroid.entity.ProjectChild;

import java.util.List;

public interface ProjectChildContract {
    interface Presenter extends BasePresenter {
        void getProjectChildren(long id);
    }
    
    interface OnView extends BaseView<Presenter> {
        void onSuccess(List<ProjectChild> projectChildren);
        void onFail(Exception e);
    }
    
}
