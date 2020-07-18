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
import com.example.playandroid.view.flowlayout.TagModel;
import com.example.playandroid.view.flowlayout.RadioFlowLayout;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity{
    private SearchView mSearchView;
    private Toolbar mToolbar;
    private RadioFlowLayout mRadioFlowLayout;

    private List<HotWord> mHotWords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        setActionBar();
        initData();

        for (int i = 0; i < mHotWords.size(); i++) {
            mRadioFlowLayout.addView(this.createChildView(mHotWords.get(i), R.layout.radiobutton));
        }
    }
    
    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mRadioFlowLayout = findViewById(R.id.radioFlowLayout);
    }

    /**
     * 创建子view
     * */
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
        String[] words = {"捷豹", "施华洛世奇", "雷朋", "Emporio Armani", "海伦凯勒",
                "精工", "HORIEN海俪恩", "CHARMANT", "COACH蔻驰", "李维斯", "新百伦"};
        for (int i = 0; i < words.length; i++) {
            HotWord hotWord = new HotWord(i, words[i]);
            mHotWords.add(hotWord);
        }
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
}



    
