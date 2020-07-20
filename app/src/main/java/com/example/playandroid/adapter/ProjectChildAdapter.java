package com.example.playandroid.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.playandroid.R;
import com.example.playandroid.entity.ProjectChild;
import com.example.playandroid.util.ImageMemoryCache;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_list_item, null);
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

    /**
     * 加载图片的方法.
     * 先从内存缓存中寻找是否有匹配的图片，没有再从磁盘缓存寻找或网络下载.
     */
    private void loadBitmaps(ImageView imageView, String url) {
        Bitmap bitmap = ImageMemoryCache.getBitmapFromMemoryCache(url);
        if (bitmap == null) {

        } else if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
    }
}
