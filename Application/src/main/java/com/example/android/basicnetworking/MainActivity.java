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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.android.common.logger.Log;
import com.example.android.common.logger.LogFragment;
import com.example.android.common.logger.LogWrapper;
import com.example.android.common.logger.MessageOnlyLogFilter;


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
    Network network = new Network();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);



//      start My service with broadcast receivers
        i= new Intent(getBaseContext(), MyService.class);
        ContextCompat.startForegroundService(this, i);
        MyService.isOn = true;


        setContentView(R.layout.sample_main);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (!(pm.isIgnoringBatteryOptimizations(getPackageName()))) {


            //https://stackoverflow.com/questions/26097513/android-simple-alert-dialog

            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Action Required");
            alertDialog.setMessage("Please disable battery optimization, to stop unexpected force close.");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivityForResult(new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS), 0);
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        }


        // Initialize text fragment that displays intro text.
        SimpleTextFragment introFragment = (SimpleTextFragment)
                    getSupportFragmentManager().findFragmentById(R.id.intro_fragment);
        introFragment.setText(R.string.intro_message);
        introFragment.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0f);

        // Initialize the logging framework.
        initializeLogging();
        // use this to start and trigger a service







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

                //TODO: change
//              Turn MyService on and off
                case R.id.kill:
                    //https://stackoverflow.com/questions/600207/how-to-check-if-a-service-is-running-on-android
                        if ( MyService.isOn){
                            MyService.isOn = false;
                            stopService(i);
                            i = null;
                            item.setTitle("START");
                            return true;
                    }else if ( !MyService.isOn){

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


//      old wifi switch (turn wifi on and off)

//        final ArrayList<String> network = new Network().checkNetworkConnection(getApplicationContext());
//
//        Switch aSwitch = (Switch) findViewById(R.id.a_switch);
//        boolean isChecked = aSwitch.isChecked();
//        if(network.contains(getString(R.string.no_wifi_connection))){
//            aSwitch.setChecked(false);
//
//        }else if(network.contains(getString(R.string.wifi_connection))){
//            aSwitch.setChecked(true);
//
//
//        }
//
//        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//
//                if(isChecked){
//                    wifiManager.setWifiEnabled(true);
//                }else if(!isChecked){
//                    wifiManager.setWifiEnabled(false);
//
//                }
//
//            }
//
//    });

}

    @Override
    protected void onPause() {
        super.onPause();

//      Make sure that the dialog activity is not launched

//        i.putExtra("ACTIVITY_ON", false);
//        ContextCompat.startForegroundService(getApplicationContext(), i);
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

//      Make sure that the dialog activity is not launched

//        i.putExtra("ACTIVITY_ON", false);
//        ContextCompat.startForegroundService(getApplicationContext(), i);
    }


    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();


//        Make sure that the dialog activity is not launched

//        i= new Intent(getApplicationContext(), MyService.class);
//        i.putExtra("ACTIVITY_ON", true);
//        ContextCompat.startForegroundService(getApplicationContext(), i);

    }
}
