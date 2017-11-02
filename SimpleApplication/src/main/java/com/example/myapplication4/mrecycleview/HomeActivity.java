package com.example.myapplication4.mrecycleview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baselibrary.BaseUtils;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by niutong on 2017-10-27.
 *
 * @author niutong
 *         Discription:
 */

public class HomeActivity extends Activity implements View.OnClickListener{


    private RecyclerView mRecyclerView;
    private Button btn_Delete;
    private Button btn_Add;

    private List<String> mDatas;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recycleview);
        btn_Add = (Button) findViewById(R.id.btn_add);
        btn_Delete = (Button) findViewById(R.id.btn_delete);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
//        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());
        mAdapter.setmOnItemClickLister(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HomeActivity.this, position + " click",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btn_Add.setOnClickListener(this);
        btn_Delete.setOnClickListener(this);
    }

    protected void initData(){
        mDatas = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++){
            mDatas.add("" + (char)i);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                mDatas.add(1, "Insert One");
                mAdapter.notifyItemInserted(1);
                break;
            case R.id.btn_delete:
                mDatas.remove(1);
                mAdapter.notifyItemRemoved(1);
                break;
            default:
                break;
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {

        private AdapterView.OnItemClickListener mOnItemClickLister;

        @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(HomeActivity.this).inflate(R.layout.item_recycleview, parent, false));
            return holder;
        }

        public void setmOnItemClickLister(AdapterView.OnItemClickListener mOnItemClickLister) {
            this.mOnItemClickLister = mOnItemClickLister;
        }

        @Override
        public void onBindViewHolder(final HomeAdapter.MyViewHolder holder, final int position) {
            holder.tv.setText(mDatas.get(position));

            if (mOnItemClickLister != null) {
                holder.tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickLister.onItemClick(null, holder.tv, position, 0);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.id_num);
                int x = BaseUtils.pxToDp(HomeActivity.this, new Random().nextInt(500));
                itemView.getLayoutParams().width = x < 10 ? 10 : x;
                itemView.getLayoutParams().height = x < 10 ? 10 : x;
            }
        }
    }

}
