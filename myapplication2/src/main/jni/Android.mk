LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := NullProject
LOCAL_SRC_FILES =: com_example_myapplication_JNIUtil.cpp
include $(BUILD_SHARED_LIBRARY)