package com.example.playandroid.util.imageloader;

import android.widget.ImageView;

public class RequestCreator {
    /**
     * 保存图片的url.
     * */
    private String mUrl;
    
    /**
     * 是否放置占位图.
     * */
    private boolean mPlaceHolder = false;
    
    /**
     * 占位图资源.
     * */
    private int mPlaceholderRes = 0;

    /**
     * 标记是否设置加载失败时的图片.
     * */
    private boolean mSetErrorDrawable = false;

    /**
     * 图片加载失败时展示的图片.
     * */
    private int mErrorDrawable = 0;
    
    /**
     * 是否裁剪图片的大小.
     * */
    private boolean mResize = false;
    
    private int mReqWidth = 0;
    
    private int mReqHeight = 0;
    
    RequestCreator(String url){
        mUrl = url;
    }
    
    /**
     * 放置占位图.
     * */
    public RequestCreator placeholder(int resource){
        mPlaceHolder = true;
        mPlaceholderRes = resource;
        return this;
    }
    
    /**
     * 图片加载错误时放置的图片.
     * */
    public RequestCreator error(int resource){
        mSetErrorDrawable = true;
        mErrorDrawable = resource;
        return this;
    }
    
    /**
     * 压缩图片.
     * @param reqWidth 裁剪后图片的宽度.
     * @param reqHeight 裁剪后图片的高度.
     * */
    public RequestCreator resize(int reqWidth,int reqHeight){
        if(reqHeight > 0 && reqWidth >0){
            mResize = true;
            mReqWidth = reqWidth;
            mReqHeight = reqHeight;
        }
        return this;
    }

    /**
     * 将图片设置到哪一个ImageView上面去.
     * */
    public void into(ImageView imageView){
        if(mPlaceHolder){
            //设置占位图
            imageView.setImageResource(mPlaceholderRes);
        }
        BitmapWorkerTask task = new BitmapWorkerTask();
        if(mResize){
            task.resize(mReqWidth,mReqHeight);
        }
        if(mSetErrorDrawable){
            //设置加载出错时的图片
            task.setErrorDrawable(mErrorDrawable);
        }
        task.setImageView(imageView);
        task.execute(mUrl);
    }
}
