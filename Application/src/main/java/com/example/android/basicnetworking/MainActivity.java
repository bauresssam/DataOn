/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.basicnetworking;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.android.common.logger.Log;
import com.example.android.common.logger.LogFragment;
import com.example.android.common.logger.LogWrapper;
import com.example.android.common.logger.MessageOnlyLogFilter;

import java.util.ArrayList;


/**
 * Sample application demonstrating how to test whether a device is connected,
 * and if so, whether the connection happens to be wifi or mobile (it could be
 * something else).
 *
 * This sample uses the logging framework to display log output in the log
 * fragment (LogFragment).
 */
public class MainActivity extends FragmentActivity {



    // Reference to the fragment showing events, so we can clear it with a button
    // as necessary.
    private LogFragment mLogFragment;

    protected Intent i;
    public static boolean isOn;
    Network network = new Network();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_main);

        // Initialize text fragment that displays intro text.
        SimpleTextFragment introFragment = (SimpleTextFragment)
                    getSupportFragmentManager().findFragmentById(R.id.intro_fragment);
        introFragment.setText(R.string.intro_message);
        introFragment.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0f);

        // Initialize the logging framework.
        initializeLogging();
        // use this to start and trigger a service




        i= new Intent(getApplicationContext(), MyService.class);
        i.putExtra("ACTIVITY_ON", true);

        ContextCompat.startForegroundService(getApplicationContext(), i);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // When the user clicks TEST, display the connection status.
            case R.id.test_action:
                network.checkNetworkConnection(getBaseContext());
                return true;
            // Clear the log view fragment.
            case R.id.clear_action:
                mLogFragment.getLogView().setText("");
                return true;
            case R.id.kill:
                //https://stackoverflow.com/questions/600207/how-to-check-if-a-service-is-running-on-android
                    if (i !=null && isOn){
                        isOn =false;
                        MyService.isOn = false;
                        stopService(i);
                        i = null;
                        item.setTitle("START");
                        return true;
                }else if (i == null | !isOn){

                        isOn = true;
                        MyService.isOn = true;
                        i= new Intent(getBaseContext(), MyService.class);
                        ContextCompat.startForegroundService(this, i);
                        item.setTitle("STOP");
                        return true;
                }

                return true;
        }
        return false;
    }

    /**
     * Check whether the device is connected, and if so, whether the connection
     * is wifi or mobile (it could be something else).
     */


    /** Create a chain of targets that will receive log data */
    public void initializeLogging() {

        // Using Log, front-end to the logging chain, emulates
        // android.util.log method signatures.

        // Wraps Android's native log framework
        LogWrapper logWrapper = new LogWrapper();
        Log.setLogNode(logWrapper);

        // A filter that strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        mLogFragment =
                (LogFragment) getSupportFragmentManager().findFragmentById(R.id.log_fragment);
        msgFilter.setNext(mLogFragment.getLogView());
    }

    @Override
    protected void onResume() {
        super.onResume();



        i.putExtra("ACTIVITY_ON", true);

        ContextCompat.startForegroundService(getApplicationContext(), i);



        final ArrayList<String> network = new Network().checkNetworkConnection(getApplicationContext());

        Switch aSwitch = (Switch) findViewById(R.id.a_switch);
        boolean isChecked = aSwitch.isChecked();
        if(network.contains(getString(R.string.no_wifi_connection))){
            aSwitch.setChecked(false);

        }else if(network.contains(getString(R.string.wifi_connection))){
            aSwitch.setChecked(true);


        }

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

                if(isChecked){
                    wifiManager.setWifiEnabled(true);
                }else if(!isChecked){
                    wifiManager.setWifiEnabled(false);

                }

            }

    });

}

    @Override
    protected void onStop() {
        super.onStop();
        i.putExtra("ACTIVITY_ON", false);

        ContextCompat.startForegroundService(getApplicationContext(), i);
    }

    //TODO reciver
}
