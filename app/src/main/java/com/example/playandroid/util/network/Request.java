package com.example.playandroid.util.network;

/**
 * 封装的网络请求的Request类.
 * */
public class Request {
    private String mUrl;

    private RequestBody mBody;
    
    public Request(Builder builder) {
        mUrl = builder.mUrl;
        mBody = builder.mBody;
    }

    public String getUrl() {
        return mUrl;
    }

    public RequestBody getBody() {
        return mBody;
    }

    public static class Builder{
        private String mUrl;
        private RequestBody mBody;
        
        public Builder url(String url){
            this.mUrl = url;
            return this;
        }        
        
        public Builder post(RequestBody requestBody){
            mBody = requestBody;
            return this;
        }
        
        public Request build(){
            return new Request(this);
        }
    }
}
