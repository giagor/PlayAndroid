package com.example.playandroid.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.playandroid.R;

/**
 * "项目"碎片模块嵌套的ViewPager的Pager(即这里的碎片)
 * */
public class ProjectPagerFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project_pager, container, false);
    }
}
