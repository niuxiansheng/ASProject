package com.example.myapplication4;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication4.baseAdapter.ListAdapterGuideModule;
import com.example.myapplication4.baseConfig.DemoModule;

import java.util.List;

/**
 * Created by niutong on 2017-10-20.
 *
 * @author niutong
 *         Discription:
 */

public abstract class GuideActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private ListView mListView = null;
    private ListAdapterGuideModule mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guide);

        mListView = (ListView) findViewById(R.id.listViewGuide);
        mAdapter = new ListAdapterGuideModule(this, getGuideModules());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }
    
    /**
    * Discription :
     * @return list of item module
    */
    protected abstract List<DemoModule> getGuideModules();

    protected DemoModule getGuideModule(int position){
        if (position >= 0 && position < getGuideModules().size()) {
            return getGuideModules().get(position);
        }
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getGuideModule(position).startModule(this);
    }
}
