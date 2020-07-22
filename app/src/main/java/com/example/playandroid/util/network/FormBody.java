package com.example.playandroid.util.network;

import java.util.ArrayList;
import java.util.List;

public class FormBody extends RequestBody {
    private List<String> mKeys = new ArrayList<>();
    private List<String> mValues = new ArrayList<>();

    public FormBody(List<String> keys, List<String> values) {
        mKeys = keys;
        mValues = values;
    }

    @Override
    public String encodeParams() {
        StringBuilder sb = new StringBuilder(); 
        if(mKeys != null && mValues != null){
            for(int i = 0;i<mKeys.size() && i< mValues.size();i++){
                if(i != 0){
                    sb.append('&');
                }
                sb.append(mKeys.get(i)).append('=').append(mValues.get(i));
            }
        }
        return sb.toString();
    }

    public static class Builder {
        /**
         * 参数key集合
         */
        List<String> mKeys = new ArrayList<>();

        /**
         * 参数value集合
         */
        List<String> mValues = new ArrayList<>();

        public Builder add(String key,String value){
            mKeys.add(key);
            mValues.add(value);
            return this;
        }
        
        public FormBody build(){
            return new FormBody(mKeys,mValues);
        } 
    }
}
