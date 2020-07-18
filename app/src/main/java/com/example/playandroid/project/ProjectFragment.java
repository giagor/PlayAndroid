package com.example.playandroid.project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.playandroid.R;
import com.example.playandroid.entity.Project;
import com.example.playandroid.util.UIHandler;

import java.util.List;

/**
 * 项目界面.
 */
public class ProjectFragment extends Fragment implements ProjectContract.OnView,
        UIHandler.HandlerListener{
    private View mView;
    private ProjectContract.Presenter mPresenter;
    private Handler mHandler;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_project, container, false);
        
        initData();
        
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getProjects();
    }

    private void initData(){
        new ProjectPresenter(this);

        mHandler = new UIHandler(this);
    }
    
    @Override
    public void onSuccess(List<Project> projects) {
        
    }

    @Override
    public void onFail(Exception e) {

    }

    @Override
    public void setPresenter(ProjectContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void handlerMessage(@NonNull Message msg) {
        
    }
}
