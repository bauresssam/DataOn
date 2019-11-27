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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.android.common.logger.Log;

import java.util.ArrayList;

class Network {

    public ArrayList<String> checkNetworkConnection(Context context) {

        ArrayList<String> networkState = new ArrayList<>();

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
                networkState.add(context.getString(R.string.wifi_connection));
                //return context.getString(R.string.wifi_connection);
            } else if (mobileConnected) {
                Log.i(TAG, context.getString(R.string.mobile_connection));
                networkState.add(context.getString(R.string.mobile_connection));
                //return context.getString(R.string.mobile_connection);
            } else if (!wifiConnected) {
                Log.i(TAG, context.getString(R.string.no_wifi_connection));
                networkState.add(context.getString(R.string.no_wifi_connection));
                //return context.getString(R.string.no_wifi_connection);
            } else if (!mobileConnected) {
                Log.i(TAG, context.getString(R.string.no_mobile_connection));
                networkState.add(context.getString(R.string.no_mobile_connection));
                //return context.getString(R.string.no_mobile_connection);
            } else if (!mobileConnected && !wifiConnected) {
                Log.i(TAG, context.getString(R.string.no_wifi_or_mobile));
                networkState.add(context.getString(R.string.no_wifi_or_mobile));
                //return context.getString(R.string.no_wifi_or_mobile);

            }
        }
        //return context.getString(R.string.no_wifi_or_mobile);
        return networkState;
    }

    }
