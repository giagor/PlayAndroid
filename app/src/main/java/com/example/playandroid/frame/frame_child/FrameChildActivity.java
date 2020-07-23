package com.example.playandroid.frame.frame_child;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.playandroid.R;
import com.example.playandroid.entity.Frame;
import com.example.playandroid.entity.FrameChild;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.playandroid.util.Constants.FrameChildConstant.FRAME;


public class FrameChildActivity extends AppCompatActivity {

    private static final String TAG = "FrameChildActivity";
    private Frame mFrame;
    private List<FrameChild> mFrameChildren;
    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_child);
        
        initView();
        initData();
        setActionBar();
        showFrameChildPager();
    }
    
    private void initView(){
        mToolbar = findViewById(R.id.toolbar);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
    }
    
    private void initData(){
        mFrame = (Frame) getIntent().getSerializableExtra(FRAME);
        
        //获得子体系的List.
        if(mFrame != null){
            mFrameChildren = mFrame.getFrameChildren();
            if(mFrameChildren != null){
                //获取子体系的标题,以及初始化碎片List
                for(FrameChild frameChild : mFrameChildren){
                    mTitles.add(frameChild.getName());
                    FrameChildPagerFragment fragment = new FrameChildPagerFragment();
                    fragment.setFrameChild(frameChild);
                    mFragments.add(fragment);
                }
            }
        }
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

    /**
     * 展示体系的ViewPager(子体系).
     * */
    private void showFrameChildPager(){
        //设置预加载数量
        mViewPager.setOffscreenPageLimit(3);
        
        //设置适配器
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFrameChildren.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles.get(position);
            }
        });
        
        //设置联动
        mTabLayout.setupWithViewPager(mViewPager);
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
