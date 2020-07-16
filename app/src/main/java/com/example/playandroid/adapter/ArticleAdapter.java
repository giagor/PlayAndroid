package com.example.playandroid.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.playandroid.R;
import com.example.playandroid.entity.Article;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private List<Article> mArticles;
    private OnItemClickListener mListener;
    
    static class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView mTitle;
        TextView mAuthor;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mTitle = itemView.findViewById(R.id.title);
            mAuthor = itemView.findViewById(R.id.author);
        }
    }

    public ArticleAdapter(List<Article> articles) {
        mArticles = articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        
        //为子项设置点击事件
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onClick(mArticles.get(holder.getAdapterPosition()));
                }
            }
        });
        
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = mArticles.get(position);
        holder.mTitle.setText(article.getTitle());
        holder.mAuthor.setText(article.getAuthor());
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }
    
    public void setListener(OnItemClickListener listener){
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onClick(Article article);
    }
    
   
}
