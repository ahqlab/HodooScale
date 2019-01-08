package com.animal.scale.hodoo.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;

import java.util.regex.Pattern;

public abstract class BaseActivity<D extends Activity> extends AppCompatActivity {

    protected final String TAG = "HJLEE";
    public SharedPrefManager mSharedPrefManager;
    private boolean badgeState = false;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            String message = intent.getStringExtra("message");
            Log.e(TAG, "messageeeeeeeeeeeeeeeeeeeeeeeeee : " + message);
            setBadge();
            //do other stuff here
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPrefManager = SharedPrefManager.getInstance(getActivityClass());
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

    public void showToast(String message) {
        Toast.makeText(getActivityClass(), message, Toast.LENGTH_SHORT).show();
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
        setBadge();
        if ( badgeState )
            getApplicationContext().registerReceiver(mMessageReceiver, new IntentFilter("unique_name"));

    }

    @Override
    protected void onPause() {
        Log.v(TAG, "onPause");
        super.onPause();
        try{
            getApplicationContext().unregisterReceiver(mMessageReceiver);
        } catch(IllegalArgumentException e){}


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
    public void setBadge () {
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        if ( toolbar != null ) {
            TextView settingBadge = toolbar.findViewById(R.id.setting_badge);
            if ( settingBadge != null ) {
                int count = mSharedPrefManager.getIntExtra(SharedPrefVariable.BADGE_COUNT);
                badgeState = true;
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
