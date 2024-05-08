package com.example.taskmanager.ui;

import java.io.Serializable;

public class TaskModel implements Serializable {
    private int id, status;
    private String title;
    private String description;
    private String currentPriority;
    private String currentDate;
    private String currentTime;

    public TaskModel()
    {

    }
    public TaskModel(int id, int status, String title, String description, String currentPriority, String currentDate, String currentTime) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.description = description;
        this.currentPriority = currentPriority;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrentPriority() {
        return currentPriority;
    }

    public void setCurrentPriority(String currentPriority) {
        this.currentPriority = currentPriority;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
