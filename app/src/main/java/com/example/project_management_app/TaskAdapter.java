package com.example.project_management_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.taskview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.newTaskName.setText(tasksList.get(i).getTaskName());
        viewHolder.newTaskPriority.setText(tasksList.get(i).getPriority());
        viewHolder.newTaskAssignTo.setText(tasksList.get(i).getAssignTo());
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

        public EditText newTaskName,
                newTaskPriority,
                newTaskAssignTo,
                newTaskAssignDate,
                newTaskDescription,
                newTaskDueDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            newTaskName = (EditText) mView.findViewById(R.id.newTaskName);
            newTaskPriority = mView.findViewById(R.id.newTaskPriority);
            newTaskAssignTo = (EditText) mView.findViewById(R.id.newTaskAssignTo);
            newTaskAssignDate = (EditText) mView.findViewById(R.id.newTaskAssignDate);
            newTaskDescription = (EditText) mView.findViewById(R.id.newTaskDescription);
            newTaskDueDate = (EditText) mView.findViewById(R.id.newTaskDueDate);
        }
    }
}
