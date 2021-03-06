package com.example.listatodo.taskDataModel;

public class TaskData {
    private Integer id;
    private String taskTitle;
    private String taskDescription;
    private Long taskStart;
    private Long taskEnd;
    private TaskStatus taskStatus;
    private Boolean haveNotification;
    private TaskCategory taskCategory;
    private byte[] attachmentByte;
    private String attachment;

    public TaskData(Integer id, String taskTitle, String taskDescription, Long taskStart, Long taskEnd, TaskStatus taskStatus, Boolean haveNotification, TaskCategory taskCategory, byte[] attachmentByte) {
        this.id = id;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskStart = taskStart;
        this.taskEnd = taskEnd;
        this.taskStatus = taskStatus;
        this.haveNotification = haveNotification;
        this.taskCategory = taskCategory;
        this.attachmentByte = attachmentByte;
    }

    public TaskData(Integer id, String taskTitle, String taskDescription, Long taskStart, Long taskEnd, TaskStatus taskStatus, Boolean haveNotification, TaskCategory taskCategory, byte[] attachmentByte, String attachment) {
        this.id = id;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskStart = taskStart;
        this.taskEnd = taskEnd;
        this.taskStatus = taskStatus;
        this.haveNotification = haveNotification;
        this.taskCategory = taskCategory;
        this.attachmentByte = attachmentByte;
        this.attachment = attachment;
    }

    public TaskData(String taskTitle, String taskDescription, Long taskStart, Long taskEnd, TaskStatus taskStatus, Boolean haveNotification, TaskCategory taskCategory, byte[] attachmentByte) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskStart = taskStart;
        this.taskEnd = taskEnd;
        this.taskStatus = taskStatus;
        this.haveNotification = haveNotification;
        this.taskCategory = taskCategory;
        this.attachmentByte = attachmentByte;
    }

    public TaskData(String taskTitle, String taskDescription, Long taskStart, Long taskEnd, TaskStatus taskStatus, Boolean haveNotification, TaskCategory taskCategory, byte[] attachmentByte, String attachment) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskStart = taskStart;
        this.taskEnd = taskEnd;
        this.taskStatus = taskStatus;
        this.haveNotification = haveNotification;
        this.taskCategory = taskCategory;
        this.attachmentByte = attachmentByte;
        this.attachment = attachment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Long getTaskStart() {
        return taskStart;
    }

    public void setTaskStart(Long taskStart) {
        this.taskStart = taskStart;
    }

    public Long getTaskEnd() {
        return taskEnd;
    }

    public void setTaskEnd(Long taskEnd) {
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

    public byte[] getAttachmentByte() {
        return attachmentByte;
    }

    public void setAttachmentByte(byte[] attachmentByte) {
        this.attachmentByte = attachmentByte;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
