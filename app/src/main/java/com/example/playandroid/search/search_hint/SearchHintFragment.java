package com.example.playandroid.search.search_hint;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.playandroid.R;
import com.example.playandroid.entity.HotWord;
import com.example.playandroid.util.HandlerUtil;
import com.example.playandroid.util.network.LogUtil;
import com.example.playandroid.view.flowlayout.FlowLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.example.playandroid.util.Constants.SEARCH_HINT_CONSTANT.HOT_WORD_SUCCESS;

/**
 * 展示搜索热词以及搜索历史的碎片.
 */
public class SearchHintFragment extends Fragment implements SearchHintContract.OnView{
    
    private static final String TAG = "SearchHintFragment";
    private SearchHintContract.Presenter mPresenter;
    private boolean mFirstLoad = true;
    private FlowLayout mFlowLayout;
    private List<HotWord> mHotWords = new ArrayList<>();
    private View mView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search_hint, container, false);
        
        initView();
        initData();
        
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        
        if(mFirstLoad){
            mFirstLoad = false;
            mPresenter.start();
        }
    }

    private void initView(){
        mFlowLayout = mView.findViewById(R.id.flow_layout);
    }
    
    private void initData(){
        new SearchHintPresenter(this);
    }
    
    @Override
    public void onGetHotWordsSuccess(List<HotWord> hotWords) {
        mHotWords.clear();
        mHotWords.addAll(hotWords);

        HandlerUtil.post(new UIRunnable(this, HOT_WORD_SUCCESS));
    }

    @Override
    public void onGetHotWordsFailure(Exception e) {
        LogUtil.d(TAG,e.getMessage());
    }

    @Override
    public void setPresenter(SearchHintContract.Presenter presenter) {
        mPresenter = presenter;
    }
    
    private static class UIRunnable implements Runnable {

        private WeakReference<SearchHintFragment> mWeak;
        private int mType;

        public UIRunnable(SearchHintFragment fragment, int type) {
            mWeak = new WeakReference<>(fragment);
            mType = type;
        }

        @Override
        public void run() {
            switch (mType){
                case HOT_WORD_SUCCESS:
                    if (mWeak.get() != null) {
                        for (int i = 0; i < mWeak.get().mHotWords.size(); i++) {
                            HotWord hotWord = mWeak.get().mHotWords.get(i);
                            //获得流式布局的子view
                            View view = FlowLayout.createChildView(
                                    (int)mWeak.get().mFlowLayout.getItemHeight(),
                                    hotWord,R.layout.textview);
                            view.setBackgroundResource(R.color.deepGreen);
                            //设置点击监听
//                            view.setOnClickListener(mWeak.get());
                            //添加子View
                            mWeak.get().mFlowLayout.addView(view);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
