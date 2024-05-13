package com.example.taskmanager.service;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleOwner;

import com.example.taskmanager.Database.RoomDB;
import com.example.taskmanager.Utility.TaskModel;

import java.util.List;

public class ForegroundService extends Service {

    private static final String CHANNEL_ID = "notification_channel";

    private RoomDB instance;

    private static List<TaskModel> tasks;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = RoomDB.getInstance(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input="";
        createNotificationChannel();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("BingeBetter")
                .setContentText(input)
                .setOngoing(true)
                .build();
        startForeground(1, notification);
        timer.start();
        instance.taskDAO().getAllOngoingTasks().observeForever( ForegroundService::updateTasks);
        return START_STICKY;
    }

    private static void updateTasks(List<TaskModel> taskModels) {
        Log.wtf("foreground", "update tasks called");
        tasks = taskModels;
    }

    public static void startService (Context context) {
        Intent i = new Intent(context, ForegroundService.class);
        context.startForegroundService(i);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    protected CountDownTimer timer = new CountDownTimer(2000, 2000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            //generate the notifications
            Log.wtf("notificationz", "test");
            for(TaskModel task: tasks){
                if (task.getCurrentPriority().contains("Low")){
                    //check deadline if multiple of 24 hours remaining send a notification

                } else if (task.getCurrentPriority().contains("Medium")){
                    //check deadline if multiple of 5 hours remaining send a notification

                } else if (task.getCurrentPriority().contains("High")) {
                    //check deadline if multiple of 1 hours remaining send a notification
                }
            }

            start();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void createNotificationChannel(){
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Notification Channel", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }
}