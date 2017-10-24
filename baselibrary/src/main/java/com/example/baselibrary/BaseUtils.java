package com.example.baselibrary;

import android.content.Context;

/**
 * Created by niutong on 2017-10-18.
 * Discription:
 */

public class BaseUtils {

    public static int dpToPx(Context context, float dpValue) {//dp转换为px
        float scale=context.getResources().getDisplayMetrics().density;//获得当前屏幕密度
        return (int)(dpValue*scale+0.5f);
    }

    public static int pxToDp(Context context,float pxValue) {//px转换为dp
        float scale=context.getResources().getDisplayMetrics().density;//获得当前屏幕密度
        return (int)(pxValue/scale+0.5f);
    }
}
