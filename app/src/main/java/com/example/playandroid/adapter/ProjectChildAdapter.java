package com.example.playandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.playandroid.R;
import com.example.playandroid.entity.ProjectChild;
import com.example.playandroid.util.imageloader.ImageLoader;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProjectChildAdapter extends RecyclerView.Adapter<ProjectChildAdapter.ViewHolder> {
    private List<ProjectChild> mProjectChildren;
    private OnItemClickListener mListener;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ProjectChild projectChild = mProjectChildren.get(position);
        holder.mTitle.setText(projectChild.getTitle());
        holder.mDesc.setText(projectChild.getDesc());
        holder.mAuthor.setText(projectChild.getAuthor());
        holder.mDate.setText(projectChild.getDate());

        if (mListener != null) {
            //为子项设置点击事件
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(mProjectChildren.get(position));
                }
            });
        }
        
        holder.mImage.setTag(projectChild.getPicUrl());
        ImageLoader.get().load(projectChild.getPicUrl())
                .placeholder(R.drawable.empty_photo)
                .error(R.drawable.load_error)
                .resize(80,120)
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mProjectChildren.size();
    }

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }
    
    static class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        ImageView mImage;
        TextView mTitle;
        TextView mDesc;
        TextView mAuthor;
        TextView mDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mImage = itemView.findViewById(R.id.project_img);
            mTitle = itemView.findViewById(R.id.title);
            mDesc = itemView.findViewById(R.id.desc);
            mAuthor = itemView.findViewById(R.id.author_name);
            mDate = itemView.findViewById(R.id.date);
        }
    }

    public interface OnItemClickListener {
        void onClick(ProjectChild projectChild);
    }
}
