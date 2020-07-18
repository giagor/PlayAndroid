package com.example.playandroid.project;

import com.example.playandroid.data.ProjectModel;
import com.example.playandroid.data.ProjectModelImpl;
import com.example.playandroid.entity.Project;

import java.util.List;

public class ProjectPresenter implements ProjectContract.Presenter, ProjectModel.OnListener {
    
    private ProjectModel mProjectModel;
    private ProjectContract.OnView mProjectView;
    
    public ProjectPresenter(ProjectContract.OnView view) {
        mProjectView = view;
        view.setPresenter(this);
        mProjectModel = new ProjectModelImpl();
    }

    @Override
    public void getProjects() {
        mProjectModel.getProjects(this);
    }

    @Override
    public void start() {
        getProjects();
    }

    @Override
    public void onSuccess(List<Project> projects) {
        mProjectView.onSuccess(projects);
    }

    @Override
    public void onFail(Exception e) {
        mProjectView.onFail(e);
    }
}
