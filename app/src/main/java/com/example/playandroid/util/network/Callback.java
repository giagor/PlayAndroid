package com.example.playandroid.util.network;

/**
 * 进行网络的异步请求时，需要传入的回调接口.
 */
public interface Callback {
    void onFailure(Exception e);
    
    void onResponse(String response);
}
