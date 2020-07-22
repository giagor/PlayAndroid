package com.example.playandroid.frame;

import com.example.playandroid.base.BasePresenter;
import com.example.playandroid.base.BaseView;
import com.example.playandroid.entity.Frame;

import java.util.List;

public interface FrameContract {
    interface Presenter extends BasePresenter {
        void getFrames();
    }
    
    interface OnView extends BaseView<Presenter> {
        void onGetFramesSuccess(List<Frame> frames);
        void onGetFramesFailure(Exception e);
    }
}
