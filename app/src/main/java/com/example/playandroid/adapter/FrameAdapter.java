package com.example.playandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.playandroid.R;
import com.example.playandroid.entity.Frame;
import com.example.playandroid.entity.FrameChild;
import com.example.playandroid.view.flowlayout.RadioFlowLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.ViewHolder>{

    private List<Frame> mFrames;

    public FrameAdapter(List<Frame> frames) {
        mFrames = frames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.frame_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Frame frame = mFrames.get(position);
        List<FrameChild> frameChildren = frame.getFrameChildren();
        holder.mFrameName.setText(frame.getName());

        //先移除流式布局中的子View，否则ViewHolder复用之后流式布局中的子View会不断的增多.
        holder.mFlowLayout.removeAllViews();
        
        //为流式布局设置子View
        for(int i = 0;i<frameChildren.size();i++){
            FrameChild frameChild = frameChildren.get(i);
            //获得子View
            View view = RadioFlowLayout.createChildView((int) holder.mFlowLayout.getItemHeight(),
                    frameChild, R.layout.radiobutton);
            //让子View无法被选中
            view.setClickable(false);
            //子View的添加
            holder.mFlowLayout.addView(view);
        }
    }

    @Override
    public int getItemCount() {
        return mFrames.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mFrameName;
        RadioFlowLayout mFlowLayout;
        
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mFrameName = itemView.findViewById(R.id.frame_name);
            mFlowLayout = itemView.findViewById(R.id.flow_layout);
        }
    }
}
