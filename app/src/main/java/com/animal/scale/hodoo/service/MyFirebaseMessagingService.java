package com.animal.scale.hodoo.service;

/**
 * Created by Joo on 2017. 12. 19.
 */

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.animal.scale.hodoo.MainActivity;
import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.fcm.PushWakeLock;
import com.animal.scale.hodoo.util.BadgeUtils;
import com.animal.scale.hodoo.util.VIewUtil;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    // 메시지 수신
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i("HJLEE", "onMessageReceived");

        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String messagae = data.get("content");
        String host = data.get("host");
        int type = NotificationCompat.PRIORITY_MAX;

        KeyguardManager km = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);


        if(km.inKeyguardRestrictedInputMode()){
            PushWakeLock.acquireCpuWakeLock(getApplicationContext());
            PushWakeLock.releaseCpuLock();
        }
        if ( isAppIsInBackground(getApplicationContext()) && !km.inKeyguardRestrictedInputMode() ) {
            Intent intent = new Intent(getApplicationContext(), AlwaysOnTopService.class);
            intent.putExtra("title", title);
            intent.putExtra("message", messagae);
            intent.putExtra("host", host);
            Gson gson = new Gson();
            intent.putExtra("data", gson.toJson(data));
            if ( !isServiceRunning() ) {
                getApplicationContext().startService(intent);
            } else {
                getApplicationContext().stopService(intent);
                getApplicationContext().startService(intent);
            }
            type = NotificationCompat.PRIORITY_LOW;
        }
        sendNotification(getApplicationContext(), data, type);
    }

    private void sendNotification(Context context, Map<String, String> data, int type) {

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(context);
        int badge_count = sharedPrefManager.getIntExtra(SharedPrefVariable.BADGE_COUNT);
        if ( badge_count == 0 )
            badge_count += 1;

        String title = data.get("title");
        String message = data.get("content");
        String host = data.get("host");
        String url = "selphone://" + host;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        intent.putExtra("host", host);
        Gson gson = new Gson();
        intent.putExtra("data", gson.toJson(data));

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.transparent_logo))
                .setSmallIcon(R.drawable.transparent_white_logo)
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.mainRed))
                .setContentTitle(title)
                .setContentText(message  + "님의 초대입니다.")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(type)
                .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_SUMMARY)
                .setContentIntent(pendingIntent);

        //.addAction(R.drawable.change_user_info_user_icon, context.getString(R.string.confirm), pendingIntent)

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        BadgeUtils.setBadge(context, badge_count);
        sharedPrefManager.putIntExtra(SharedPrefVariable.BADGE_COUNT, badge_count + 1);
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
//        unbindService(connection);
    }
    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }
    private String getActiveActivity(Context context) {
        ActivityManager activityManager = (ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> info;
        info = activityManager.getRunningTasks(1);
        ActivityManager.RunningTaskInfo runningTaskInfo = info.get(0);
        return runningTaskInfo.topActivity.getClassName();
    }

    public boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (AlwaysOnTopService.class.getName().equals(service.service.getClassName()))
                return true;
        }
        return false;
    }


}

