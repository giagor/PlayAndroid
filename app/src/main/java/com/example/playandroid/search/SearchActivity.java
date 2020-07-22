package com.example.playandroid.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.playandroid.R;
import com.example.playandroid.entity.HotWord;
import com.example.playandroid.util.HandlerUtil;
import com.example.playandroid.view.flowlayout.TagModel;
import com.example.playandroid.view.flowlayout.RadioFlowLayout;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import static com.example.playandroid.util.Constants.SearchConstant.HOT_WORD_SUCCESS;

public class SearchActivity extends AppCompatActivity implements SearchContract.OnView {
    private SearchView mSearchView;
    private Toolbar mToolbar;
    private RadioFlowLayout mRadioFlowLayout;
    private SearchContract.Presenter mPresenter;

    private List<HotWord> mHotWords = new ArrayList<>();

    private boolean mFirstLoad = true;

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
        return view;
    }

    private void initData() {
        new SearchPresenter(this);
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
            SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setIconifiedByDefault(false);
        }
        return true;
    }

    @Override
    public void onSuccess(List<HotWord> hotWords) {
        mHotWords.clear();
        mHotWords.addAll(hotWords);

        HandlerUtil.post(new UIRunnable(this,HOT_WORD_SUCCESS));
    }

    @Override
    public void onFailure(Exception e) {

    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private static class UIRunnable implements Runnable {

        WeakReference<SearchActivity> mWeak;
        int mType;

        public UIRunnable(SearchActivity activity, int type) {
            mWeak = new WeakReference<>(activity);
            mType = type;
        }

        @Override
        public void run() {
            switch (mType) {
                case HOT_WORD_SUCCESS:
                    if(mWeak.get() != null){
                        for (int i = 0; i < mWeak.get().mHotWords.size(); i++) {
                            HotWord hotWord = mWeak.get().mHotWords.get(i);
                            mWeak.get().mRadioFlowLayout.addView(mWeak.get().createChildView(
                                    hotWord, R.layout.radiobutton));
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}



    
