package com.example.playandroid.util.network;

public class HttpClient {
    public Call newCall(Request request){
        return new Call(request);
    }
}
