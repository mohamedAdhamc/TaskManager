package com.example.taskmanager.service;

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

public class ForegroundService extends Service {

    private static final String CHANNEL_ID = "notification_channel";

    @Override
    public void onCreate() {
        super.onCreate();
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
        return START_STICKY;
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