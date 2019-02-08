package com.animal.scale.hodoo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.animal.scale.hodoo.activity.home.activity.HomeActivity;
import com.animal.scale.hodoo.activity.home.fragment.welcome.WelcomeFirstFragment;
import com.animal.scale.hodoo.activity.home.fragment.welcome.WelcomeFourFragment;
import com.animal.scale.hodoo.activity.home.fragment.welcome.WelcomeHomeFragment;
import com.animal.scale.hodoo.activity.home.fragment.welcome.WelcomeSecondFragment;
import com.animal.scale.hodoo.activity.home.fragment.welcome.WelcomeThirdFragment;
import com.animal.scale.hodoo.activity.user.login.LoginActivity;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.custom.view.WelcomeViewPager;
import com.animal.scale.hodoo.util.VIewUtil;

import static com.animal.scale.hodoo.constant.HodooConstant.ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE;
import static com.animal.scale.hodoo.constant.HodooConstant.AUTO_LOGIN_SUCCESS;

public class
MainActivity extends AppCompatActivity implements Main.View {

    private ProgressBar bar;

    private static final String CONNECTION_CONFIRM_CLIENT_URL = "http://clients3.google.com/generate_204";

    public String store_version;

    public String device_version;

    private WelcomeViewPager mSlideView;

    Intent intent;

    private boolean isCreated = false;

    private boolean logoutState = false;

    private Main.Presenter presenter;

    public SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoutState = getIntent().getBooleanExtra(SharedPrefVariable.LOGIN_PAGE_INTENT, false);
        presenter = new MainPresenter(this);
        presenter.initDate(this);
        sharedPrefManager = SharedPrefManager.getInstance(MainActivity.this);
        String countryCode = VIewUtil.getMyLocationCode(MainActivity.this);
        sharedPrefManager.putStringExtra(SharedPrefVariable.CURRENT_COUNTRY, countryCode);

        mSlideView = findViewById(R.id.slide_view);


        systemAlertPermission();

//        bar = (ProgressBar) findViewById(R.id.progress_loader);
//        bar.setVisibility(View.GONE);
//        if (!isOnline()) {
//            Toast.makeText(getApplicationContext(), R.string.not_connected_to_the_Internet, Toast.LENGTH_LONG).show();
//            // new ServiceCheckTask().execute();
//        }

    }

//    @OnClick({R.id.signup_btn, R.id.login_btn})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.signup_btn:
//                intent = new Intent(getApplicationContext(), SignUpActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.login_btn:
//                intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
//                break;
//        }
//    }

   /* private boolean isOnline() {
        CheckConnect cc = new CheckConnect(CONNECTION_CONFIRM_CLIENT_URL);
        cc.start();
        try {
            cc.join();
            return cc.isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }*/

    //    private class ServiceCheckTask extends AsyncTask<Void, Void, String> {
//        @Override
//        protected void onPreExecute() {
//            bar.setVisibility(View.VISIBLE);
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(Void... arg0) {
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            //Intent intent = new Intent(getApplicationContext(), WifiSearchActivity.class);
//            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//            startActivity(intent);
//            finish();
//            super.onPostExecute(result);
//        }
//    }


    public void showDialog() {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Log.e("HJLEE", "!@#!@#!@#");
                //Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
                //DialogUtil.showAlert(getApplicationContext(), R.string.message_complete_required_fields);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
                alertDialog.setMessage("11111");
                alertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isCreated) {
            mSlideView.setFragments(getSupportFragmentManager(), WelcomeHomeFragment.newInstance(), WelcomeFirstFragment.newInstance(), WelcomeSecondFragment.newInstance(), WelcomeThirdFragment.newInstance(), WelcomeFourFragment.newInstance());
            isCreated = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }



    @Override
    public void goHomeActivity() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        //overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    @Override
    public void goAutoLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra(SharedPrefVariable.AUTO_LOGIN, AUTO_LOGIN_SUCCESS);
        startActivity(intent);
        //overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    public void systemAlertPermission () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                AlertDialog builder = new AlertDialog.Builder(this)
                        .setTitle(R.string.main_activity__permission_check_title)
                        .setMessage(R.string.main_activity__permission_check_suffix)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                        Uri.parse("package:" + getPackageName()));
                                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
                            }
                        }).setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                presenter.getData();
                                dialogInterface.dismiss();
                            }
                        })
                        .setCancelable(false)
                        .create();
                builder.setCanceledOnTouchOutside(false);
                builder.show();
            } else {
                presenter.getData();
            }
        } else {
            presenter.getData();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Settings.canDrawOverlays(this)) {
            presenter.getData();
        }
    }
}
