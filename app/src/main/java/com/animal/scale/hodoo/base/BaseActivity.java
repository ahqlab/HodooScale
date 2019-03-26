package com.animal.scale.hodoo.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.home.activity.HomeActivity;
import com.animal.scale.hodoo.activity.home.fragment.meal.MealFragment;
import com.animal.scale.hodoo.activity.home.fragment.weight.WeightFragment;
import com.animal.scale.hodoo.common.CommonNotificationModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.util.BadgeUtils;
import com.animal.scale.hodoo.util.VIewUtil;

import java.util.List;

public abstract class BaseActivity<D extends Activity> extends AppCompatActivity {

    protected final String TAG = "HJLEE";
    public SharedPrefManager mSharedPrefManager;
    private boolean badgeState = false;



    public interface OnSubBtnClickListener {
        void onClick( View v );
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setBadge();
            Log.e(TAG, "onReceive: ");

            ActivityManager mActivityManager = (ActivityManager) BaseActivity.this.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> rt = mActivityManager.getRunningTasks(1);

            /* 열려있는 액티비티가 홈 액티비티일 경우 (s) */
            if ( rt.get(0).topActivity.getClassName().equals(HomeActivity.class.getName()) ) {
                for (Fragment fragment: getSupportFragmentManager().getFragments()) {
                    if (fragment.isVisible())
                        /* 활동중인 프래그먼트가 체중 프래그먼트일경우 (s) */
                        if ( fragment instanceof WeightFragment )
                            ((WeightFragment) fragment).setKg();
                        /* 활동중인 프래그먼트가 식사 프래그먼트일경우 (e) */
                        else if(fragment instanceof MealFragment){
                            ((MealFragment) fragment).refrashSetProgress();
                        }

                }
            }
            /* 열려있는 액티비티가 홈 액티비티일 경우 (e) */
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPrefManager = SharedPrefManager.getInstance(getActivityClass());
        String countryCode = VIewUtil.getMyLocationCode(getActivityClass());
        mSharedPrefManager.putStringExtra(SharedPrefVariable.CURRENT_COUNTRY, countryCode);
    }

    protected abstract BaseActivity<D> getActivityClass();

    public void setToolbarColor() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.hodoo_pink), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
            }
        });
    }
    public void setSubBtn ( String text, final OnSubBtnClickListener clickListener ) {
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        Button subBtn = toolbar.findViewById(R.id.sub_btn);
        subBtn.setVisibility(View.VISIBLE);
        subBtn.setText(text);
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( clickListener != null ) {
                    clickListener.onClick(view);
                }
            }
        });
    }


    public AlertDialog.Builder showBasicOneBtnPopup(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivityClass());
        if(title != null){
            builder.setTitle(title);
        }
        if(message != null){
            builder.setMessage(message);
        }
        return builder;
    }

    public AlertDialog.Builder showBasicOneBtnPopup(int title, int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivityClass());
        if(title != 0){
            builder.setTitle(title);
        }
        if(message != 0){
            builder.setMessage(message);
        }
        return builder;
    }

    public void showToast(String message) {
//        Toast.makeText(getActivityClass(), message, Toast.LENGTH_SHORT).show();
    }

    public void moveIntent(Context packageContext, Class<?> cls, int enterAnim, int exitAnim, boolean kill){
        Intent intent = new Intent(packageContext, cls);
        startActivity(intent);
        overridePendingTransition(enterAnim , exitAnim);
        if(kill){
            finish();
        }
    }

    @Override
    protected void onStart() {
        Log.v(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.v(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.v(TAG, "onResume");
        super.onResume();
//        cancelNotification( this );
        if ( mSharedPrefManager.getIntExtra(SharedPrefVariable.BADGE_COUNT) > 0 ) {
            mSharedPrefManager.putIntExtra(SharedPrefVariable.BADGE_COUNT, 0);
            BadgeUtils.clearBadge(this);
        }
    }
    public static void cancelNotification(Context ctx) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager notifManager = (NotificationManager) ctx.getSystemService(ns);
        notifManager.cancelAll();
    }
    public static void removeNotiWithId ( Context ctx, int id ) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager notifManager = (NotificationManager) ctx.getSystemService(ns);
        notifManager.cancel(id);
    }

    @Override
    protected void onPause() {
        Log.v(TAG, "onPause");
        super.onPause();
        try{
            getApplicationContext().unregisterReceiver(mMessageReceiver);
        } catch(IllegalArgumentException e){}

        if ( isApplicationSentToBackground(getApplicationContext()) ) {
            Log.e(TAG, "application kill");
        }
    }

    @Override
    protected void onStop() {
        Log.v(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        return super.onKeyDown(keyCode, event);
    }

    public void setBadge () {
        if ( findViewById(R.id.my_toolbar) instanceof Toolbar ) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
            if ( toolbar != null ) {
                TextView settingBadge = toolbar.findViewById(R.id.setting_badge);
                if ( settingBadge != null ) {
                    getApplicationContext().registerReceiver(mMessageReceiver, new IntentFilter(HodooConstant.FCM_RECEIVER_NAME));
                    CommonNotificationModel notificationModel = CommonNotificationModel.getInstance(this);
                    int count = notificationModel.getInvitationCount();
                    if ( count <= 0 ) {
                        settingBadge.setVisibility(View.GONE);
                    } else {
                        if ( settingBadge.getVisibility() == View.GONE )
                            settingBadge.setVisibility(View.VISIBLE);
                        settingBadge.setText(String.valueOf(Math.min(count, 99)));
                    }
                }
            }
        }
    }

    public static boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
