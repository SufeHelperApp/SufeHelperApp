package com.example.sufehelperapp;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import java.util.List;

import me.grantland.widget.AutofitHelper;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    private Context mContext;
    private List<task> mTaskList;
    private user user;
    private int num;

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView launcherImageView;
        TextView launcherNameView;
        TextView launcherPhoneNumberView;
        TextView launcherSubtaskTypeView;
        TextView launcherLocationView;
        TextView launcherDeadlineView;
        TextView launcherPaymentView;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            launcherImageView = (ImageView) view.findViewById(R.id.launcher_image);
            launcherNameView = (TextView) view.findViewById(R.id.launcher_name);
            launcherPhoneNumberView = (TextView) view.findViewById(R.id.launcher_phoneNumber);
            launcherSubtaskTypeView = (TextView) view.findViewById(R.id.launcher_subtaskType);
            launcherLocationView = (TextView) view.findViewById(R.id.launcher_location);
            launcherDeadlineView = (TextView) view.findViewById(R.id.launcher_deadline);
            launcherPaymentView = (TextView) view.findViewById(R.id.launcher_payment);

        }
    }

    public TaskAdapter(List<task> taskList , user user1, int i ){
        mTaskList = taskList;
        user = user1; //接收user
        num = i;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.task_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                task task = mTaskList.get(position);
                if(num == 1) {
                    Intent intent = new Intent(mContext, Task_InfoActivity.class);
                    intent.putExtra(Task_InfoActivity.TASK_SELECTED, task);
                    intent.putExtra(Task_InfoActivity.USER_NOW, user);//传送user
                    mContext.startActivity(intent);
                }else if (num == 2){
                    Intent intent = new Intent(mContext, MyActivity_Task_Details.class);
                    intent.putExtra(Task_InfoActivity.TASK_SELECTED, task);
                    intent.putExtra(Task_InfoActivity.USER_NOW, user);//传送user
                    mContext.startActivity(intent);
                }

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        task task = mTaskList.get(position);
        holder.launcherNameView.setText(task.getLauncherName());
        Glide.with(mContext).load(task.getLauncherImageId()).into(holder.launcherImageView);
        holder.launcherPhoneNumberView.setText(task.getLauncherPhoneNumber());
        holder.launcherSubtaskTypeView.setText(task.getSubtaskType());
        holder.launcherLocationView.setText(task.getLocation());
        holder.launcherDeadlineView.setText(task.getDdl());
        String paymentString = Double.toString(task.getPayment());
        holder.launcherPaymentView.setText(paymentString);
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

}
