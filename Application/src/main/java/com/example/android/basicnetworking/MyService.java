//
//                Copyright 2019 Samuel Arthur Bauress
//
//                Licensed under the Apache License, Version 2.0 (the "License");
//                you may not use this file except in compliance with the License.
//                You may obtain a copy of the License at
//
//                http://www.apache.org/licenses/LICENSE-2.0
//
//                Unless required by applicable law or agreed to in writing, software
//                distributed under the License is distributed on an "AS IS" BASIS,
//                WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//                See the License for the specific language governing permissions and
//                limitations under the License.
//
//

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
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    static boolean isOn;

    //TODO: getters and setters
    static boolean dialogOnScreen = false;


    @Override
    public void onRebind(Intent intent) {

super.onRebind(intent);


//        MyReceiver broadCastReceiver = new MyReceiver();
//
//        //https://developer.android.com/guide/components/broadcasts
//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//        registerReceiver(broadCastReceiver, filter);
//
//        //https://stackoverflow.com/questions/47531742/startforeground-fail-after-upgrade-to-android-8-1
//        IntentFilter screenStateFilter = new IntentFilter();
//        screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
//        registerReceiver(broadCastReceiver, screenStateFilter);

    }

    @Override
    public void onCreate() {
        super.onCreate();

//        notification opens app on press
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        //display notification to show service is running
        //https://stackoverflow.com/questions/47531742/startforeground-fail-after-upgrade-to-android-8-1

        String NOTIFICATION_CHANNEL_ID = "com.example.android";
        String channelName = "My Background Service";

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("App is running in background")
                .setContentText("Press to open app")
                .setContentIntent(pendingIntent)
                .setCategory(Notification.CATEGORY_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            notificationBuilder.setPriority(NotificationManager.IMPORTANCE_MAX);
        }

        Notification notification = notificationBuilder.build();
        startForeground(2, notification);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(2, notification);

        NotificationChannel chan = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(chan);
        }


    }


    @Override
    public void onDestroy() {

//        if (MainActivity.isOn | isOn) {
//            Intent i = new Intent(getBaseContext(), MyService.class);
//            ContextCompat.startForegroundService(this, i);
//            MainActivity.isOn = true;
//            isOn = true;
//        }
//        else if (!MainActivity.isOn | !isOn) {
//
//            MainActivity.isOn = false;
//            isOn = false;
//
//        }


        stopForeground(true); //true will remove notification
        super.onDestroy();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//TODO: add to the wifi state change to turn wifi of if change this may need new receiver needs to check if wifi has just been disconnected!!!!
        //TODO: change
//        if ( !MyService.dialogOnScreen) {

            ActivityDialog.CloseDialogReceiver closeDialogReceiver = new ActivityDialog.CloseDialogReceiver();
            MyReceiver broadCastReceiver = new MyReceiver();

            //close dialog by calling receiver in ActivityDialog witch in turn calls .dismiss ActivityDialog is closed by
            // TurnWifiOn method witch closes .finish
//        https://stackoverflow.com/questions/5888502/how-to-detect-when-wifi-connection-has-been-established-in-android
            IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
            registerReceiver(closeDialogReceiver, intentFilter);

            // calls MyReceiver - when network state changes - witch in turn launches TurnWifiOn (dialog:custom) if appropriate.
            //https://developer.android.com/guide/components/broadcasts
            IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(broadCastReceiver, filter);

            // calls MyReceiver - when user is presented changes - witch in turn launches TurnWifiOn (dialog:custom) if appropriate.
            //https://stackoverflow.com/questions/47531742/startforeground-fail-after-upgrade-to-android-8-1
            IntentFilter screenStateFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
            screenStateFilter.addAction(Intent.ACTION_USER_PRESENT);
            registerReceiver(broadCastReceiver, screenStateFilter);
        //}
        //TODO: change

        return Service.START_STICKY;
    }



    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation



        return null;
    }
}
