package com.example.android.basicnetworking;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;

public class ActivityDialog extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        if(!MyService.dialogOnScreen) {
            super.onCreate(savedInstanceState);
            MyService.dialogOnScreen = true;
            TurnWifiOn newFragment = new TurnWifiOn();
            newFragment.show(getSupportFragmentManager(), "missiles");

        }

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        MyService.dialogOnScreen = false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
        MyService.dialogOnScreen = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyService.dialogOnScreen = false;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        MyService.dialogOnScreen = false;
    }
}
