package com.animal.scale.hodoo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ProgressBar;

import com.animal.scale.hodoo.activity.home.activity.HomeActivity;
import com.animal.scale.hodoo.activity.home.fragment.welcome.WelcomeFirstFragment;
import com.animal.scale.hodoo.activity.home.fragment.welcome.WelcomeFourFragment;
import com.animal.scale.hodoo.activity.home.fragment.welcome.home.WelcomeHomeFragment;
import com.animal.scale.hodoo.activity.home.fragment.welcome.WelcomeSecondFragment;
import com.animal.scale.hodoo.activity.home.fragment.welcome.WelcomeThirdFragment;
import com.animal.scale.hodoo.activity.user.login.LoginActivity;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.custom.view.WelcomeViewPager;
import com.animal.scale.hodoo.util.VIewUtil;
import com.kakao.auth.Session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.support.constraint.Constraints.TAG;
import static com.animal.scale.hodoo.constant.HodooConstant.ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE;
import static com.animal.scale.hodoo.constant.HodooConstant.AUTO_LOGIN_SUCCESS;

public class MainActivity extends AppCompatActivity implements Main.View {

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
        String key = getKeyHash(this);
        Log.e("HJLEE", key);
    }

    /**
     * 프레그먼트를 셋팅한다.
     */
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

    /**
     * Home Activity로 이동한다.
     * Fragment 부모
     */
    @Override
    public void goHomeActivity() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        //overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    /**
     * 자동 로그인
     */
    @Override
    public void goAutoLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra(SharedPrefVariable.AUTO_LOGIN, AUTO_LOGIN_SUCCESS);
        startActivity(intent);
        //overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    /**
     * 푸쉬가 왔을때 다이얼로그로 보여주기위한 퍼미션
     */
    public void systemAlertPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                AlertDialog builder = new AlertDialog.Builder(this)
                        .setTitle(R.string.main_activity__permission_check_title)
                        .setMessage(R.string.main_activity__permission_check_suffix)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
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
        if (Settings.canDrawOverlays(this)) {
            presenter.getData();
        }
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            Log.d(TAG, "requestCode: " + requestCode + ", resultCode: " + resultCode);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * Key Hash 를 얻는 로직
     * @param context
     * @return
     */
    public static String getKeyHash(final Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            if (packageInfo == null)
                return null;

            for (Signature signature : packageInfo.signatures) {
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
                } catch (NoSuchAlgorithmException e) {
                    Log.e("HJLEE", "Unable to get MessageDigest. signature=" + signature, e);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
