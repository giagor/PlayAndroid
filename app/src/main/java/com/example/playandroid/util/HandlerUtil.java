package com.example.playandroid.util;

import android.os.Handler;

public class HandlerUtil {
    private static Handler sHandler;

    private HandlerUtil() {
    }

    static {
        sHandler = new Handler();
    }

    public static void post(Runnable r) {
        if (sHandler != null) {
            sHandler.post(r);
        }
    }
}
