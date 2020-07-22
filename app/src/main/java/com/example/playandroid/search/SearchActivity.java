package com.example.playandroid.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.playandroid.R;
import com.example.playandroid.acticle.ArticleDetailActivity;
import com.example.playandroid.adapter.ArticleAdapter;
import com.example.playandroid.entity.Article;
import com.example.playandroid.entity.HotWord;
import com.example.playandroid.util.HandlerUtil;
import com.example.playandroid.view.flowlayout.TagModel;
import com.example.playandroid.view.flowlayout.RadioFlowLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static com.example.playandroid.util.Constants.SearchConstant.HOT_WORD_SUCCESS;
import static com.example.playandroid.util.Constants.SearchConstant.SEARCH_SUCCESS;

public class SearchActivity extends AppCompatActivity implements SearchContract.OnView,
        ArticleAdapter.OnItemClickListener, View.OnClickListener {
    private static final String TAG = "SearchActivity";
    private Toolbar mToolbar;
    private RadioFlowLayout mRadioFlowLayout;
    private SearchContract.Presenter mPresenter;
    private List<HotWord> mHotWords = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private SearchView mSearchView;

    /**
     * 展示搜索内容的适配器.
     */
    private ArticleAdapter mAdapter;

    /**
     * 搜索热词的负父布局.
     */
    private LinearLayout mSearchHintLayout;

    /**
     * 标志是不是第一次加载数据.
     */
    private boolean mFirstLoad = true;

    /**
     * 搜索得到的文章列表.
     */
    private List<Article> mArticles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        setActionBar();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mFirstLoad) {
            mPresenter.start();
            mFirstLoad = false;
        }
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mRadioFlowLayout = findViewById(R.id.radioFlowLayout);
        mSearchHintLayout = findViewById(R.id.search_hint_layout);
        mRecyclerView = findViewById(R.id.search_content);
    }
    
    private void initData() {
        new SearchPresenter(this);

        //为RecyclerView设置数据
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new ArticleAdapter(mArticles);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setListener(this);
    }

    /**
     * 设置顶部标题栏的信息.
     */
    private void setActionBar() {
        setSupportActionBar(mToolbar);
        //ToolBar左侧显示出返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 启动该活动.
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    /**
     * 用户点击标题栏的"返回键"时，销毁"搜索"所在的活动.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    /**
     * 加载toolbar的menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_search, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        if (menuItem != null) {
            final SearchView searchView = (SearchView) menuItem.getActionView();
            mSearchView = searchView;
            searchView.setIconifiedByDefault(false);

            //为SearchView设置监听
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                //当点击搜索按钮时，回调该方法.
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //搜索文章
                    mPresenter.searchContents(query);
                    //提交后失去焦点，即收起软键盘
                    searchView.clearFocus();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return true;
    }

    /**
     * 成功拿到搜索热词.
     */
    @Override
    public void onGetHotWordsSuccess(List<HotWord> hotWords) {
        mHotWords.clear();
        mHotWords.addAll(hotWords);

        HandlerUtil.post(new UIRunnable(this, HOT_WORD_SUCCESS));
    }

    /**
     * 没有拿到搜索热词.
     */
    @Override
    public void onGetHotWordsFailure(Exception e) {
    }

    /**
     * 搜索成功.
     */
    @Override
    public void onSearchContentSuccess(List<Article> articles) {
        mArticles.clear();
        mArticles.addAll(articles);

        HandlerUtil.post(new UIRunnable(this, SEARCH_SUCCESS));
    }

    /**
     * 搜索失败.
     */
    @Override
    public void onSearchContentFailure(Exception e) {
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 当搜索内容被点击时，回调该方法打开文章详情界面.
     */
    @Override
    public void onClick(Article article) {
        ArticleDetailActivity.actionStart(this, article.getTitle(), article.getLink());
    }

    /**
     * View(热词)被点击后回调.
     */
    @Override
    public void onClick(View v) {
        String keyword = ((HotWord)(((TagModel)(v.getTag())).getT())).getName();
        mPresenter.searchContents(keyword);
    }

    /**
     * 创建子view
     */
    private <T extends TextView> View createChildView(HotWord hotWord, int layoutId) {
        //为子view加载布局
        LayoutInflater inflater = LayoutInflater.from(this);
        T view = (T) inflater.inflate(layoutId, null);
        //设置RadioButton的参数
        RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.height = (int) mRadioFlowLayout.getItemHeight();
        view.setLayoutParams(lp);
        //添加view的时候，将HotWord信息setTag()
        TagModel<HotWord> tagModel = new TagModel<>();
        tagModel.setT(hotWord);
        view.setTag(tagModel);
        view.setText(hotWord.getName());
        //设置点击监听
        view.setOnClickListener(this);
        return view;
    }

    private static class UIRunnable implements Runnable {

        WeakReference<SearchActivity> mWeak;
        int mType;

        UIRunnable(SearchActivity activity, int type) {
            mWeak = new WeakReference<>(activity);
            mType = type;
        }

        @Override
        public void run() {
            switch (mType) {
                case HOT_WORD_SUCCESS:
                    if (mWeak.get() != null) {
                        for (int i = 0; i < mWeak.get().mHotWords.size(); i++) {
                            HotWord hotWord = mWeak.get().mHotWords.get(i);
                            mWeak.get().mRadioFlowLayout.addView(mWeak.get().createChildView(
                                    hotWord, R.layout.radiobutton));
                        }
                    }
                    break;
                case SEARCH_SUCCESS:
                    if (mWeak.get() != null) {
                        //将搜索热词的内容隐藏
                        mWeak.get().mSearchHintLayout.setVisibility(GONE);
                        //展示搜索内容
                        mWeak.get().mRecyclerView.setVisibility(View.VISIBLE);
                        mWeak.get().mAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}



    
