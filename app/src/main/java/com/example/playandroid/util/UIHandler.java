package com.example.playandroid.util;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;

/**
 * 线程调度工具还没有思路，暂时先这样写着
 */
public class UIHandler extends Handler {
    private WeakReference<HandlerListener> mWeakRef;

    public UIHandler(HandlerListener listener) {
        mWeakRef = new WeakReference<>(listener);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        if (mWeakRef != null && mWeakRef.get() != null) {
            mWeakRef.get().handlerMessage(msg);
        }
    }

    public interface HandlerListener {
        void handlerMessage(@NonNull Message msg);
    }
}
