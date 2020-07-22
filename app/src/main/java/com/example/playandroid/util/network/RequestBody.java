package com.example.playandroid.util.network;

import java.util.List;

public abstract class RequestBody {
    /**
     * 子类实现该类，解析出网络请求的key和value组成的参数字符串.
     * */
    public abstract String encodeParams();
}
