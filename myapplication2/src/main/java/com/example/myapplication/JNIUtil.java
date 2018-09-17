package com.example.myapplication;

/**
 * Created by XMuser on 2018-03-12.
 */

public class JNIUtil {

    static {
        System.loadLibrary("NullProject");
    }

    public native String getWorld();
}
