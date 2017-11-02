package com.example.myapplication4.baseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication4.baseConfig.DemoModule;

import java.util.List;

/**
 * Created by niutong on 2017-10-20.
 *
 * @author niutong
 *         Discription:
 */

public class ListAdapterGuideModule extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<DemoModule> mListModules;

    public ListAdapterGuideModule(Context context, List<DemoModule> modules){
        mInflater = LayoutInflater.from(context);
        mListModules = modules;
    }

    @Override
    public int getCount() {
        return mListModules.size();
    }

    @Override
    public Object getItem(int position) {
        return mListModules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_guide_list, null);

            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imgItemIcon);
            holder.textView = (TextView) convertView.findViewById(R.id.txtItemTitle);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        DemoModule module = mListModules.get(position);

        if (module.iconResId > 0) {
            holder.imageView.setImageResource(module.iconResId);
        }else{
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        }

        if (module.itemText > 0) {
            holder.textView.setText(module.itemText);
        }else {
            holder.textView.setText("");
        }
        return convertView;
    }

    public class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
