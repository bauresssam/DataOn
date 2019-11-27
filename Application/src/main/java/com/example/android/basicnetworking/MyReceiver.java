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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {




    @Override
    public void onReceive(final Context context, Intent intent) {


                    if (!MyService.dialogOnScreen && new Network().checkNetworkConnection(context).contains(context.getString(R.string.mobile_connection))){


                        Intent in = new Intent(context.getApplicationContext(), ActivityDialog.class);
                        //not when activity is open
                        in.addFlags(
                                Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS|
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


