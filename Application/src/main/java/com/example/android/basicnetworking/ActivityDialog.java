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

        //TODO launch dialog main activity reciver
        newFragment = new TurnWifiOn();

        newFragment.show(getSupportFragmentManager(), "missiles");


    }

    @Override
    protected void onPause() {

        super.onPause();

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
