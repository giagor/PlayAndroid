package com.example.playandroid.frame.frame_child;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.playandroid.R;
import com.example.playandroid.entity.Frame;

import static com.example.playandroid.util.Constants.FrameChildConstant.FRAME;


public class FrameChildActivity extends AppCompatActivity {

    private static final String TAG = "FrameChildActivity";
    private Frame mFrame;
    private Toolbar mToolbar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_child);
        
        initView();
        initData();
        setActionBar();
    }
    
    private void initView(){
        mToolbar = findViewById(R.id.toolbar);
    }
    
    private void initData(){
        mFrame = (Frame) getIntent().getSerializableExtra(FRAME);
    }
    
    private void setActionBar(){
        setSupportActionBar(mToolbar);
        
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            //显示出返回的箭头
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置标题
            actionBar.setTitle(mFrame.getName());
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            //点击返回箭头时，销毁该活动
            finish();
        }
        return true;
    }

    public static void actionStart(Context context, Frame frame){
        Intent intent = new Intent(context, FrameChildActivity.class);
        intent.putExtra(FRAME,frame);
        context.startActivity(intent);
    }
}
