package com.example.project_management_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * The TaskAdapter class reformats the tasks from the database and reads them
 * into the recyclerView
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {
    private static final String TAG = "DatabaseDownload";

    Context context;
    ArrayList<Task> task;
    public TaskAdapter(Context c, ArrayList<Task> t){
        Log.d(TAG, "TaskAdapter() constructor called");
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

    // get the number of tasks
    @Override
    public int getItemCount() {
        return task.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
       TextView taskName, taskDescription, taskPriority, taskProgress, taskDueDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = (TextView) itemView.findViewById(R.id.newTaskName);
            taskDescription = (TextView) itemView.findViewById(R.id.newTaskDescription);
            taskPriority = (TextView) itemView.findViewById(R.id.newTaskPriority);
            taskProgress = (TextView) itemView.findViewById(R.id.taskProgress);
            taskDueDate = (TextView) itemView.findViewById(R.id.newTaskDueDate);
        }
    }
}
