package com.example.playandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.playandroid.R;
import com.example.playandroid.entity.ProjectChild;
import com.example.playandroid.util.imageloader.BitmapWorkerTask;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProjectChildAdapter extends RecyclerView.Adapter<ProjectChildAdapter.ViewHolder> {
    private List<ProjectChild> mProjectChildren;

    public ProjectChildAdapter(List<ProjectChild> projectChildren) {
        mProjectChildren = projectChildren;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_child_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProjectChild projectChild = mProjectChildren.get(position);
        holder.mTitle.setText(projectChild.getTitle());
        holder.mDesc.setText(projectChild.getDesc());
        holder.mAuthor.setText(projectChild.getAuthor());
        holder.mDate.setText(projectChild.getDate());

        holder.mImage.setImageResource(R.drawable.empty_photo);
        holder.mImage.setTag(projectChild.getPicUrl());
        //通过三级缓存寻找图片
        BitmapWorkerTask task = new BitmapWorkerTask();
        task.setImageView(holder.mImage);
        task.setErrorDrawable(R.drawable.load_error);
        task.execute(projectChild.getPicUrl());
    }

    @Override
    public int getItemCount() {
        return mProjectChildren.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImage;
        TextView mTitle;
        TextView mDesc;
        TextView mAuthor;
        TextView mDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.project_img);
            mTitle = itemView.findViewById(R.id.title);
            mDesc = itemView.findViewById(R.id.desc);
            mAuthor = itemView.findViewById(R.id.author_name);
            mDate = itemView.findViewById(R.id.date);
        }
    }
    
}
