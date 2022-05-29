package com.example.listatodo.taskData;

import java.util.Date;

public class TaskData {
    private String taskTitle;
    private String taskDescription;
    private Date taskStart;
    private Date taskEnd;
    private TaskStatus taskStatus;
    private Boolean haveNotification;
    private TaskCategory taskCategory;
    private Boolean haveAttachment;
    private Object attachment;

    public TaskData(String taskTitle, String taskDescription, Date taskStart, Date taskEnd, TaskStatus taskStatus, Boolean haveNotification, TaskCategory taskCategory, Boolean haveAttachment) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskStart = taskStart;
        this.taskEnd = taskEnd;
        this.taskStatus = taskStatus;
        this.haveNotification = haveNotification;
        this.taskCategory = taskCategory;
        this.haveAttachment = haveAttachment;
    }

    public TaskData(String taskTitle, String taskDescription, Date taskStart, Date taskEnd, TaskStatus taskStatus, Boolean haveNotification, TaskCategory taskCategory, Boolean haveAttachment, Object attachment) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskStart = taskStart;
        this.taskEnd = taskEnd;
        this.taskStatus = taskStatus;
        this.haveNotification = haveNotification;
        this.taskCategory = taskCategory;
        this.haveAttachment = haveAttachment;
        this.attachment = attachment;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Date getTaskStart() {
        return taskStart;
    }

    public void setTaskStart(Date taskStart) {
        this.taskStart = taskStart;
    }

    public Date getTaskEnd() {
        return taskEnd;
    }

    public void setTaskEnd(Date taskEnd) {
        this.taskEnd = taskEnd;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Boolean getHaveNotification() {
        return haveNotification;
    }

    public void setHaveNotification(Boolean haveNotification) {
        this.haveNotification = haveNotification;
    }

    public TaskCategory getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(TaskCategory taskCategory) {
        this.taskCategory = taskCategory;
    }

    public Boolean getHaveAttachment() {
        return haveAttachment;
    }

    public void setHaveAttachment(Boolean haveAttachment) {
        this.haveAttachment = haveAttachment;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }
}
