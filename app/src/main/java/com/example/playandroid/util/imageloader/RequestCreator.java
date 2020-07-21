package com.example.playandroid.util.imageloader;

import android.widget.ImageView;

import com.example.playandroid.util.network.Request;

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
    
    public RequestCreator(String url){
        mUrl = url;
    }
    
    /**
     * 放置占位图.
     * */
    public RequestCreator placeholder(int resource){
        return null;
    }
    
    /**
     * 图片加载错误时放置的图片.
     * */
    public RequestCreator error(int resource){
        return null;
    }
    
    /**
     * 压缩图片.
     * @param reqWidth 裁剪后图片的宽度.
     * @param reqHeight 裁剪后图片的高度.
     * */
    public RequestCreator resize(int reqWidth,int reqHeight){
        return null;
    }

    /**
     * 将图片设置到哪一个ImageView上面去.
     * */
    public void into(ImageView imageView){
        if(mPlaceHolder){
            imageView.setImageResource(mPlaceholderRes);
        }
        
    }
}
