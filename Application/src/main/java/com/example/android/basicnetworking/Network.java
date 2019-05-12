package com.example.android.basicnetworking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.android.common.logger.Log;

class Network {

    public String checkNetworkConnection(Context context) {
        final String TAG = "Basic Network Demo";
        // Whether there is a Wi-Fi connection.
        boolean wifiConnected = false;
        // Whether there is a mobile connection.
        boolean mobileConnected = false;

        // BEGIN_INCLUDE(connect)
        ConnectivityManager connMgr =
                (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected) {
                Log.i(TAG, context.getString(R.string.wifi_connection));
                return context.getString(R.string.wifi_connection);

            } else if (mobileConnected) {
                Log.i(TAG, context.getString(R.string.mobile_connection));
                return context.getString(R.string.mobile_connection);
            }
        } else if (!wifiConnected) {
            Log.i(TAG, context.getString(R.string.no_wifi_connection));
            return context.getString(R.string.no_wifi_connection);

        } else if (!mobileConnected) {
            Log.i(TAG, context.getString(R.string.no_mobile_connection));
            return context.getString(R.string.no_mobile_connection);
        } else if (!mobileConnected && !wifiConnected){
                Log.i(TAG, context.getString(R.string.no_wifi_or_mobile));
                return context.getString(R.string.no_wifi_or_mobile);

            }
            return null;
        }
    }
