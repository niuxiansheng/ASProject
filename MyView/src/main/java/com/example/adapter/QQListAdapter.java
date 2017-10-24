package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication4.R;

import java.util.List;

/**
 * Created by niutong on 2017-10-18.
 * Discription:
 */

public class QQListAdapter extends BaseAdapter{

    private List<String> mLists;
    private LayoutInflater inflater;
    private int opedPosition;  // last delete or put

    public QQListAdapter(Context context, List<String> mStrings){
        mLists = mStrings;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.delete_btn, null);

            holder.mTextview = (TextView) convertView.findViewById(R.id.item_qqlist_textview);
            holder.btnTop = (Button) convertView.findViewById(R.id.item_qqlist_btnTop);
            holder.btnDelete = (Button) convertView.findViewById(R.id.item_qqlist_btnDelete);
            holder.indexPosition = position;

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
            if (holder.indexPosition == opedPosition) {
                holder = new ViewHolder();

                convertView = inflater.inflate(R.layout.delete_btn, null);

                holder.mTextview = (TextView) convertView.findViewById(R.id.item_qqlist_textview);
                holder.btnTop = (Button) convertView.findViewById(R.id.item_qqlist_btnTop);
                holder.btnDelete = (Button) convertView.findViewById(R.id.item_qqlist_btnDelete);
                holder.indexPosition = position;

                convertView.setTag(holder);
                holder.mTextview.setText(mLists.get(position));
            }
        }

        holder.mTextview.setText(mLists.get(position));

        holder.btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putPositionTOP(position);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePosition(position);
            }
        });

        return convertView;
    }

    public class ViewHolder{
        TextView mTextview;
        Button btnDelete;
        Button btnTop;
        int indexPosition;
    }

    private void removePosition(int position){
        mLists.remove(position);
        opedPosition = position;
        notifyDataSetChanged();
    }

    private void putPositionTOP(int position){
        String str = mLists.remove(position);
        mLists.add(0, str);
        opedPosition = position;
        notifyDataSetChanged();
    }
}
