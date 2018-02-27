package com.example.sufehelperapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends BaseAdapter {

    List<String> datas1 = new ArrayList<>();
    Context mContext;
    private View convertView;
    public AddressAdapter(Context context) {
        this.mContext = context;
    }

    public void setDatas(List<String> datas) {
        this.datas1 = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas1 == null?0:datas1.size();
    }

    @Override
    public Object getItem(int i) {
        return datas1 == null?null:datas1.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_my_register_third_interal,null);
            holder.mTextView = (TextView) convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTextView.setText(datas1.get(i));
        return convertView;
    }
    
    private static class ViewHolder{
        TextView mTextView;
    }
}
