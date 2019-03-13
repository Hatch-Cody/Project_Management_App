package com.example.project_management_app;

public class TaskItems {
    private String TaskName;
    private int priority;
    private Boolean isComplete;
    private int progress;
    private String description;
    private String comment;
    private String dueDate;
    private String assignDate;
    private String taskId;

    public String  getTaskName() {
        return TaskName;
    }
    public void    setTaskName(String taskName) {
        TaskName = taskName;
    }
    public int     getPriority() {
        return priority;
    }
    public void    setPriority(int priority) {
        this.priority = priority;
    }
    public String  getDescription() {
        return description;
    }
    public void    setDescription(String description) {
        this.description = description;
    }
    public String  getComment() {
        return comment;
    }
    public void    setComment(String comment) {
        this.comment = comment;
    }
    public String  getTaskId() {
        return taskId;
    }
    public void    setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public Boolean getComplete() {
        return isComplete;
    }
    public void    setComplete(Boolean complete) {
        isComplete = complete;
    }
    public int     getProgress() {
        return progress;
    }
    public void    setProgress(int progress) {
        this.progress = progress;
    }
    public String  getDueDate() {
        return dueDate;
    }
    public void    setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    public String  getAssignDate() {
        return assignDate;
    }
    public void    setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }
}
