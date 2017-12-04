package com.example.baselibrary;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Modify by niutong at 2017-11-25
 *
 * @author niutong
 * @date 2017-11-25
 * Discription:
 */

public class MyThreadPool {

    public static MyThreadPool myThreadPool;

    private ThreadPoolExecutor mThreadPoolExecutor;

    public static MyThreadPool instance(){
        if (myThreadPool == null){
            synchronized (MyThreadPool.class)
            {
                if (myThreadPool == null){
                    myThreadPool = new MyThreadPool();
                }
            }
        }
        return myThreadPool;
    }

    ThreadFactory mThreadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Demo Thread");
        }
    };

    public ThreadPoolExecutor getThreadPoolExecutor(){
        if (mThreadPoolExecutor == null){
            mThreadPoolExecutor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MINUTES, new LinkedBlockingDeque<Runnable>(), mThreadFactory);
        }
        return mThreadPoolExecutor;
    }
}
