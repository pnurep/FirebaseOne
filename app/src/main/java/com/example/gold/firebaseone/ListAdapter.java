package com.example.gold.firebaseone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gold on 2017. 3. 13..
 */

public class ListAdapter extends BaseAdapter {

    List<Bbs> datas;
    Context context;
    LayoutInflater inflater;

    public ListAdapter(List<Bbs> data, Context context){
        this.context = context;
        this.datas = data;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvContent = (TextView) convertView.findViewById(R.id.tvContent);

        Bbs bbs = datas.get(position);
        tvTitle.setText(bbs.title);
        tvContent.setText(bbs.content);

        return convertView;
    }
}
