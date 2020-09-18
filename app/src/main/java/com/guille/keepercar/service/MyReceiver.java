package com.guille.keepercar.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Guille on 03/20/2016.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(!UpdateDistanceService.isRunning())
            context.startService(new Intent(context, UpdateDistanceService.class));
    }
}
