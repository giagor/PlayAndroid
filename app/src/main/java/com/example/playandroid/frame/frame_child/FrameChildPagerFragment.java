package com.example.playandroid.frame.frame_child;

import android.os.Bundle;

import com.example.playandroid.entity.FrameChild;

import androidx.fragment.app.Fragment;

/**
 * 子体系的ViewPager的碎片.
 * */
public class FrameChildPagerFragment extends Fragment {
    
    private FrameChild mFrameChild;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setFrameChild(FrameChild frameChild) {
        mFrameChild = frameChild;
    }
    
}
