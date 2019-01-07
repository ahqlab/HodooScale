package com.animal.scale.hodoo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
    }
    public static ServiceReceiver getInstance( Context context ) {
        return new ServiceReceiver();
    }
}
