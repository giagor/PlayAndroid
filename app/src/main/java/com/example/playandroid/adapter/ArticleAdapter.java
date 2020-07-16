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
    
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTitle;
        TextView mAuthor;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
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
        return new ViewHolder(view);
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

    public interface onItemClickListener{
        
    }
   
}
