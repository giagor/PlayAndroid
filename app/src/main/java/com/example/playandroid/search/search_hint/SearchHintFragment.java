package com.example.playandroid.search.search_hint;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.playandroid.R;
import com.example.playandroid.dao.DatabaseHelper;
import com.example.playandroid.entity.HotWord;
import com.example.playandroid.entity.SearchHistory;
import com.example.playandroid.util.HandlerUtil;
import com.example.playandroid.util.network.LogUtil;
import com.example.playandroid.view.flowlayout.FlowLayout;
import com.example.playandroid.view.flowlayout.TagModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.example.playandroid.util.Constants.DatabaseConstant.ARTICLE_DB_NAME;
import static com.example.playandroid.util.Constants.DatabaseConstant.CURRENT_VERSION;
import static com.example.playandroid.util.Constants.SearchHintConstant.HOT_WORD_SUCCESS;

/**
 * 展示搜索热词以及搜索历史的碎片.
 */
public class SearchHintFragment extends Fragment implements SearchHintContract.OnView,
        View.OnClickListener {

    private static final String TAG = "SearchHintFragment";
    private SearchHintContract.Presenter mPresenter;
    private boolean mFirstLoad = true;
    private FlowLayout mHotWordFlowLayout;
    private List<HotWord> mHotWords = new ArrayList<>();
    private View mView;
    private OnListener mListener;
    private FlowLayout mHistoryFlowLayout;
    private DatabaseHelper mHelper;
    
    /**
     * 用于保存历史搜索记录.
     */
    private List<SearchHistory> mHistories = new ArrayList<>();

    /**
     * 标记是否需要恢复界面的View.
     */
    private boolean mShouldAddViewAgain = false;

    /**
     * 标记当前页面是否正在显示.
     */
    private boolean mShowing = false;

    /**
     * 对数据库进行CRUD.
     */
    private SQLiteDatabase mDatabase;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //获取数据库的帮助类
        mHelper = new DatabaseHelper(getContext(), ARTICLE_DB_NAME, null,
                CURRENT_VERSION);
        mDatabase = mHelper.getWritableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search_hint, container, false);

        initView();
        initData();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mFirstLoad) {
            mFirstLoad = false;
            mPresenter.start();
        }

        //恢复搜索热词的子View
        if (mShouldAddViewAgain) {
            addViewToHotWordLayout();
            mShouldAddViewAgain = false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //当前页面正在显示.
        mShowing = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        //当前页面没有在显示
        mShowing = false;
    }

    public void setShouldAddViewAgain(boolean shouldAddViewAgain) {
        mShouldAddViewAgain = shouldAddViewAgain;
    }

    private void initView() {
        mHotWordFlowLayout = mView.findViewById(R.id.hotword_flow_layout);
        mHistoryFlowLayout = mView.findViewById(R.id.hitsory_flow_layout);
    }

    private void initData() {
        new SearchHintPresenter(this);
        
        //从数据库中获取历史搜索.
        mPresenter.getAllHistories(mDatabase);
    }

    /**
     * 向流式布局中添加搜索热词.
     * */
    private void addViewToHotWordLayout() {
        for (int i = 0; i < mHotWords.size(); i++) {
            HotWord hotWord = mHotWords.get(i);
            //获得流式布局的子view
            View view = FlowLayout.createChildView((int) mHotWordFlowLayout.getItemHeight(), hotWord,
                    R.layout.textview);
            view.setBackgroundResource(R.color.deepGreen);
            //设置点击监听
            view.setOnClickListener(this);
            //添加子View
            mHotWordFlowLayout.addView(view);
        }
    }

    /**
     * 向流式布局中添加历史搜索.
     * */
    private void addViewToSearchHistoryLayout(){
        for(SearchHistory history : mHistories){
            //获得流式布局的子View
            View view = FlowLayout.createChildView((int)mHistoryFlowLayout.getItemHeight(),history,
                    R.layout.textview);
            view.setBackgroundResource(R.color.deepGreen);
            //设置点击监听
            view.setOnClickListener(this);
            //添加子View
            mHistoryFlowLayout.addView(view);
        }
    }
    
    /**
     * 获得搜索热词页面的显示情况.
     */
    public boolean isShowing() {
        return mShowing;
    }

    /**
     * 每当用户进行搜索时，把该历史搜索添加到流式布局和数据库中.
     * */
    public void addHistoryView(String query){
//        boolean contain = false;
//        for(SearchHistory history : mHistories){
//            if(history.getName().equals(query)){
//                contain = true;
//                break;
//            }
//        }
//        
//        if(!contain){
//            View view = FlowLayout.createChildView(mHistoryFlowLayout.getItemHeight(),)
//        }
    }
    
    @Override
    public void onGetHotWordsSuccess(List<HotWord> hotWords) {
        mHotWords.clear();
        mHotWords.addAll(hotWords);

        HandlerUtil.post(new UIRunnable(this, HOT_WORD_SUCCESS));
    }

    @Override
    public void onGetHotWordsFailure(Exception e) {
        LogUtil.d(TAG, e.getMessage());
    }

    @Override
    public void getHistoriesFromDaoSuccess(List<SearchHistory> histories) {
       mHistories.clear();
       mHistories.addAll(histories);
       
       addViewToSearchHistoryLayout();
    }

    @Override
    public void setPresenter(SearchHintContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onClickQuery(((HotWord) (((TagModel) (v.getTag())).getT())).getName());
        }
    }

    @Override
    public void onDestroy() {
        mHelper.close();
        super.onDestroy();
    }
    
    private static class UIRunnable implements Runnable {

        private WeakReference<SearchHintFragment> mWeak;
        private int mType;

        UIRunnable(SearchHintFragment fragment, int type) {
            mWeak = new WeakReference<>(fragment);
            mType = type;
        }

        @Override
        public void run() {
            switch (mType) {
                case HOT_WORD_SUCCESS:
                    if (mWeak.get() != null) {
                        mWeak.get().addViewToHotWordLayout();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public interface OnListener {
        /**
         * 点击搜索热词时，直接回调活动中的方法，进行搜索.
         */
        void onClickQuery(String query);
    }
}
