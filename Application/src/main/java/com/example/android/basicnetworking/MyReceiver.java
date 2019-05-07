package com.example.android.basicnetworking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.example.android.common.logger.Log;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static com.example.android.basicnetworking.MainActivity.TAG;
import static com.example.android.basicnetworking.MainActivity.mobileConnected;
import static com.example.android.basicnetworking.MainActivity.wifiConnected;

public class MyReceiver extends BroadcastReceiver {




    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.


            // BEGIN_INCLUDE(connect)
            final ConnectivityManager connMgr =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
            if (activeInfo != null && activeInfo.isConnected()) {
                wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
                mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
                if(wifiConnected) {
                    Log.i(TAG, context.getString(R.string.wifi_connection));
                } else if (mobileConnected && MyService.dialogOnScreen == false){


                    Intent in = new Intent(context, ActivityDialog.class);
                        in.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(in);

/*
                    Toast toast = Toast.makeText(context,  context.getString(R.string.mobile_connection),Toast.LENGTH_LONG);
                    toast.show();
                    Log.i(TAG, context.getString(R.string.mobile_connection));
*/

                }
            } else {
                Log.i(TAG, context.getString(R.string.no_wifi_or_mobile));
            }
            // END_INCLUDE(connect)


        /*
        boolean dataOn;




        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        dataOn = (netInfo != null && netInfo.isConnected());


        Toast toast = Toast.makeText(context, Boolean.toString(dataOn),Toast.LENGTH_LONG);
        toast.show();

         */

    }

}
