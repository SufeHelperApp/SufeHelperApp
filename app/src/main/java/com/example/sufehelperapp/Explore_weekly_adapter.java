package com.example.sufehelperapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Explore_weekly_adapter extends RecyclerView.Adapter<Explore_weekly_adapter.ViewHolder> {
    private Context mContext;
    private List<Explore_weekly_interal> mInteralList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView interalImage;
        TextView interalName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            interalImage = (ImageView) view.findViewById(R.id.release_amount);
            interalName = (TextView) view.findViewById(R.id.interal_name);
        }
    }

    public Explore_weekly_adapter(List<Explore_weekly_interal> interalList) {
        mInteralList = interalList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_explore_weekly_interal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Explore_weekly_interal explore_weekly_interal = mInteralList.get(position);
        holder.interalName.setText(explore_weekly_interal.getName());
        Glide.with(mContext).load(explore_weekly_interal.getImageID()).into(holder.interalImage);
    }

    @Override
    public int getItemCount() {
        return mInteralList.size();
    }
}
