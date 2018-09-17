package com.example.myapplication4;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityGuideBinding;
import com.example.myapplication4.baseAdapter.ListAdapterGuideModule;
import com.example.myapplication4.baseConfig.DemoModule;
import com.example.myapplication4.mainlist.bingding.DataActivityGuide;

import java.util.List;

/**
 * Created by niutong on 2017-10-20.
 *
 * @author niutong
 *         Discription:
 */

public abstract class GuideActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private ListAdapterGuideModule mAdapter = null;
    private DataActivityGuide dataActivityGuide;
    private ActivityGuideBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_guide);

        dataActivityGuide = new DataActivityGuide(R.drawable.common_list_selector);
        binding.setGuideList(dataActivityGuide);

        mAdapter = new ListAdapterGuideModule(this, getGuideModules());
        binding.listViewGuide.setAdapter(mAdapter);
        binding.listViewGuide.setOnItemClickListener(this);
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
