package com.thalesgroup.tshpaysample.ui.ui_util;

import android.app.Activity;
import android.view.View;
import com.google.android.material.snackbar.Snackbar;
public class DisplayMessage {


    public static void show(Activity activity, String message){
        View parentLayout = activity.findViewById(android.R.id.content);
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                .setTextColor(activity.getResources().getColor(android.R.color.white ))
                .setBackgroundTint(activity.getResources().getColor(android.R.color.black ))

                .show();
    }
}
