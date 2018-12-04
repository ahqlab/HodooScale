package com.animal.scale.hodoo.activity.wifi.find;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.home.activity.HomeActivity;
import com.animal.scale.hodoo.activity.wifi.WifiSearchActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityFindHodoosBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.util.VIewUtil;
import com.espressif.iot.esptouch.EsptouchTask;
import com.espressif.iot.esptouch.IEsptouchListener;
import com.espressif.iot.esptouch.IEsptouchResult;
import com.espressif.iot.esptouch.IEsptouchTask;
import com.espressif.iot.esptouch.util.ByteUtil;
import com.espressif.iot.esptouch.util.EspNetUtil;

import java.lang.ref.WeakReference;
import java.util.List;

public class FindHodoosActivity extends BaseActivity<FindHodoosActivity> implements FindHodoosIn.View{

    ProgressBar mprogressBar;

    public ActivityFindHodoosBinding binding;

    private EsptouchAsyncTask4 mTask;

    String ssid;

    String bssid;

    String password;

    FindHodoosIn.Presenter presenter;

    WifiSearchActivity wifiSearchActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_hodoos);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.find_hodoo_title)));
        wifiSearchActivity = (WifiSearchActivity) WifiSearchActivity.wifiSearchActivity;
        presenter = new FindHodoosPresenter(this);
        presenter.loadData(FindHodoosActivity.this);
        binding.findDeviceLayout.setVisibility(View.GONE);
        ssid = intent.getStringExtra("ssid");
        bssid = intent.getStringExtra("bssid");
        password = intent.getStringExtra("password");
        showEsptouchInfo(ssid , bssid, password);
        super.setToolbarColor();
    }

    @Override
    protected BaseActivity<FindHodoosActivity> getActivityClass() {
        return FindHodoosActivity.this;
    }


    public void createSaveBtn(final String bssid){
        VIewUtil vIewUtil = new VIewUtil(this);
        final int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 58, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        params.setMargins(vIewUtil.numberToDP(40),vIewUtil.numberToDP(0),vIewUtil.numberToDP(40),vIewUtil.numberToDP(31));
        Button btn = new Button(this);
        btn.setText(getString(R.string.istyle_regist));
        btn.setTypeface(Typeface.DEFAULT_BOLD);
        btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        btn.setTextColor(Color.WHITE);
        btn.setLayoutParams(params);
        btn.setBackground( getResources().getDrawable(R.drawable.rounded_pink_btn));
        btn.setStateListAnimator(null);
        ViewCompat.setElevation(btn, vIewUtil.numberToDP(1));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.registDevice(bssid);
            }
        });
        binding.findHodooMessage2.setText(getString(R.string.istyle_plate_found_message));
        binding.findDeviceIndicatior.setVisibility(View.GONE);
        binding.wifiIcon.setVisibility(View.GONE);
        binding.findDeviceLayout.setVisibility(View.VISIBLE);
        binding.btnArea.removeAllViews();
        binding.btnArea.addView(btn);

    }

    public void createRetryBtn(){
        VIewUtil vIewUtil = new VIewUtil(this);
        final int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 58, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        params.setMargins(vIewUtil.numberToDP(40),vIewUtil.numberToDP(0),vIewUtil.numberToDP(40),vIewUtil.numberToDP(31));
        Button btn = new Button(this);
        btn.setText(getString(R.string.istyle_retry));
        btn.setTypeface(Typeface.DEFAULT_BOLD);
        btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        btn.setTextColor(getResources().getColor(R.color.hodoo_midle_pink));
        btn.setLayoutParams(params);
        btn.setBackground( getResources().getDrawable(R.drawable.rounded_gray_btn));
        btn.setStateListAnimator(null);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEsptouchInfo(ssid , bssid, password);
            }
        });
        ViewCompat.setElevation(btn, vIewUtil.numberToDP(1));
        binding.btnArea.removeAllViews();
        binding.btnArea.addView(btn);
    }


    private void showEsptouchInfo(String pSsid, String pBssid, String pPassword) {
        byte[] ssid = ByteUtil.getBytesByString(pSsid);
        byte[] password = ByteUtil.getBytesByString(pPassword);
        byte[] bssid = EspNetUtil.parseBssid2bytes(pBssid);

        byte[] deviceCount = "1".getBytes();
        byte[] broadcast = {(byte) (1)};

        if (mTask != null) {
            mTask.cancelEsptouch();
        }
        mTask = new EsptouchAsyncTask4(FindHodoosActivity.this);
        mTask.execute(ssid, bssid, password, deviceCount, broadcast);
    }

    private IEsptouchListener myListener = new IEsptouchListener() {

        @Override
        public void onEsptouchResultAdded(final IEsptouchResult result) {
            onEsptoucResultAddedPerform(result);
        }
    };

    private void onEsptoucResultAddedPerform(final IEsptouchResult result) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                String text = result.getBssid() + " is connected to the wifi";
                Toast.makeText(FindHodoosActivity.this, text, Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public void registDeviceResult(Integer result) {
        if(result != 0){
            wifiSearchActivity.finish();
            finish();
        }
    }

    public class EsptouchAsyncTask4 extends AsyncTask<byte[], Void, List<IEsptouchResult>> {
        private WeakReference<FindHodoosActivity> mActivity;

        // without the lock, if the user tap confirm and cancel quickly enough,
        // the bug will arise. the reason is follows:
        // 0. task is starting created, but not finished
        // 1. the task is cancel for the task hasn't been created, it do nothing
        // 2. task is created
        // 3. Oops, the task should be cancelled, but it is running
        private final Object mLock = new Object();
        private ProgressDialog mProgressDialog;
        private android.support.v7.app.AlertDialog mResultDialog;
        private IEsptouchTask mEsptouchTask;

        EsptouchAsyncTask4(FindHodoosActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        void cancelEsptouch() {
            cancel(true);
            if (mEsptouchTask != null) {
                mEsptouchTask.interrupt();
            }
        }

        @Override
        protected void onPreExecute() {
            binding.circleView.spin();
        }

        @Override
        protected List<IEsptouchResult> doInBackground(byte[]... params) {
            FindHodoosActivity activity = mActivity.get();
            int taskResultCount;
            synchronized (mLock) {
                byte[] apSsid = params[0];
                byte[] apBssid = params[1];
                byte[] apPassword = params[2];
                byte[] deviceCountData = params[3];
                byte[] broadcastData = params[4];
                taskResultCount = deviceCountData.length == 0 ? -1 : Integer.parseInt(new String(deviceCountData));
                Context context = activity.getApplicationContext();
                mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword, context);
                mEsptouchTask.setPackageBroadcast(broadcastData[0] == 1);
                mEsptouchTask.setEsptouchListener(activity.myListener);
            }
            return mEsptouchTask.executeForResults(taskResultCount);
        }

        @Override
        protected void onPostExecute(List<IEsptouchResult> result) {
            FindHodoosActivity activity = mActivity.get();
            if (result == null) {
                Toast.makeText(activity ,"Create Esptouch task failed, the esptouch port could be used by other thread", Toast.LENGTH_SHORT).show();
                return;
            }
            IEsptouchResult firstResult = result.get(0);
            // check whether the task is cancelled and no results received
            if (!firstResult.isCancelled()) {
                int count = 0;
                // max results to be displayed, if it is more than maxDisplayCount,
                // just show the count of redundant ones
                final int maxDisplayCount = 5;
                // the task received some results including cancelled while
                // executing before receiving enough results

                if (firstResult.isSuc()) {
                    StringBuilder sb = new StringBuilder();
                    for (IEsptouchResult resultInList : result) {
                        sb.append("Esptouch success, bssid = ")
                                .append(resultInList.getBssid())
                                .append(", InetAddress = ")
                                .append(resultInList.getInetAddress().getHostAddress())
                                .append("\n");
                        count++;
                        if (count >= maxDisplayCount) {
                            break;
                        }
                    }
                    if (count < result.size()) {
                        sb.append("\nthere's ")
                                .append(result.size() - count)
                                .append(" more result(s) without showing\n");
                    }
                    Toast.makeText(activity, sb.toString(), Toast.LENGTH_LONG).show();
                    createSaveBtn(result.get(0).getBssid());
                    binding.deviceInfo.setText(sb);
                } else {
                    Toast.makeText(activity, "Esptouch fail", Toast.LENGTH_LONG).show();
                    createRetryBtn();
                }
            }
            activity.mTask = null;
            binding.circleView.stopSpinning();
        }
    }
}
