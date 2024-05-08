package com.example.taskmanager.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.taskmanager.Utility.TaskModel;

@Database(entities = {TaskModel.class}, version = 1)
public abstract class RoomDB extends RoomDatabase {
    private static volatile RoomDB INSTANCE;
    public abstract TaskDAO taskDAO ();

    public static RoomDB getInstance(Context context){
        if (INSTANCE == null){
            synchronized (RoomDB.class) {
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, "TASK_DB").build();
                }
            }
        }
        return INSTANCE;
    }

    public void insertTask(TaskModel task){

    }
}
