package com.example.playandroid.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.playandroid.R;
import com.example.playandroid.acticle.ArticleDetailActivity;
import com.example.playandroid.acticle.ArticleFragment;
import com.example.playandroid.frame.FrameFragment;
import com.example.playandroid.project.ProjectFragment;
import com.example.playandroid.search.SearchActivity;

import static com.example.playandroid.util.Constants.MainConstant.ARTICLE;
import static com.example.playandroid.util.Constants.MainConstant.FRAME;
import static com.example.playandroid.util.Constants.MainConstant.PROJECT;



public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        ArticleFragment.OnArticleListener {
    private Toolbar mToolbar;
    
    /**
     * 底部导航栏的3个LinearLayout
     */
    private LinearLayout mLl_home;
    private LinearLayout mLl_frame;
    private LinearLayout mLl_project;

    /**
     * 底部导航栏的3个ImageView
     */
    private ImageView mIv_home;
    private ImageView mIv_frame;
    private ImageView mIv_project;

    /**
     * 底部导航栏的3个标题
     */
    private TextView mTv_home;
    private TextView mTv_frame;
    private TextView mTv_project;
    
    /**
     * 碎片
     * */
    private Fragment mArticleFragment;
    private Fragment mProjectFragment;
    private Fragment mFrameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initView();
        setActionBar();
        initEvent();
        initFragment(ARTICLE);
    }
    
    /**
     * 设置顶部标题栏的信息.
     * */
    private void setActionBar(){
        setSupportActionBar(mToolbar);
        //ToolBar左侧显示出导航栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.navi_white);
        }
    }
    
    private void initView(){
        mToolbar = findViewById(R.id.toolbar);
        
        mLl_home = findViewById(R.id.ll_home);
        mLl_frame = findViewById(R.id.ll_frame);
        mLl_project = findViewById(R.id.ll_project);
        
        mIv_home = findViewById(R.id.iv_home);
        mIv_frame = findViewById(R.id.iv_frame);
        mIv_project = findViewById(R.id.iv_project);
        
        mTv_home = findViewById(R.id.tv_home);
        mTv_frame = findViewById(R.id.tv_frame);
        mTv_project = findViewById(R.id.tv_project);
    }

    private void initEvent(){
        mLl_home.setOnClickListener(this);
        mLl_frame.setOnClickListener(this);
        mLl_project.setOnClickListener(this);
    }
    
    private void initFragment(int index){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragment(transaction);
        switch(index){
            case ARTICLE:
                if(mArticleFragment == null){
                    mArticleFragment = new ArticleFragment();
                    transaction.add(R.id.fragment_layout,mArticleFragment);
                }else{
                    transaction.show(mArticleFragment);
                }
                break;
            case FRAME:
                if(mFrameFragment == null){
                    mFrameFragment = new FrameFragment();
                    transaction.add(R.id.fragment_layout,mFrameFragment);
                }else{
                    transaction.show(mFrameFragment);
                }
                break;
            case PROJECT:
                if(mProjectFragment == null){
                    mProjectFragment = new ProjectFragment();
                    transaction.add(R.id.fragment_layout,mProjectFragment);
                }else {
                    transaction.show(mProjectFragment);
                }
            default:
                break;
        }
        
        transaction.commit();
    }
    
    private void hideFragment(FragmentTransaction transaction){
        if(mArticleFragment != null){
            transaction.hide(mArticleFragment);
        }
        if(mFrameFragment != null){
            transaction.hide(mFrameFragment);
        }
        if(mProjectFragment != null){
            transaction.hide(mProjectFragment);
        }
    }
    
    @Override
    public void onClick(View v) {
        restartButton();
        switch(v.getId()){
            case R.id.ll_home:
                mIv_home.setImageResource(R.drawable.home_green);
                mTv_home.setText("首页");
                initFragment(ARTICLE);
                break;
            case R.id.ll_frame:
                mIv_frame.setImageResource(R.drawable.frame_green);
                mTv_frame.setText("体系");
                initFragment(FRAME);
                break;
            case R.id.ll_project:
                mIv_project.setImageResource(R.drawable.project_green);
                mTv_project.setText("项目");
                initFragment(PROJECT);
                break;
            default:
                break;
        }
    }
    
    /**
     * 底部导航栏的按钮和文字显示默认的颜色
     * */
    private void restartButton(){
        //ImageView设置为黑色
        mIv_home.setImageResource(R.drawable.home_black);
        mIv_frame.setImageResource(R.drawable.frame_black);
        mIv_project.setImageResource(R.drawable.project_black);
        
        //文字设置为空字符串
        mTv_home.setText("");
        mTv_frame.setText("");
        mTv_project.setText("");
    }

    /**
     * 在toolbar加载菜单项
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main,menu);
        return true;
    }

    /**
     * 顶部标题栏(toolbar_main)的菜单项(图标)被选中时回调该方法.
     * */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.search){
            SearchActivity.actionStart(this);
        }
        return true;
    }

    @Override
    public void showArticleDetail(String title,String url) {
        ArticleDetailActivity.actionStart(MainActivity.this,title,url);
    }
}
