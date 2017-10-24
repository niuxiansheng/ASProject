package com.example.myapplication4;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication4.baseConfig.DemoModule;
import com.example.myapplication4.functionstorageurlget.ActivityStorageGet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class MainActivity extends GuideActivity implements View.OnClickListener{

    private List<DemoModule> mGuideModules = new ArrayList<>();

    protected void addDemoModules(){
        mGuideModules.add(new DemoModule(-1, R.string.storage_get, ActivityStorageGet.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        addDemoModules();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List<DemoModule> getGuideModules() {
        return mGuideModules;
    }

    @Override
    public void onClick(View v) {

    }
}
