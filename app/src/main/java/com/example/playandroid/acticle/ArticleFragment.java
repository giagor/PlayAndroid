package com.example.playandroid.acticle;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.playandroid.R;
import com.example.playandroid.adapter.ArticleAdapter;
import com.example.playandroid.dao.DatabaseHelper;
import com.example.playandroid.entity.Article;
import com.example.playandroid.main.MainActivity;
import com.example.playandroid.util.HandlerUtil;
import com.example.playandroid.util.network.LogUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuWrapperICS;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.example.playandroid.util.Constants.ArticleConstant.GET_ARTICLES_FAILURE;
import static com.example.playandroid.util.Constants.ArticleConstant.GET_ARTICLES_SUCCESS;
import static com.example.playandroid.util.Constants.ArticleConstant.REFRESH_FAILURE;
import static com.example.playandroid.util.Constants.ArticleConstant.REFRESH_SUCCESS;
import static com.example.playandroid.util.Constants.ArticleConstant.LOAD_MORE_SUCCESS;
import static com.example.playandroid.util.Constants.DatabaseConstant.ARTICLE_DB_NAME;
import static com.example.playandroid.util.Constants.DatabaseConstant.CURRENT_VERSION;

public class ArticleFragment extends Fragment implements ArticleContract.OnView,
        ArticleAdapter.OnItemClickListener {
    private static final String TAG = "ArticleFragment";

    private ArticleContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private View mView;
    private List<Article> mArticles = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefresh;
    private ArticleAdapter mAdapter;
    private DatabaseHelper mHelper;

    /**
     * 对数据库进行CRUD.
     */
    private SQLiteDatabase mDatabase;

    /**
     * 标记是否第一次请求数据.
     */
    private boolean mFirstLoad = true;


    /**
     * 标记是否下拉刷新.
     */
    private boolean mRefresh = false;

    /**
     * 标记当前的Page,初始时为0
     */
    private int mCurPage = 0;

    /**
     * 标记"加载更多"是否已经结束，防止重复加载.
     */
    private boolean mLoadFinish = true;

    /**
     * 记录文章的总页数.
     */
    private int mPageCount;

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

        //获取数据库的帮助类
        mHelper = new DatabaseHelper(getContext(), ARTICLE_DB_NAME, null,
                CURRENT_VERSION);
        mDatabase = mHelper.getWritableDatabase();

        //从数据库中获取已经缓存好的列表数据
        mPresenter.getAllArticles(mDatabase);

        //为RecyclerView设置数据
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new ArticleAdapter(mArticles);
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefresh.setColorSchemeResources(R.color.green);
    }

    private void initEvent() {
        //下拉刷新的监听.
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefresh = true;
                mPresenter.refreshArticles(0);
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
                        if (mCurPage >= mPageCount) {
                            Toast.makeText(getContext(), "已全部加载完毕", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //如果之前的加载更多已结束
                        if (mLoadFinish) {
                            setFooterView(mRecyclerView);
                            mPresenter.getArticles(mCurPage);
                        }
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

    private void removeFooterView() {
        mAdapter.removeFooterView();
    }

    @Override
    public void onGetArticlesSuccess(int pageCount, List<Article> articles) {
        mArticles.clear();
        mArticles.addAll(articles);
        mPageCount = pageCount;

        HandlerUtil.post(new UIRunnable(this, GET_ARTICLES_SUCCESS));
    }

    @Override
    public void onGetArticlesFailure(Exception e) {
        LogUtil.d(TAG, "onLoadMoreFailure: " + e.getMessage());
        HandlerUtil.post(new UIRunnable(this, GET_ARTICLES_FAILURE));
    }

    @Override
    public void onLoadMoreSuccess(List<Article> articles) {
        mArticles.addAll(articles);

        HandlerUtil.post(new UIRunnable(this, LOAD_MORE_SUCCESS));
    }

    @Override
    public void onLoadMoreFailure(Exception e) {
    }

    @Override
    public void onRefreshSuccess(int pageCount, List<Article> articles) {
        mArticles.clear();
        mArticles.addAll(articles);
        mPageCount = pageCount;
        
        HandlerUtil.post(new UIRunnable(this,REFRESH_SUCCESS));
    }

    @Override
    public void onRefreshFailure(Exception e) {
        HandlerUtil.post(new UIRunnable(this,REFRESH_FAILURE));
    }

    /**
     * 从dao获取数据成功.
     */
    @Override
    public void getArticlesFromDaoSuccess(List<Article> articles) {
        //因为数据库读取顺序的原因，这里要做个倒序操作.
        Collections.reverse(articles);
        mArticles.addAll(articles);
    }

    @Override
    public void onClick(Article article) {
        if (getContext() != null) {
            ((MainActivity) getContext()).showArticleDetail(article.getTitle(), article.getLink());
        }
    }

    @Override
    public void onDestroy() {
        mHelper.close();
        super.onDestroy();
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
                case GET_ARTICLES_SUCCESS:
                    //第一次加载数据
                    if (mWeak.get() != null) {
                        //标记已经从网络中加载过一次数据
                        mWeak.get().mFirstLoad = false;
                        mWeak.get().mAdapter.notifyDataSetChanged();
                        mWeak.get().mCurPage++;
                        
                        //操作数据库，先删除表中的数据
                        mWeak.get().mPresenter.deleteAllArticles(mWeak.get().mDatabase);
                        //再放入新的缓存好的数据
                        mWeak.get().mPresenter.insertArticles(mWeak.get().mDatabase,
                                mWeak.get().mArticles);
                    }
                    break;
                case GET_ARTICLES_FAILURE:
//                    if(mWeak.get() != null){
//                        //若进入页面时加载失败，则标记上
//                        mWeak.get().mFirstLoad = false;
//                    }
                    break;
                case LOAD_MORE_SUCCESS:
                    if (mWeak.get() != null) {
                        mWeak.get().mAdapter.notifyDataSetChanged();
                        mWeak.get().mCurPage++;

                        //加载更多已结束
                        mWeak.get().mLoadFinish = true;

                        //移除FooterView
                        mWeak.get().removeFooterView();

                    }
                case REFRESH_SUCCESS:
                    if (mWeak.get() != null) {
                        mWeak.get().mAdapter.notifyDataSetChanged();

                        mWeak.get().mCurPage = 1;
                        mWeak.get().mRefresh = false;
                        //关闭刷新圈圈
                        mWeak.get().mSwipeRefresh.setRefreshing(false);
                    }
                    break;
                case REFRESH_FAILURE:
                    if (mWeak.get() != null) {
                        if (mWeak.get().mRefresh) {
                            mWeak.get().mRefresh = false;
                            mWeak.get().mSwipeRefresh.setRefreshing(false);
                            Toast.makeText(mWeak.get().getContext(), "刷新失败",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                default:
                    break;
            }
        }
    }
}
