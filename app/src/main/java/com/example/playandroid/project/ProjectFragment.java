package com.example.playandroid.project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.playandroid.R;
import com.example.playandroid.entity.Project;
import com.example.playandroid.project.project_child.ProjectChildFragment;
import com.example.playandroid.util.UIHandler;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;
import static com.example.playandroid.util.Constants.ProjectConstant.SUCCESS;

/**
 * 项目界面.
 */
public class ProjectFragment extends Fragment implements ProjectContract.OnView,
        UIHandler.HandlerListener{
    private View mView;
    private ProjectContract.Presenter mPresenter;
    private Handler mHandler;

    /**
     * ViewPager的各个pager.
     */
    private List<Fragment> mFragments = new ArrayList<>();

    /**
     * ViewPager的各个pager的标题
     */
    private List<String> mTitles = new ArrayList<>();

    /**
     * 存储网络请求得到的Project(一级)
     */
    private List<Project> mProjects = new ArrayList<>();

    private ViewPager mViewPager;

    private TabLayout mTabLayout;
    
    /**
     * 标记是不是第一次加载.
     * */
    private boolean mFirstLoad = true;

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
        
        if(mFirstLoad){
            mPresenter.start();
            mFirstLoad = false;
        }
    }

    private void initData() {
        new ProjectPresenter(this);

        mHandler = new UIHandler(this);

        mViewPager = mView.findViewById(R.id.view_pager);
        mTabLayout = mView.findViewById(R.id.tab_layout);
    }

    @Override
    public void onSuccess(List<Project> projects) {
        mProjects.clear();
        mProjects.addAll(projects);

        Message message = Message.obtain();
        message.what = SUCCESS;
        mHandler.sendMessage(message);
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
        if (msg.what == SUCCESS) {
            //创建ViewPager的各个Pager的碎片和Title
            for (int i = 0; i < mProjects.size(); i++) {
                ProjectChildFragment fragment = new ProjectChildFragment();
                fragment.setProject(mProjects.get(i));
                mFragments.add(fragment);
                mTitles.add(mProjects.get(i).getName());
            }
            mViewPager.setOffscreenPageLimit(3);
            mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager(),
                    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
                @NonNull
                @Override
                public Fragment getItem(int position) {
                    return mFragments.get(position);
                }

                @Override
                public int getCount() {
                    return mFragments.size();
                }

                @Nullable
                @Override
                public CharSequence getPageTitle(int position) {
                    return mTitles.get(position);
                }
            });
            
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }
    
}

