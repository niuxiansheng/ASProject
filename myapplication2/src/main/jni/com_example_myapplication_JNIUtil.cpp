//
// Created by XMuser on 2018-03-12.
//

#include "com_example_myapplication_JNIUtil.h"

JNIEXPORT jstring JNICALL Java_com_example_myapplication_JNIUtil_getWorld(JNIEnv *env, jobject obj){
    return env-> NewStringUTF("Hello World");
}
