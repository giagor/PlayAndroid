package com.example.playandroid.frame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.playandroid.R;
import com.example.playandroid.entity.Frame;
import com.example.playandroid.entity.FrameChild;

import java.util.List;

public class FrameFragment extends Fragment implements FrameContract.OnView {

    private static final String TAG = "FrameFragment";
    private FrameContract.Presenter mPresenter;

    /**
     * 标记是否是第一次加载.
     */
    private boolean mFirstLoad = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initView();
        initData();

        return inflater.inflate(R.layout.fragment_frame, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        //进入页面时，进行数据的加载.
        if (mFirstLoad) {
            if (mPresenter != null) {
                mPresenter.start();
            }
            mFirstLoad = false;
        }
    }

    private void initView() {

    }

    private void initData() {
        new FramePresenter(this);
    }

    /**
     * 成功拿到知识体系的内容.
     */
    @Override
    public void onGetFramesSuccess(List<Frame> frames) {
    }

    /**
     * 没有拿到知识体系的内容.
     * */
    @Override
    public void onGetFramesFailure(Exception e) {
        Log.d(TAG, "onGetFramesFailure: "+e.getMessage());
    }

    @Override
    public void setPresenter(FrameContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
