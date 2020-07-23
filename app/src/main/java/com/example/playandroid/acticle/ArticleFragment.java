package com.example.playandroid.acticle;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private ArticleContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private View mView;
    //    private Handler mHandler;
    private List<Article> mArticles = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefresh;
    private ArticleAdapter mAdapter;

    /**
     * 标记是否第一次请求数据.
     */
    private boolean mFirstLoad = true;


    /**
     * 标记是否下拉刷新.
     * */
    private boolean mRefresh = false;
    /**
     * 碎片和活动通信的接口引用.
     */
//    private OnArticleListener mCallback;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        mCallback = (OnArticleListener) context;
    }

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

//        mHandler = new UIHandler(this);

        //为RecyclerView设置数据
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
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
                mPresenter.getArticles();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null && mFirstLoad) {
            mPresenter.start();
            mFirstLoad = false;
        }
    }

    @Override
    public void setPresenter(ArticleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 为RecyclerView添加Footer.
     * */
    private void setFooterView(RecyclerView recyclerView){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.footer_view,recyclerView,
                false);
        mAdapter.setFooterView(view);
    }
    
    @Override
    public void onSuccess(List<Article> articles) {
        mArticles.clear();
        mArticles.addAll(articles);
//        Message msg = Message.obtain();
//        msg.what = SUCCESS;
//        mHandler.sendMessage(msg);
        HandlerUtil.post(new UIRunnable(this, SUCCESS));
    }

    @Override
    public void onFail(Exception e) {

    }

//    @Override
//    public void handlerMessage(@NonNull Message msg) {
//        switch (msg.what){
//            case SUCCESS:
//                RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
//                mRecyclerView.setLayoutManager(manager);
//                ArticleAdapter adapter = new ArticleAdapter(mArticles);
//                adapter.setListener(this);
//                mRecyclerView.setAdapter(adapter);
//                break;
//            default:
//                break;
//        }
//    }

    @Override
    public void onClick(Article article) {
        if (getContext() != null) {
            ((MainActivity) getContext()).showArticleDetail(article.getTitle(), article.getLink());
        }
    }

//    /**
//     * 回调接口，主活动实现，用于打开文章的主界面.
//     * */
//    public interface OnArticleListener{
//        void showArticleDetail(String title,String url);
//    }

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
                    
                        //如果是下拉刷新
                        if(mWeak.get().mRefresh){
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
