package com.example.project_management_app;

/**
 * Contains the attributes of a task
 */
public class Task {
    private String TaskName;
    private int priority;
    private Boolean isComplete;
    private int progress;
    private String description;
    private String comment;
    private String dueDate;
    private String assignDate;
    private String taskId;

    public Task() {
    }

    /**
     * Creates a task with the following parameters
     * @param taskName
     * @param priority The level of importance for the task between 1 - 5.
     * @param isComplete Returns true if the task has been completed.
     * @param progress The level of progress for the task from 1 - 5.
     * @param description
     * @param comment
     * @param dueDate
     * @param assignDate
     * @param taskId
     */
    public Task(String taskName, int priority, Boolean isComplete, int progress, String description, String comment, String dueDate, String assignDate, String taskId) {
        TaskName = taskName;
        this.priority = priority;
        this.isComplete = isComplete;
        this.progress = progress;
        this.description = description;
        this.comment = comment;
        this.dueDate = dueDate;
        this.assignDate = assignDate;
        this.taskId = taskId;
    }

    public String getTaskName() {
        return TaskName;
    }
    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() { return comment; }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Boolean getComplete() {
        return isComplete;
    }
    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public int getProgress() {
        return progress;
    }
    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getDueDate() {
        return dueDate;
    }
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssignDate() {
        return assignDate;
    }
    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }
}
