package com.example.myapplication4;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    MyNotifyCationReciver myNotifyCationReciver;
    NotificationManager notifyManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.start_service).setOnClickListener(this);
        registerReceiver(myNotifyCationReciver = new MyNotifyCationReciver(), new IntentFilter("test_brocast"));
    }

    @Override
    public void onClick(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                showNotifyCation();
            }
        }).start();
//        showMyNotifyCation();
    }

    private void showMyNotifyCation() {
        notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        RemoteViews remoteviews = new RemoteViews(getPackageName(), R.layout.notifycation_ll);
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setContent(remoteviews)
                .setSmallIcon(R.mipmap.notificationpic);
    }

    private void showNotifyCation() {
        notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, new Intent("test_brocast"), 0);
        final NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.notificationpic)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle("hei hei hei")
                .setContentText("here first notifycation")
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .setDefaults(Notification.DEFAULT_ALL);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    mBuilder.setVisibility(Notification.VISIBILITY_PUBLIC);
                    mBuilder.setFullScreenIntent(pIntent, false);
                }
//                .setStyle(new NotificationCompat.BigTextStyle().bigText("6666666666"))
//                .addAction(R.mipmap.notificationpic, "OK", pIntent);
        notifyManager.notify(1001, mBuilder.build());

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 100; i++) {
//                    mBuilder.setProgress(100, i, false);
//                    notifyManager.notify(1001, mBuilder.build());
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                mBuilder.setContentText("download complete");
//                mBuilder.setProgress(0, 0, true);
//                notifyManager.notify(1001, mBuilder.build());
//            }
//        }).start();
    }

    public class MyNotifyCationReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            notifyManager.cancel(1001
            );
            System.out.println("I run ...");
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(myNotifyCationReciver);
        super.onDestroy();
    }
}
