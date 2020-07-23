package com.example.playandroid.frame.frame_child;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.playandroid.R;
import com.example.playandroid.entity.Frame;

import static com.example.playandroid.util.Constants.FrameChildConstant.FRAME;


public class FrameChildActivity extends AppCompatActivity {

    private static final String TAG = "FrameChildActivity";
    private Frame mFrame;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_child);
    }
    
    private void initData(){
        mFrame = (Frame) getIntent().getSerializableExtra(FRAME);
        if(mFrame != null){
            Log.d(TAG, "initData: "+mFrame.getName());
        }
    }
    
    public static void actionStart(Context context, Frame frame){
        Intent intent = new Intent(context, FrameChildActivity.class);
        intent.putExtra(FRAME,frame);
        Log.d(TAG, "actionStart: "+frame.getName());
        context.startActivity(intent);
    }
}
