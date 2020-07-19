package com.example.playandroid.project.project_child;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.playandroid.R;
import com.example.playandroid.entity.Project;
import com.example.playandroid.entity.ProjectChild;

import java.util.List;

/**
 * "项目"碎片模块嵌套的ViewPager的Pager(即这里的碎片)
 */
public class ProjectChildFragment extends Fragment implements ProjectChildContract.OnView {
    private View mView;
    private Project mProject;
    private ProjectChildContract.Presenter mPresenter;

    /**
     * 标记是否已经请求过了数据，是否是第一次加载.
     */
    private boolean mFirstLoad = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_project_pager, container, false);

        initData();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mFirstLoad) {
            mPresenter.getProjectChildren(mProject.getId());
            mFirstLoad = false;
        }

    }

    private void initData() {
        new ProjectChildPresenter(this);
    }

    public void setProject(Project project) {
        mProject = project;
    }

    @Override
    public void onSuccess(List<ProjectChild> projectChildren) {

    }

    @Override
    public void onFail(Exception e) {

    }

    @Override
    public void setPresenter(ProjectChildContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
