package com.example.playandroid.frame;

import android.util.Log;

import com.example.playandroid.data.FrameModel;
import com.example.playandroid.data.FrameModelImpl;
import com.example.playandroid.entity.Frame;

import java.util.List;

public class FramePresenter implements FrameContract.Presenter,FrameModel.OnListener {
    
    private FrameContract.OnView mView;
    private FrameModel mModel;

    public FramePresenter(FrameContract.OnView view) {
        mView = view;
        mView.setPresenter(this);
        mModel = new FrameModelImpl();
    }

    @Override
    public void getFrames() {
        mModel.getFrames(this);
    }

    @Override
    public void start() {
        getFrames();
    }

    @Override
    public void onGetFrameSuccess(List<Frame> frames) {
        mView.onGetFramesSuccess(frames);
    }

    @Override
    public void onGetFrameFailure(Exception e) {
        mView.onGetFramesFailure(e);
    }
}
