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
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class ActivityDialog extends FragmentActivity {


  protected static TurnWifiOn newFragment;



    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        MyService.dialogOnScreen = true;

        newFragment = new TurnWifiOn();



        newFragment.show(getSupportFragmentManager(), "missiles");


    }

    @Override
    protected void onPause() {

        super.onPause();
        MyService.dialogOnScreen = false;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyService.dialogOnScreen = false;

    }

    @Override
    protected void onStart() {

        super.onStart();


    }


    @Override
    protected void onResume() {

        super.onResume();

        //TODO:

    }

     private static void kill(){
        newFragment.dismiss();
    }

    public static class CloseDialogReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
                                 if(MyService.dialogOnScreen ) {
                                     MyService.dialogOnScreen = false;
                                     kill();
                                 }
                    }
                }



}
