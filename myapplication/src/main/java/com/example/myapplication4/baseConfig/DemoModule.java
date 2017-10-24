package com.example.myapplication4.baseConfig;

import android.content.Context;
import android.content.Intent;

/**
 * Created by niutong on 2017-10-20.
 *
 * @author niutong
 *         Discription:
 */

public class DemoModule {

    public Integer iconResId;
    public Integer itemText;
    Class<?> classActivity;

    public DemoModule(Integer icon, Integer text, Class<?> classActivity){
        this.iconResId = icon;
        this.itemText = text;
        this.classActivity = classActivity;
    }

    public boolean startModule(Context context){
        if (classActivity != null) {
            Intent intent = new Intent();
            intent.setClass(context, classActivity);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            return true;
        }

        return false;
    }
}
