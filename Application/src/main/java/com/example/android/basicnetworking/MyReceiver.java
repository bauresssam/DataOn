package com.example.android.basicnetworking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class MyReceiver extends BroadcastReceiver {




    @Override
    public void onReceive(final Context context, Intent intent) {
        String TAG = "Basic Network Demo";

        String network = new Network().checkNetworkConnection(context);

                    if (!MyService.dialogOnScreen && network.equals(context.getString(R.string.mobile_connection))){



                        Intent in = new Intent(context, ActivityDialog.class);
                        //not when activity is open
                        in.addFlags(FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(in);
                    /*

                    Toast toast = Toast.makeText(context,  context.getString(R.string.mobile_connection),Toast.LENGTH_LONG);
                    toast.show();
                    Log.i(TAG, context.getString(R.string.mobile_connection));
*/



            }
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


