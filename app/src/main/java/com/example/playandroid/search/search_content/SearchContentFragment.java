package com.example.playandroid.search.search_content;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.playandroid.R;
import com.example.playandroid.acticle.ArticleDetailActivity;
import com.example.playandroid.adapter.ArticleAdapter;
import com.example.playandroid.entity.Article;
import com.example.playandroid.util.HandlerUtil;
import com.example.playandroid.util.network.LogUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.example.playandroid.util.Constants.SearchContentConstant.SEARCH_SUCCESS;

/**
 * 展示搜索内容的碎片.
 */
public class SearchContentFragment extends Fragment implements SearchContentContract.OnView
        ,ArticleAdapter.OnItemClickListener{

    private static final String TAG = "SearchContentFragment";
    private View mView;
    private SearchContentContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private String mKeyWord = null;

    /**
     * 展示搜索内容的适配器.
     */
    private ArticleAdapter mAdapter;
    
    /**
     * 搜索得到的文章列表.
     */
    private List<Article> mArticles = new ArrayList<>();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search_content, container, false);
        
        initView();
        initData();
        
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        
        //判断当前碎片所带有的keyWord是否为空，不为空则去搜索内容
        if(mKeyWord != null){
            mPresenter.searchContents(mKeyWord);
            mKeyWord = null;
        }
    }

    private void initView(){
        mRecyclerView = mView.findViewById(R.id.search_content);
    }
    
    private void initData(){
        new SearchContentPresenter(this);

        //为RecyclerView设置数据
        mArticles.clear();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new ArticleAdapter(mArticles);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setListener(this);
    }
    
    @Override
    public void onSearchContentSuccess(List<Article> articles) {
        mArticles.clear();
        mArticles.addAll(articles);
        
        HandlerUtil.post(new UIRunnable(this, SEARCH_SUCCESS));
    }

    @Override
    public void onSearchContentFailure(Exception e) {
        LogUtil.d(TAG,e.getMessage());
    }
    
    /**
     * 搜索内容的方法，供外部调用.
     * */
    public void searchContent(String keyword){
        mPresenter.searchContents(keyword);
    }
    
    @Override
    public void setPresenter(SearchContentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void setKeyWord(String keyWord) {
        mKeyWord = keyWord;
    }

    @Override
    public void onClick(Article article) {
        ArticleDetailActivity.actionStart(getContext(), article.getTitle(), article.getLink());
    }
    
    private static class UIRunnable implements Runnable{

        private WeakReference<SearchContentFragment> mWeak;
        private int mType;

        UIRunnable(SearchContentFragment fragment, int type) {
            mWeak = new WeakReference<>(fragment);
            mType = type;
        }

        @Override
        public void run() {
            switch (mType){
                case SEARCH_SUCCESS:
                    if(mWeak.get() != null){
                        mWeak.get().mAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
