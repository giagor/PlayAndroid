package com.example.playandroid.util.imageloader;

import com.example.playandroid.util.network.Request;

public class ImageLoader {
    private volatile static ImageLoader sImageLoader = null;
    
    private ImageLoader(){
    }
    
    /**
     * 获取一个ImageLoader单例.
     * */
    public static ImageLoader get(){
        if(sImageLoader == null){
            synchronized (ImageLoader.class){
                if(sImageLoader == null){
                    sImageLoader = new ImageLoader();
                }
            }
        }
        return sImageLoader;
    }
    
    public RequestCreator load(String url){
        return new RequestCreator(url);
    }
}
