package com.example.playandroid.project.project_child;

import com.example.playandroid.data.ProjectChildModel;
import com.example.playandroid.data.ProjectChildModelImpl;
import com.example.playandroid.entity.ProjectChild;

import java.util.List;

public class ProjectChildPresenter implements ProjectChildContract.Presenter,ProjectChildModel.OnListener {
    private ProjectChildContract.OnView mView;
    private ProjectChildModel mModel;

    public ProjectChildPresenter(ProjectChildContract.OnView mView) {
        this.mView = mView;
        mView.setPresenter(this);
        mModel = new ProjectChildModelImpl();
    }

    @Override
    public void getProjectChildren(long id) {
        mModel.getProjectChildren(this,id);
    }

    @Override
    public void start() {
        
    }

    @Override
    public void onSuccess(List<ProjectChild> projectChildren) {
        mView.onSuccess(projectChildren);
    }

    @Override
    public void onFailure(Exception e) {
        mView.onFail(e);
    }
}
