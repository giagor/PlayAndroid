package com.example.playandroid.util.network;

import com.example.playandroid.util.ThreadPool;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Call {
    private Request mRequest;

    public Call(Request request) {
        this.mRequest = request;
    }
    
    public void enqueue(final Callback callback){
        ThreadPool.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream;
                ByteArrayOutputStream byteArrayOutputStream;
                HttpURLConnection connection = null;

                try{
                    URL url = new URL(mRequest.getUrl());
                    connection = (HttpURLConnection) url.openConnection();

                    if(mRequest.getBody() == null){
                        connection.setRequestMethod("GET");
                        connection.setDoOutput(false);
                        connection.setDoInput(true);
                    }else{
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        connection.setDoInput(true);
                        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                        out.writeBytes(mRequest.getBody().encodeParams());
                    }
                    
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    connection.connect();

                    //获取响应状态
                    int responseCode = connection.getResponseCode();

                    if(HttpURLConnection.HTTP_OK == responseCode){
                        inputStream = connection.getInputStream();
                        byteArrayOutputStream = new ByteArrayOutputStream();
                        int readLength;
                        byte[] bytes = new byte[1024];//用于存放每次读取的数据
                        while((readLength = inputStream.read(bytes)) != -1){
                            byteArrayOutputStream.write(bytes,0,readLength);
                        }
                        String response = byteArrayOutputStream.toString();

                        callback.onResponse(response);
                    }else{
                        callback.onFailure(null);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    callback.onFailure(e);
                }finally {
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        });
        
    }
    
}
