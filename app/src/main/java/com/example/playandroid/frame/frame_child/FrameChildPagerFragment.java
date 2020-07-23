package com.example.playandroid.frame.frame_child;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.playandroid.R;
import com.example.playandroid.adapter.ArticleAdapter;
import com.example.playandroid.entity.Article;
import com.example.playandroid.entity.FrameChild;
import com.example.playandroid.util.HandlerUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.playandroid.util.Constants.FrameChildConstant.SUCCESS;

/**
 * 子体系的ViewPager的碎片.
 */
public class FrameChildPagerFragment extends Fragment implements FrameChildPagerContract.OnView {

    private View mView;
    private FrameChild mFrameChild;
    private FrameChildPagerContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private ArticleAdapter mAdapter;
    private List<Article> mArticles = new ArrayList<>();
    
    /**
     * 标记是否是第一次加载.
     */
    private boolean mFirstLoad = true;
    

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_frame_child_pager,container,false);
        
        initView();
        initData();
        
        return mView;
    }

    private void initView(){
        mRecyclerView = mView.findViewById(R.id.recycler_view);
    }
    
    private void initData(){
        new FrameChildPagerPresenter(this);
        
        //为recyclerView设置适配器以及布局
        mAdapter = new ArticleAdapter(mArticles);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(manager);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        
        if(mFirstLoad){
            mPresenter.getFrameArticles(mFrameChild.getId());
            mFirstLoad = false;
        }
    }

    void setFrameChild(FrameChild frameChild) {
        mFrameChild = frameChild;
    }

    @Override
    public void onGetFrameArticlesSuccess(List<Article> articles) {
        mArticles.clear();
        mArticles.addAll(articles);

        HandlerUtil.post(new UIRunnable(SUCCESS,this));
    }

    @Override
    public void onGetFrameArticlesFailure(Exception e) {

    }

    @Override
    public void setPresenter(FrameChildPagerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private static class UIRunnable implements Runnable {

        private int mType;
        private WeakReference<FrameChildPagerFragment> mWeak;

        public UIRunnable(int type, FrameChildPagerFragment fragment) {
            mType = type;
            mWeak = new WeakReference<>(fragment);
        }

        @Override
        public void run() {
            switch (mType){
                case SUCCESS:
                    if(mWeak.get() != null){
                        mWeak.get().mAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
                    
            }
        }
    }
}
