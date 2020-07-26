package com.example.playandroid.search.search_hint;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.playandroid.R;
import com.example.playandroid.entity.HotWord;

import java.util.List;

/**
 * 展示搜索热词以及搜索历史的碎片.
 */
public class SearchHintFragment extends Fragment implements SearchHintContract.OnView{
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_hint, container, false);
    }


    @Override
    public void onGetHotWordsSuccess(List<HotWord> hotWords) {
        
    }

    @Override
    public void onGetHotWordsFailure(Exception e) {

    }

    @Override
    public void setPresenter(SearchHintContract.Presenter presenter) {

    }
}
