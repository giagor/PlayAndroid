package com.example.playandroid.project.project_child;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.playandroid.R;
import com.example.playandroid.adapter.ProjectChildAdapter;
import com.example.playandroid.entity.Project;
import com.example.playandroid.entity.ProjectChild;
import com.example.playandroid.util.HandlerUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.example.playandroid.util.Constants.ProjectChildConstant.SUCCESS;

/**
 * "项目"碎片模块嵌套的ViewPager的Pager(即这里的碎片)
 */
public class ProjectChildFragment extends Fragment implements ProjectChildContract.OnView {
    private View mView;
    private Project mProject;
    private ProjectChildContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private List<ProjectChild> mProjectChildren = new ArrayList<>();

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
        
        mRecyclerView = mView.findViewById(R.id.recycler_view);
    }

    public void setProject(Project project) {
        mProject = project;
    }

    @Override
    public void onSuccess(List<ProjectChild> projectChildren) {
        mProjectChildren.clear();
        mProjectChildren.addAll(projectChildren);

        HandlerUtil.post(new UIRunnable(this,SUCCESS));
    }

    @Override
    public void onFail(Exception e) {

    }

    @Override
    public void setPresenter(ProjectChildContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private static class UIRunnable implements Runnable{

        private WeakReference<ProjectChildFragment> mWeak;
        private int mType;

        UIRunnable(ProjectChildFragment fragment, int type) {
            mWeak = new WeakReference<>(fragment);
            mType = type;
        }

        @Override
        public void run() {
            switch(mType){
                case SUCCESS:
                    if(mWeak.get() != null){
                        RecyclerView.LayoutManager manager = new LinearLayoutManager(mWeak.get().getContext());
                        mWeak.get().mRecyclerView.setLayoutManager(manager);
                        ProjectChildAdapter adapter = new ProjectChildAdapter(mWeak.get().mProjectChildren);
                        mWeak.get().mRecyclerView.setAdapter(adapter);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
