package com.example.playandroid.util;

import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;

/**
 * 线程调度工具.
 */
public class HandlerUtil {
    /**
     * 全局唯一.
     */
    private static Handler sHandler;

    private HandlerUtil() {
    }

    static {
        //相当于在主线程中创建了一个Handler
        sHandler = new Handler(Looper.getMainLooper());
    }

    public static void post(Runnable r) {
        if (sHandler != null) {
            sHandler.post(r);
        }
    }
}
