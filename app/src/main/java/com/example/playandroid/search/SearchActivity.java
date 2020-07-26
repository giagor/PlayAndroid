package com.example.playandroid.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.playandroid.R;
import com.example.playandroid.acticle.ArticleDetailActivity;
import com.example.playandroid.adapter.ArticleAdapter;
import com.example.playandroid.entity.Article;
import com.example.playandroid.entity.HotWord;
import com.example.playandroid.search.search_content.SearchContentFragment;
import com.example.playandroid.search.search_hint.SearchHintFragment;
import com.example.playandroid.util.HandlerUtil;
import com.example.playandroid.view.flowlayout.TagModel;
import com.example.playandroid.view.flowlayout.FlowLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static com.example.playandroid.util.Constants.SearchConstant.HOT_WORD_SUCCESS;
import static com.example.playandroid.util.Constants.SearchConstant.SEARCH_SUCCESS;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    private Toolbar mToolbar;
    private SearchHintFragment mSearchHintFragment;
    private SearchContentFragment mSearchContentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        setActionBar();
        initData();
        
    }
    
    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
    }
    
    private void initData(){
//        replaceFragment(new SearchHintFragment());
        //初始化搜索热词界面的碎片
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        mSearchHintFragment = new SearchHintFragment();
        transaction.replace(R.id.fragment_layout,mSearchHintFragment);
        transaction.commit();
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
     * 替换碎片，并且添加到返回栈.
     * */
    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_layout,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
            searchView.setIconifiedByDefault(false);

            //为SearchView设置监听
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                //当点击搜索按钮时，回调该方法.
                @Override
                public boolean onQueryTextSubmit(String query) {
                    
                    if(mSearchContentFragment == null){
                        mSearchContentFragment = new SearchContentFragment();
                    }
                    
                    if(mSearchHintFragment.isShowing()){
                        //如果搜索热词的界面正在显示
                        mSearchContentFragment.setKeyWord(query);
                        replaceFragment(mSearchContentFragment);
                        mSearchHintFragment.setShouldAddViewAgain(true);
                    }else{
                        //如果正在显示的界面时搜索内容的界面
                        mSearchContentFragment.searchContent(query);
                    }
                    
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
}



    
