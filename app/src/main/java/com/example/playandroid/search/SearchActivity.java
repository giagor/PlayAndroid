package com.example.playandroid.search;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.playandroid.R;

public class SearchActivity extends AppCompatActivity {
    private SearchView mSearchView;
    private Toolbar mToolbar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        
        initView();
        setActionBar();
    }


    private void initView(){
        mToolbar = findViewById(R.id.toolbar);
    }
    
    /**
     * 设置顶部标题栏的信息.
     * */
    private void setActionBar(){
        setSupportActionBar(mToolbar);
    }
    
    /**
     * 启动该活动.
     * */
    public static void actionStart(Context context){
        Intent intent = new Intent(context,SearchActivity.class);
        context.startActivity(intent);
    }

    /**
     * 加载toolbar的menu.
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_search,menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        if(menuItem != null){
            SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setIconifiedByDefault(false);
        }
        return true;
    }
}



    
