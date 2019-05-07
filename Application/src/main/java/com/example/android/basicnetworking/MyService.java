package com.example.android.basicnetworking;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class MyService extends Service {

    static boolean isOn;

    static boolean dialogOnScreen = false;

    @Override
    public void onRebind(Intent intent) {




        MyReceiver broadCastReceiver = new MyReceiver();

        //https://developer.android.com/guide/components/broadcasts
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(broadCastReceiver, filter);

        //https://stackoverflow.com/questions/47531742/startforeground-fail-after-upgrade-to-android-8-1
        IntentFilter screenStateFilter = new IntentFilter();
        screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(broadCastReceiver, screenStateFilter);

        super.onRebind(intent);
    }

    @Override
    public void onCreate() {


        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        //https://stackoverflow.com/questions/47531742/startforeground-fail-after-upgrade-to-android-8-1

        String NOTIFICATION_CHANNEL_ID = "com.example.android";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setContentIntent(pendingIntent)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);


        super.onCreate();
    }

    @Override
    public void onDestroy() {

        if (MainActivity.isOn == true | isOn == true ) {
            Intent i = new Intent(getBaseContext(), MyService.class);
            ContextCompat.startForegroundService(this, i);
            MainActivity.isOn = true;
            isOn = true;
        }
        else if (MainActivity.isOn == false | isOn == false) {

            MainActivity.isOn = false;
            isOn = false;
            stopForeground(true); //true will remove notification
            super.onDestroy();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful


        if (dialogOnScreen == false) {
            MyReceiver broadCastReceiver = new MyReceiver();

            //https://developer.android.com/guide/components/broadcasts
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            this.registerReceiver(broadCastReceiver, filter);

            //https://stackoverflow.com/questions/47531742/startforeground-fail-after-upgrade-to-android-8-1
            IntentFilter screenStateFilter = new IntentFilter();
            screenStateFilter.addAction(Intent.ACTION_USER_PRESENT);
            registerReceiver(broadCastReceiver, screenStateFilter);

        }
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation



        return null;
    }
}
