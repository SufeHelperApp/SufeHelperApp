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


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    private Context mContext;
    private List<task> mTaskList;
    private user user;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView launcherImage;
        TextView launcherName;
        TextView launcherPhoneNumber;
        TextView subtaskType;
        TextView location;
        TextView deadline;
        TextView payment;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            launcherImage = (ImageView) view.findViewById(R.id.launcher_image);
            launcherName = (TextView) view.findViewById(R.id.launcher_name);
            launcherPhoneNumber = (TextView) view.findViewById(R.id.launcher_phoneNumber);
            subtaskType = (TextView) view.findViewById(R.id.subtaskType);
            location = (TextView) view.findViewById(R.id.location);
            deadline = (TextView) view.findViewById(R.id.deadline);
            payment = (TextView) view.findViewById(R.id.payment);

        }
    }

    public TaskAdapter(List<task> taskList , user user1){
        mTaskList = taskList;
        user = user1; //接收user
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
                Intent intent = new Intent(mContext, Task_InfoActivity.class);
                intent.putExtra(Task_InfoActivity.TASK_SELECTED, task);
                intent.putExtra(Task_InfoActivity.USER_NOW, user);//传送user
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        task task = mTaskList.get(position);
        holder.launcherName.setText(task.getLauncherName());
        Glide.with(mContext).load(task.getLauncherImageId()).into(holder.launcherImage);
        holder.launcherPhoneNumber.setText(task.getLauncherPhoneNumber());
        holder.subtaskType.setText(task.getSubtaskType());
        holder.location.setText(task.getLocation());
        holder.deadline.setText(task.getDdl());
        String paymentString = Double.toString(task.getPayment());
        holder.payment.setText(paymentString);
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

}
