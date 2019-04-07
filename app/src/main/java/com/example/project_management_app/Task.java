package com.example.project_management_app;

/**
 * Contains the attributes of a task as well as the associated getters, setters, and constructors
 */
public class Task {
    private String  TaskName;
    private String  priority;
    private String  assignedTo;
    private String  description;
    private String  dueDate;
    private String  assignDate;
    private String  taskId;
    private Boolean isComplete;
    //private int progress;
    //private String comment;

    public Task() {
    }

    /**
     * Creates a task with the following parameters.
     * @param taskName Name of the task.
     * @param priority Level of task importance 1 - 5.
     * @param isComplete Returns true if task is completed.
     * @param description of the task.
     * @param dueDate of the task.
     * @param assignDate Date the task was assigned.
     * @param taskId Unique ID of each task.
     */
    public Task(String taskName, String priority, Boolean isComplete, String assignedTo, String description, String dueDate, String assignDate, String taskId) {
        TaskName =         taskName;
        this.priority =    priority;
        this.isComplete =  isComplete;
        this.assignedTo =    assignedTo;
        this.description = description;
        this.dueDate =     dueDate;
        this.assignDate =  assignDate;
        this.taskId =      taskId;
        //this.progress =  progress;
        //this.comment =   comment;
    }

    public String getTaskName() {
        return TaskName;
    }
    public void  setTaskName(String taskName) {
        TaskName = taskName;
    }

    public String getPriority() {
        return priority;
    }
    public void   setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }
    public void   setDescription(String description) {
        this.description = description;
    }

    public String getTaskId() {
        return taskId;
    }
    public void   setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Boolean getComplete() {
        return isComplete;
    }
    public void   setComplete(Boolean complete) {
        isComplete = complete;
    }

    public String getDueDate() {
        return dueDate;
    }
    public void   setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssignDate() {
        return assignDate;
    }
    public void   setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }

    public String getAssignedTo() {
        return assignedTo;
    }
    public void   setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    /*
    public String getComment() { return comment; }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getProgress() {
        return progress;
    }
    public void setProgress(int progress) {
        this.progress = progress;
    }
    */
}
