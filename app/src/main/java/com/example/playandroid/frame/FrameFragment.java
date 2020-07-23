package com.example.playandroid.frame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.playandroid.R;
import com.example.playandroid.adapter.FrameAdapter;
import com.example.playandroid.entity.Frame;
import com.example.playandroid.entity.FrameChild;
import com.example.playandroid.frame.frame_child.FrameChildActivity;
import com.example.playandroid.util.HandlerUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.example.playandroid.util.Constants.FrameConstant.FRAME_SUCCESS;

public class FrameFragment extends Fragment implements FrameContract.OnView,
        FrameAdapter.OnItemClickListener {

    private static final String TAG = "FrameFragment";
    private FrameContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private View mView;
    private FrameAdapter mAdapter;
    private List<Frame> mFrames = new ArrayList<>();

    /**
     * 标记是否是第一次加载.
     */
    private boolean mFirstLoad = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_frame, container, false);
        
        initView();
        initData();

        return mView;
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
        mRecyclerView = mView.findViewById(R.id.recycler_view);
    }

    private void initData() {
        new FramePresenter(this);
        
        //为RecyclerView设置布局方式和适配器
        mAdapter = new FrameAdapter(mFrames);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mAdapter.setListener(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 成功拿到知识体系的内容.
     */
    @Override
    public void onGetFramesSuccess(List<Frame> frames) {
        mFrames.clear();
        mFrames.addAll(frames);
        
        HandlerUtil.post(new UIRunnable(FRAME_SUCCESS,this));
    }

    /**
     * 没有拿到知识体系的内容.
     * */
    @Override
    public void onGetFramesFailure(Exception e) {
        
    }

    @Override
    public void setPresenter(FrameContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(Frame frame) {
        if(getContext() != null){
            FrameChildActivity.actionStart(getContext(),frame);
        }
    }

    static class UIRunnable implements Runnable{

        private int mType;
        private WeakReference<FrameFragment> mWeak;

        public UIRunnable(int type, FrameFragment fragment) {
            mType = type;
            mWeak = new WeakReference<>(fragment);
        }

        @Override
        public void run() {
            switch (mType){
                case FRAME_SUCCESS:
                    if(mWeak.get() != null){
                        //通知知识体系(一级)数据页面的改变
                        mWeak.get().mAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
        }
    } 
}
