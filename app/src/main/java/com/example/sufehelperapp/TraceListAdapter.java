package com.example.sufehelperapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TraceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private List<Trace> traceList = new ArrayList<>(1);
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL = 0x0001;
    private static final int TYPE_THIRD = 0x0002;
    private static final int TYPE_FORTH = 0x0003;

    private int progress;
    public TraceListAdapter(Context context,List<Trace> traceList) {
        this.inflater = LayoutInflater.from(context);
        this.traceList = traceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.activity_time_list,parent,false));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemHolder = (ViewHolder) holder;
        /*if(getItemViewType(position) == TYPE_TOP) {
            // 第一行头的竖线不显示
            itemHolder.tvTopLine.setVisibility(View.INVISIBLE);
            // 字体颜色加深
            itemHolder.tvAcceptTime.setTextColor(0xff555555);
            itemHolder.tvAcceptStation.setTextColor(0xff555555);
            itemHolder.tvDot.setBackgroundResource(R.drawable.timeline_dot_first);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            itemHolder.tvTopLine.setVisibility(View.VISIBLE);
            itemHolder.tvAcceptTime.setTextColor(0xff999999);
            itemHolder.tvAcceptStation.setTextColor(0xff999999);
            itemHolder.tvDot.setBackgroundResource(R.drawable.timeline_dot_normal);
        }*/
        if(getItemViewType(position) == TYPE_TOP && getItemProgress() == 1) {
            //第一行头的竖线不显示
            itemHolder.tvTopLine.setVisibility(View.INVISIBLE);
            //字体颜色加深
            itemHolder.tvAcceptTime.setTextColor(0xff555555);
            itemHolder.tvAcceptStation.setTextColor(0xff555555);
            itemHolder.tvDot.setBackgroundResource(R.drawable.timeline_dot_first);
        } else if (getItemViewType(position) == TYPE_TOP && getItemProgress() != 1){
            //第一行头的竖线不显示
            itemHolder.tvTopLine.setVisibility(View.INVISIBLE);
            //字体颜色不加深
            itemHolder.tvAcceptTime.setTextColor(0xff999999);
            itemHolder.tvAcceptStation.setTextColor(0xff999999);
            itemHolder.tvDot.setBackgroundResource(R.drawable.timeline_dot_normal);
        } else if (getItemViewType(position) == TYPE_NORMAL && getItemProgress() == 2){
            //第二行头的竖线显示
            itemHolder.tvTopLine.setVisibility(View.VISIBLE);
            //字体颜色加深
            itemHolder.tvAcceptTime.setTextColor(0xff555555);
            itemHolder.tvAcceptStation.setTextColor(0xff555555);
            itemHolder.tvDot.setBackgroundResource(R.drawable.timeline_dot_first);
        } else if (getItemViewType(position) == TYPE_NORMAL && getItemProgress() != 2){
            //第二行头的竖线显示
            itemHolder.tvTopLine.setVisibility(View.VISIBLE);
            //字体颜色不加深
            itemHolder.tvAcceptTime.setTextColor(0xff999999);
            itemHolder.tvAcceptStation.setTextColor(0xff999999);
            itemHolder.tvDot.setBackgroundResource(R.drawable.timeline_dot_normal);
        } else if (getItemViewType(position) == TYPE_THIRD && getItemProgress() == 3){
            //第三行头的竖线显示
            itemHolder.tvTopLine.setVisibility(View.VISIBLE);
            //字体颜色加深
            itemHolder.tvAcceptTime.setTextColor(0xff555555);
            itemHolder.tvAcceptStation.setTextColor(0xff555555);
            itemHolder.tvDot.setBackgroundResource(R.drawable.timeline_dot_first);
        } else if (getItemViewType(position) == TYPE_THIRD && getItemProgress() != 3){
            //第三行头的竖线显示
            itemHolder.tvTopLine.setVisibility(View.VISIBLE);
            //字体颜色不加深
            itemHolder.tvAcceptTime.setTextColor(0xff999999);
            itemHolder.tvAcceptStation.setTextColor(0xff999999);
            itemHolder.tvDot.setBackgroundResource(R.drawable.timeline_dot_normal);
        } else if (getItemViewType(position) == TYPE_FORTH && getItemProgress() == 4){
            //第四行头的竖线显示
            itemHolder.tvTopLine.setVisibility(View.VISIBLE);
            //第四行尾的竖线不显示
            itemHolder.tvBottomLine.setVisibility(View.INVISIBLE);
            //字体颜色加深
            itemHolder.tvAcceptTime.setTextColor(0xff555555);
            itemHolder.tvAcceptStation.setTextColor(0xff555555);
            itemHolder.tvDot.setBackgroundResource(R.drawable.timeline_dot_first);
        } else if (getItemViewType(position) == TYPE_FORTH && getItemProgress() != 4){
            //第四行头的竖线显示
            itemHolder.tvTopLine.setVisibility(View.VISIBLE);
            //第四行尾的竖线不显示
            itemHolder.tvBottomLine.setVisibility(View.INVISIBLE);
            //字体颜色不加深
            itemHolder.tvAcceptTime.setTextColor(0xff999999);
            itemHolder.tvAcceptStation.setTextColor(0xff999999);
            itemHolder.tvDot.setBackgroundResource(R.drawable.timeline_dot_normal);
        }

        itemHolder.bindHolder(traceList.get(position));
    }
    @Override
    public int getItemCount() {
        return traceList.size();
    }
    @Override
    public int getItemViewType(int position) {
        /*if(position == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;*/
        if(position == 0) {
            return TYPE_TOP;
        } else if (position == 1) {
            return TYPE_NORMAL;
        } else if (position == 2) {
            return TYPE_THIRD;
        } else if (position == 3) {
            return TYPE_FORTH;
        }

        return TYPE_NORMAL;
    }
    public int getItemProgress() {
        return progress = 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvAcceptTime,tvAcceptStation;
        private TextView tvTopLine,tvDot,tvBottomLine;
        private String timeUtils;
        public ViewHolder(View itemView) {
            super(itemView);
            tvAcceptTime = (TextView) itemView.findViewById(R.id.tvAcceptTime);
            tvAcceptStation = (TextView) itemView.findViewById(R.id.tvAcceptStation);
            tvTopLine = (TextView) itemView.findViewById(R.id.tvTopLine);
            tvDot = (TextView) itemView.findViewById(R.id.tvDot);
            tvBottomLine = (TextView)itemView.findViewById(R.id.tvBottomLine);
        }

        public void bindHolder(Trace trace) {
            tvAcceptTime.setText(trace.getAcceptTime());
            tvAcceptStation.setText(trace.getAcceptStation());
        }

    }
}