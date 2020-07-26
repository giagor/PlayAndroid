package com.example.playandroid.search.search_content;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.playandroid.R;
import com.example.playandroid.entity.Article;

import java.util.List;

/**
 * 展示搜索内容的碎片.
 */
public class SearchContentFragment extends Fragment implements SearchContentContract.OnView{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_content, container, false);
    }

    @Override
    public void onSearchContentSuccess(List<Article> articles) {
        
    }

    @Override
    public void onSearchContentFailure(Exception e) {

    }

    @Override
    public void setPresenter(SearchContentContract.Presenter presenter) {

    }
}
