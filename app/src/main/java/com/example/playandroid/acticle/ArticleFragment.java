package com.example.playandroid.acticle;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.playandroid.R;
import com.example.playandroid.adapter.ArticleAdapter;
import com.example.playandroid.entity.Article;
import com.example.playandroid.main.MainActivity;
import com.example.playandroid.util.HandlerUtil;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.playandroid.util.Constants.ArticleConstant.SUCCESS;

public class ArticleFragment extends Fragment implements ArticleContract.OnView, 
        ArticleAdapter.OnItemClickListener {
    private ArticleContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private View mView;
//    private Handler mHandler;
    private List<Article> mArticles;
    
    /**
     * 标记是否第一次请求数据.
     * */
    private boolean mFirstLoad = true;
    
    /**
     * 碎片和活动通信的接口引用.
     * */
//    private OnArticleListener mCallback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        mCallback = (OnArticleListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, 
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_article,container,false);
        
        initData();
        
        return mView;
    }

    private void initData(){
        new ArticlePresenter(this);
        
//        mHandler = new UIHandler(this);
        
        mArticles = new ArrayList<>();
        
        mRecyclerView = mView.findViewById(R.id.recycler_view);
    }
    
    private void initEvent(){
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if(mPresenter != null && mFirstLoad){
            mPresenter.start();
            mFirstLoad = false;
        }
    }

    @Override
    public void setPresenter(ArticleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onSuccess(List<Article> articles) {
        mArticles.clear();
        mArticles.addAll(articles);
//        Message msg = Message.obtain();
//        msg.what = SUCCESS;
//        mHandler.sendMessage(msg);
        HandlerUtil.post(new UIRunnable(this,SUCCESS));
    }

    @Override
    public void onFail(Exception e) {

    }

//    @Override
//    public void handlerMessage(@NonNull Message msg) {
//        switch (msg.what){
//            case SUCCESS:
//                RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
//                mRecyclerView.setLayoutManager(manager);
//                ArticleAdapter adapter = new ArticleAdapter(mArticles);
//                adapter.setListener(this);
//                mRecyclerView.setAdapter(adapter);
//                break;
//            default:
//                break;
//        }
//    }

    @Override
    public void onClick(Article article) {
        if(getContext() != null){
            ((MainActivity)getContext()).showArticleDetail(article.getTitle(),article.getLink());
        }
    }

//    /**
//     * 回调接口，主活动实现，用于打开文章的主界面.
//     * */
//    public interface OnArticleListener{
//        void showArticleDetail(String title,String url);
//    }
    
    private static class UIRunnable implements Runnable{

        private WeakReference<ArticleFragment> mWeak;
        private int mType;
        
        UIRunnable(ArticleFragment fragment, int type) {
            mWeak = new WeakReference<>(fragment);
            mType = type;
        }

        @Override
        public void run() {
            switch(mType){
                case SUCCESS:
                    if(mWeak.get() != null){
                        RecyclerView.LayoutManager manager = new LinearLayoutManager(mWeak.get().getContext());
                        mWeak.get().mRecyclerView.setLayoutManager(manager);
                        ArticleAdapter adapter = new ArticleAdapter(mWeak.get().mArticles);
                        adapter.setListener(mWeak.get());
                        mWeak.get().mRecyclerView.setAdapter(adapter);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
