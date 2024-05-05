package com.example.taskmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.taskmanager.R;

public class MainActivity extends AppCompatActivity {
    static int x = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createNewTask(View v){
        Intent i = new Intent(this, CreateTaskActivity.class);
        startActivity(i);
    }
    //Delete
    public void goToView_Modify(View v){
        Intent i = new Intent(this, ModifyTaskActivity.class);
        //
        String taskName = "Planning";
        String date = "20-2-2030";
        String description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s," +
                " when an unknown printer took a galley of type and scrambled it to make a type specimen book." +
                " It has survived not only five centuries, but also the leap into electronic typesetting," +
                " remaining essentially unchanged." +
                " It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages," +
                " and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
        String activityType;// View , modify
        if(x%2==0){
            activityType = "View";
        }
        else{
            activityType = "Modify";
        }
        x++;

        int priorityIndex = 1;
        String time = "20:5 AM";
        //
        i.putExtra("name",taskName);
        i.putExtra("date",date);
        i.putExtra("priority",String.valueOf(priorityIndex).trim());
        i.putExtra("activityType",activityType);
        i.putExtra("description",description);
        i.putExtra("time",time);
        startActivity(i);
    }
}