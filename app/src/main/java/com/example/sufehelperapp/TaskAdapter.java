package com.example.sufehelperapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView launcherImage;
        TextView launcherName;
        TextView launcherPhoneNumber;
        TextView taskType;
        TextView location;
        TextView deadline;
        TextView payment;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            launcherImage = (ImageView) view.findViewById(R.id.launcher_image);
            launcherName = (TextView) view.findViewById(R.id.launcher_name);
            launcherPhoneNumber = (TextView) view.findViewById(R.id.launcher_phoneNumber);
            taskType = (TextView) view.findViewById(R.id.taskType);
            location = (TextView) view.findViewById(R.id.location);
            deadline = (TextView) view.findViewById(R.id.deadline);
            payment = (TextView) view.findViewById(R.id.payment);

        }
    }

    public TaskAdapter(List<task> taskList){
        mTaskList = taskList;
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
                Intent intent = new Intent(mContext, TaskInfoActivity.class);
                intent.putExtra(TaskInfoActivity.TASK_SELECTED, task);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        task task = mTaskList.get(position);
        holder.launcherName.setText(task.getLauncherName());
        Glide.with(mContext).load(task.getImageId()).into(holder.launcherImage);
        holder.launcherPhoneNumber.setText(task.getLauncherPhoneNumber());
        holder.taskType.setText(task.getTaskType());
        holder.location.setText(task.getLocation());
        holder.deadline.setText(task.getDeadline());
        holder.payment.setText(task.getPayment());
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

}
