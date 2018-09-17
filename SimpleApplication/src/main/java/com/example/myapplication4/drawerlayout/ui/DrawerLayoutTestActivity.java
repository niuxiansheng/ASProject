package com.example.myapplication4.drawerlayout.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.myapplication.R;

/**
 * Created by XMuser on 2018-05-15.
 */

public class DrawerLayoutTestActivity extends AppCompatActivity {

    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_test);
        initView();
    }

    private void initView(){
        mToolbar = (Toolbar) findViewById(R.id.mTool_bar);
        mToolbar.setTitle("testToolbar");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // 显示返回图标
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        mActionBarDrawerToggle.setHomeAsUpIndicator(R.mipmap.ic_launcher); // 图标
        mActionBarDrawerToggle.syncState(); // 使用默认设置，会与图标设置冲突，覆盖图标设置
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
    }
}
