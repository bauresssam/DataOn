package com.example.android.basicnetworking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {




    @Override
    public void onReceive(final Context context, Intent intent) {
        String TAG = "Basic Network Demo";



        //TODO: this in reciver inbeded class in main activity

                    if (!MyService.dialogOnScreen && new Network().checkNetworkConnection(context).contains(context.getString(R.string.mobile_connection))){


                        Intent in = new Intent(context.getApplicationContext(), ActivityDialog.class);
                        //not when activity is open
                        in.addFlags(

                                        Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_NO_ANIMATION
                                 );
                        MyService.dialogOnScreen = true;
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


