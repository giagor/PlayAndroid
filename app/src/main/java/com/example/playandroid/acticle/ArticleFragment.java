package com.example.playandroid.acticle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.playandroid.R;
import com.example.playandroid.adapter.ArticleAdapter;
import com.example.playandroid.entity.Article;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleFragment extends Fragment implements ArticleContract.OnView{
    private ArticleContract.Presenter mArticlePresenter;
    private RecyclerView mRecyclerView;
    private View mView;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, 
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.acticle_fragment,container,false);
        
        initData();
        
        return mView;
    }

    private void initData(){
        new ArticlePresenter(this);
        
        mRecyclerView = mView.findViewById(R.id.recycler_view);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mArticlePresenter != null){
            mArticlePresenter.start();
        }
    }

    @Override
    public void setPresenter(ArticleContract.Presenter presenter) {
        mArticlePresenter = presenter;
    }

    @Override
    public void onSuccess(List<Article> articles) {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        ArticleAdapter adapter = new ArticleAdapter(articles);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onFail(Exception e) {

    }
}
