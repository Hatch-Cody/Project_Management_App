package com.example.project_management_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    Context context;
    ArrayList<Task> task;
    public TaskAdapter(Context c, ArrayList<Task> t){
        context = c;
        task = t;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.taskview,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.taskName.setText(task.get(position).getTaskName());
        holder.taskDescription.setText(task.get(position).getDescription());
        holder.taskProgress.setText(task.get(position).getProgress());
        holder.taskDueDate.setText(task.get(position).getDueDate());

    }

    @Override
    public int getItemCount() {
        return task.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

       TextView taskName, taskDescription, taskPriority, taskProgress, taskDueDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = (TextView) itemView.findViewById(R.id.taskName);
            taskDescription = (TextView) itemView.findViewById(R.id.taskDescription);
            taskPriority = (TextView) itemView.findViewById(R.id.taskPriority);
            taskProgress = (TextView) itemView.findViewById(R.id.taskProgress);
            taskDueDate = (TextView) itemView.findViewById(R.id.taskDueDate);
        }
    }

}
