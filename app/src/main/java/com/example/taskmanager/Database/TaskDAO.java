package com.example.taskmanager.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.taskmanager.Utility.TaskModel;
import java.util.List;

@Dao
public interface TaskDAO {
    @Insert
    void insertTask(TaskModel task);

    @Delete
    void deleteTask(TaskModel task);

    @Update
    void updateTask(TaskModel task);

    @Query("SELECT * FROM task_table")
    LiveData<List<TaskModel>> getAllTasks();

    @Query("SELECT * FROM task_table WHERE id LIKE :taskId")
    TaskModel findTaskByIrd(int taskId);

    @Query("SELECT * FROM task_table WHERE status LIKE 0")
    LiveData<List<TaskModel>> getAllOngoingTasks();

    @Query("SELECT * FROM task_table WHERE status LIKE 1")
    LiveData<List<TaskModel>> getAllCompletedTasks();
}
