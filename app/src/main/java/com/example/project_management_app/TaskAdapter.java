package com.example.project_management_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * The TaskAdapter class reformats the tasks from the database and reads them
 * into the recyclerView
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private static final String TAG = "DatabaseDownload";

    public List<Task> tasksList;

    public TaskAdapter(List<Task> tasksList) {
        this.tasksList = tasksList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "Inside onCreateViewHolder of TaskAdapter");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.taskview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.newTaskName.setText(tasksList.get(i).getTaskName());
        viewHolder.newTaskPriority.setText(tasksList.get(i).getPriority());
        viewHolder.newTaskAssignedTo.setText(tasksList.get(i).getAssignedTo());
        viewHolder.newTaskAssignDate.setText(tasksList.get(i).getAssignDate());
        viewHolder.newTaskDescription.setText(tasksList.get(i).getDescription());
        viewHolder.newTaskDueDate.setText(tasksList.get(i).getDueDate());
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public TextView newTaskName,
                        newTaskPriority,
                        newTaskAssignedTo,
                        newTaskAssignDate,
                        newTaskDescription,
                        newTaskDueDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            newTaskName        = mView.findViewById(R.id.taskName);
            newTaskPriority    = mView.findViewById(R.id.taskPriority);
            newTaskAssignedTo    = mView.findViewById(R.id.taskAssignedTo);
            newTaskAssignDate  = mView.findViewById(R.id.taskAssignDate);
            newTaskDescription = mView.findViewById(R.id.taskDescription);
            newTaskDueDate     = mView.findViewById(R.id.taskDueDate);
        }
    }
}
