package com.example.playandroid.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.playandroid.R;
import com.example.playandroid.entity.Article;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Request;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    /**
     * 表示是item的type.
     */
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;

    private List<Article> mArticles;
    private OnItemClickListener mListener;
    private View mFooterView;

    public ArticleAdapter(List<Article> articles) {
        mArticles = articles;
    }

    @Override
    public int getItemViewType(int position) {
        if(mFooterView != null && position == getItemCount() - 1){
            return TYPE_FOOTER;
        }else{
            return TYPE_NORMAL;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //有FooterView时
        if(mFooterView != null && viewType == TYPE_FOOTER){
            return new ViewHolder(mFooterView);
        }
        
        //正常情况
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //如果是FooterView，则不绑定数据
        if(getItemViewType(position) == TYPE_FOOTER){
            return ;
        }
        
        //不是FooterView,则绑定数据
        Article article = mArticles.get(position);
        holder.mTitle.setText(article.getTitle());
        holder.mAuthor.setText(article.getAuthor());
        holder.mTime.setText(article.getTime());

        if (mListener != null) {
            //为子项设置点击事件
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(mArticles.get(position));
                }
            });
        }
    }

    /**
     * 此处返回的是View的数量，包括FooterView.
     * */
    @Override
    public int getItemCount() {
        return mFooterView == null ? mArticles.size() : mArticles.size() + 1;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount());
    }

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView mTitle;
        TextView mAuthor;
        TextView mTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mTitle = itemView.findViewById(R.id.title);
            mAuthor = itemView.findViewById(R.id.author);
            mTime = itemView.findViewById(R.id.time);
        }
    }

    public interface OnItemClickListener {
        void onClick(Article article);
    }
}
