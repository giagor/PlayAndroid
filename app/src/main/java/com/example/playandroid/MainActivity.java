package com.example.playandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.app.AlertDialog;
import android.app.Notification;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //ToolBar左侧显示出导航栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.navi_white);
        }
        
        initView();
        initEvent();
    }
    
    private void initView(){
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
    
    
    @Override
    public void onClick(View v) {
        restartButton();
        switch(v.getId()){
            case R.id.ll_home:
                mIv_home.setImageResource(R.drawable.home_green);
                mTv_home.setText("首页");
                break;
            case R.id.ll_frame:
                mIv_frame.setImageResource(R.drawable.frame_green);
                mTv_frame.setText("体系");
                break;
            case R.id.ll_project:
                mIv_project.setImageResource(R.drawable.project_green);
                mTv_project.setText("项目");
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
}
