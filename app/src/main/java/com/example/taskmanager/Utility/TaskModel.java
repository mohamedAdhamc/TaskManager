package com.example.taskmanager.Utility;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity (tableName = "task_table")
public class TaskModel implements Serializable {
    @PrimaryKey( autoGenerate = true)
    private int id;
    @ColumnInfo (name = "status")
    private int status;
    @ColumnInfo (name = "title")
    private String title;
    @ColumnInfo (name = "desc")
    private String description;
    @ColumnInfo (name = "priority")
    private String currentPriority;
    @ColumnInfo (name = "date")
    private String currentDate;
    @ColumnInfo (name = "time")
    private String currentTime;
    @ColumnInfo (name = "editable")
    private Boolean editable;

    public TaskModel()
    {

    }
    public TaskModel(int status, String title, String description,
                     String currentPriority, String currentDate, String currentTime, Boolean editable) {
        this.status = status;
        this.title = title;
        this.description = description;
        this.currentPriority = currentPriority;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.editable = editable;
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

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }
}
