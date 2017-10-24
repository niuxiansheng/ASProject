package com.example.myapplication4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.adapter.QQListAdapter;
import com.example.baselibrary.BaseUtils;
import com.example.myviews.QQListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private QQListView mListView;
    private QQListAdapter mAdapter;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listview_qq);

        mListView = (QQListView) findViewById(R.id.id_listview);
        mListView.setMaxLength(BaseUtils.dpToPx(this, 200));
        mDatas = new ArrayList<String>(Arrays.asList("HelloWorld", "Welcome", "Java", "Android", "Servlet", "Struts"));
        mAdapter = new QQListAdapter(this, mDatas);
        mListView.setAdapter(mAdapter);

    }
}
