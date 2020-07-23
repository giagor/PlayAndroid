package com.example.playandroid.acticle;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.playandroid.R;
import com.example.playandroid.adapter.ArticleAdapter;
import com.example.playandroid.entity.Article;
import com.example.playandroid.main.MainActivity;
import com.example.playandroid.util.HandlerUtil;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.example.playandroid.util.Constants.ArticleConstant.SUCCESS;

public class ArticleFragment extends Fragment implements ArticleContract.OnView,
        ArticleAdapter.OnItemClickListener {
    private static final String TAG = "ArticleFragment";

    private ArticleContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private View mView;
    private List<Article> mArticles = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefresh;
    private ArticleAdapter mAdapter;

    /**
     * 标记是否第一次请求数据.
     */
    private boolean mFirstLoad = true;


    /**
     * 标记是否下拉刷新.
     */
    private boolean mRefresh = false;
    
    /**
     * 标记当前的Page.
     * */
    private int mCurPage = 0;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_article, container, false);

        initView();
        initData();
        initEvent();


        return mView;
    }

    private void initView() {
        mRecyclerView = mView.findViewById(R.id.recycler_view);
        mSwipeRefresh = mView.findViewById(R.id.swipe_refresh);
    }

    private void initData() {
        new ArticlePresenter(this);
        
        //为RecyclerView设置数据
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new ArticleAdapter(mArticles);
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefresh.setColorSchemeResources(R.color.green);
    }

    private void initEvent() {
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefresh = true;
                mPresenter.getArticles(0);
            }
        });

        //为RecyclerView设置滚动监听
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * 滚动状态改变时回调该方法.
             * */
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //当滚动之后，停止滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE && manager != null) {
                    //得到最后一个完全可见的item的下标
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    if (lastVisibleItem == totalItemCount - 1) {
                        
                    }
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null && mFirstLoad) {
            mPresenter.getArticles(mCurPage);
            mFirstLoad = false;
        }
    }

    @Override
    public void setPresenter(ArticleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 为RecyclerView添加Footer.
     */
    private void setFooterView(RecyclerView recyclerView) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.footer_view, recyclerView,
                false);
        mAdapter.setFooterView(view);
    }

    @Override
    public void onSuccess(List<Article> articles) {
        mArticles.clear();
        mArticles.addAll(articles);

        HandlerUtil.post(new UIRunnable(this, SUCCESS));
    }

    @Override
    public void onFail(Exception e) {

    }

    @Override
    public void onLoadMoreSuccess(List<Article> articles) {
        
    }

    @Override
    public void onLoadMoreFailure(Exception e) {

    }

    @Override
    public void onClick(Article article) {
        if (getContext() != null) {
            ((MainActivity) getContext()).showArticleDetail(article.getTitle(), article.getLink());
        }
    }
    
    private static class UIRunnable implements Runnable {

        private WeakReference<ArticleFragment> mWeak;
        private int mType;

        UIRunnable(ArticleFragment fragment, int type) {
            mWeak = new WeakReference<>(fragment);
            mType = type;
        }

        @Override
        public void run() {
            switch (mType) {
                case SUCCESS:
                    if (mWeak.get() != null) {
                        mWeak.get().mAdapter.notifyDataSetChanged();
                        mWeak.get().mCurPage++;
                        
                        //如果是下拉刷新
                        if (mWeak.get().mRefresh) {
                            mWeak.get().mCurPage = 0;
                            mWeak.get().mRefresh = false;
                            //关闭刷新圈圈
                            mWeak.get().mSwipeRefresh.setRefreshing(false);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
