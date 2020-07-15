package com.example.playandroid.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池(暂时先用这个).
 */
public class ThreadPool {
    /**
     * 一段时间内线程可以复用.
     */
    private static ExecutorService sThreadPool;

    static {
        sThreadPool = Executors.newCachedThreadPool();
    }

    public static ExecutorService getThreadPool() {
        return sThreadPool;
    }

    public static void shutDownPool() {
        sThreadPool.shutdown();
    }
}
