package com.example.myapplication4;

/**
 * Created by Administrator on 2017-02-24.
 */

public class StorageInfo {
    public String path;
    public String state;
    public boolean isRemoveable;

    public StorageInfo(String path){
        this.path = path;
    }

    public boolean isMounted(){
        return "mounted".equals(state);
    }

    @Override
    public String toString() {
        return "Path = " + path + "State = " + state + "IsRemovable = " + isRemoveable;
    }
}

