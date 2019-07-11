package com.example.android.basicnetworking;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;


public class TurnWifiOn extends DialogFragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MyService.dialogOnScreen = true;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        MyService.dialogOnScreen = true;

        // https://stackoverflow.com/questions/6186433/clear-back-stack-using-fragments

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.turn_wifi_on, null))
                // Add action buttons
                .setPositiveButton("TURN WIFI ON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        wifiManager.setWifiEnabled(true);
                        MyService.dialogOnScreen = false;

                        dismiss();
                    }

                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TurnWifiOn.this.getDialog().cancel();
//                        ((FragmentActivity) Objects.requireNonNull(getContext())).finish();
                        MyService.dialogOnScreen = false;

                        dismiss();
                    }
                });

        // https://stackoverflow.com/questions/21307858/detect-back-button-but-dont-dismiss-dialogfragment
        builder.setOnKeyListener(new DialogInterface.OnKeyListener()
        {
            @Override
            public boolean onKey(android.content.DialogInterface dialog, int keyCode,android.view.KeyEvent event) {

                if ((keyCode ==  android.view.KeyEvent.KEYCODE_BACK))
                {
                    MyService.dialogOnScreen = false;

                    dismiss();
                    return true;
                }
                else
                    return false; // pass on to be processed as normal
            }
        });



        return  builder.create();
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //https://stackoverflow.com/questions/9698410/position-of-dialogfragment-in-android
        MyService.dialogOnScreen = true;
        Window w = getDialog().getWindow();
                w.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        w.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE;
        p.x = 0;


        getDialog().getWindow().setAttributes(p);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        MyService.dialogOnScreen = false;
      //  ((ActivityDialog) Objects.requireNonNull(getContext().getApplicationContext())).finish();
        dismiss();
        ((ActivityDialog) Objects.requireNonNull(getContext())).finish();

        //onDestroyView();

    }

    @Override
    public void onStop() {
        super.onStop();
    //    ((ActivityDialog) Objects.requireNonNull(getContext().getApplicationContext())).finish();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        ((ActivityDialog) Objects.requireNonNull(getContext())).finish();

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //((ActivityDialog) Objects.requireNonNull(getContext().getApplicationContext())).finish();
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onPause() {
        super.onPause();


//        // BEGIN_INCLUDE(connect)
//        final ConnectivityManager connMgr =
//                (ConnectivityManager) this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
//        if (activeInfo != null && activeInfo.isConnected()) {
//            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
//            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
//            if(mobileConnected  && MyService.dialogOnScreen) {
//                ((FragmentActivity) Objects.requireNonNull(this.getContext())).finish();
//            } else if (!mobileConnected && MyService.dialogOnScreen){
//
//            }
//        }

    }


    @Override
    public void onDetach() {
        super.onDetach();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

