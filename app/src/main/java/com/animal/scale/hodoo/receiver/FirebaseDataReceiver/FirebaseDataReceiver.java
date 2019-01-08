package com.animal.scale.hodoo.receiver.FirebaseDataReceiver;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.animal.scale.hodoo.MainActivity;
import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.fcm.AlertActivity;
import com.animal.scale.hodoo.fcm.PushWakeLock;
import com.animal.scale.hodoo.service.AlwaysOnTopService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseDataReceiver extends WakefulBroadcastReceiver {

    NotificationCompat notificationBuilder;
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Log.d("HJLEE", "I'm in!!!");
        String title = "", content = "", host = "", data = "";
        if (intent.getExtras() != null) {
            for (String key : intent.getExtras().keySet()) {
                Object value = intent.getExtras().get(key);
                if ( key.contains("title") ) {
                    title = (String) value;
                } else if ( key.contains("body") ) {
                    content = (String) value;
                } else if ( key.contains("host") ) {
                    host = (String) value;
                } else if ( key.contains("datas") ) {
                    data = (String) value;
                }
            }
        }

    }

}