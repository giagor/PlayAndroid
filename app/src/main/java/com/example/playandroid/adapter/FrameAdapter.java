package com.example.playandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.playandroid.R;
import com.example.playandroid.entity.Frame;
import com.example.playandroid.entity.FrameChild;
import com.example.playandroid.view.flowlayout.FlowLayout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.ViewHolder>{

    private List<Frame> mFrames;

    private OnItemClickListener mListener;
    
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
        final Frame frame = mFrames.get(position);
        List<FrameChild> frameChildren = frame.getFrameChildren();
        holder.mFrameName.setText(frame.getName());

        //先移除流式布局中的子View，否则ViewHolder复用之后流式布局中的子View会不断的增多.
        holder.mFlowLayout.removeAllViews();
        
        //为流式布局设置子View
        for(int i = 0;i<frameChildren.size();i++){
            FrameChild frameChild = frameChildren.get(i);
            //获得子View
            View view = FlowLayout.createChildView((int) holder.mFlowLayout.getItemHeight(),
                    frameChild, R.layout.textview);
     
            //子View的添加
            holder.mFlowLayout.addView(view);
            
        }
        
        //设置监听
        if(mListener != null){
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(frame);
                }
            });
        }
        
        
    }

    @Override
    public int getItemCount() {
        return mFrames.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView mFrameName;
        FlowLayout mFlowLayout;
        
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mFrameName = itemView.findViewById(R.id.frame_name);
            mFlowLayout = itemView.findViewById(R.id.hotword_flow_layout);
        }
    }

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onClick(Frame frame);
    }
}
