package com.example.playandroid.util.network;

/**
 * 封装的网络请求的Request类.
 * */
public class Request {
    private String mUrl;

    public Request(String url) {
        this.mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public static class Builder{
        private String sUrl;
        
        public Builder url(String url){
            this.sUrl = url;
            return this;
        }        
        
        public Request build(){
            return new Request(sUrl);
        }
    }
}
