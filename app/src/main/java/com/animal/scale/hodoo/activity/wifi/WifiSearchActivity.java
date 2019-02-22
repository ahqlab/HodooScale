package com.animal.scale.hodoo.activity.wifi;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.wifi.find.FindHodoosActivity;
import com.animal.scale.hodoo.adapter.AdapterOfWifiList;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityWifiSearchBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.util.WifiUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WifiSearchActivity extends BaseActivity<WifiSearchActivity> {

    private static final String TAG = "WIFIScanner";

    private static final int REQUEST_ACCESS_COARSE_LOCATION = 1;

    private static final int REQUEST_PERMISSION = 0x01;

    // WifiManager variable
    WifiManager wifimanager;

    ActivityWifiSearchBinding binding;

    private List<ScanResult> mScanResult; // ScanResult List

    AdapterOfWifiList Adapter;

    ConnectivityManager manager;

    public static WifiSearchActivity wifiSearchActivity;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wifi_search);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.wifi_list_title)));
        wifiSearchActivity = WifiSearchActivity.this;
        super.setToolbarColor();
        // Setup WIFI
        binding.loginProgress.setVisibility(View.VISIBLE);
        manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        wifimanager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        // Log.d(TAG, "Setup WIfiManager getSystemService");
        // if WIFIEnabled
    /*    if (wifimanager.isWifiEnabled() == false) {
            wifimanager.setWifiEnabled(true);
        }*/
        int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permissionCamera == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(WifiSearchActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ACCESS_COARSE_LOCATION);
        } else {
            initWIFIScan();
        }

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        manager.registerNetworkCallback(
                builder.build(),
                new ConnectivityManager.NetworkCallback() {
                    @Override
                    public void onAvailable(Network network) {
                        //initWIFIScan();
                        //네트워크 연결됨
                        // Toast.makeText(getApplicationContext(), "연결됨", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onLost(Network network) {
                        //네트워크 끊어짐
                        //Toast.makeText(getApplicationContext(), "끊어짐", Toast.LENGTH_LONG).show();
                    }
                }
        );

        if (isSDKAtLeastP()) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION);
            } else {
                //registerBroadcastReceiver();
                //showEsptouchInfo();
            }
        } else {
            //registerBroadcastReceiver();
            //showEsptouchInfo();
        }

    }

    private boolean isSDKAtLeastP() {
        return Build.VERSION.SDK_INT >= 28;
    }

   /* private void showEsptouchInfo() {
        byte[] ssid = ByteUtil.getBytesByString("AHQLab_Dev");
        byte[] password = ByteUtil.getBytesByString("ahqlab3596");
        byte[] bssid = EspNetUtil.parseBssid2bytes("88:36:6c:31:66:58");

        byte[] deviceCount = "1".getBytes();
        byte[] broadcast = {(byte) (1)};

        if (mTask != null) {
            mTask.cancelEsptouch();
        }
        mTask = new EsptouchAsyncTask4(WifiSearchActivity.this);
        mTask.execute(ssid, bssid, password, deviceCount, broadcast);
    }*/

    @Override
    protected BaseActivity<WifiSearchActivity> getActivityClass() {
        return WifiSearchActivity.this;
    }


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                //Log.e("HJLEE", "SCAN_RESULTS_AVAILABLE_ACTION");
                getWIFIScanResult(); // get WIFISCanResult
            } else if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                //Log.e("HJLEE", "NETWORK_STATE_CHANGED_ACTION");
                //sendBroadcast(new Intent("wifi.ON_NETWORK_STATE_CHANGED"));
                //wifimanager.startScan(); // for refresh
                //getWIFIScanResult();
                initWIFIScan();
            }
        }
    };


    public void showPopup(final String SSID, final String bSSID) {
        Log.e("HJLEE", "SSID : "+ SSID);
        Log.e("HJLEE", "bSSID : "+ bSSID);
        AlertDialog.Builder builder = new AlertDialog.Builder(WifiSearchActivity.this);
        builder.setTitle(SSID);
        LayoutInflater inflater = this.getLayoutInflater();

        View viewInflated = inflater.inflate(R.layout.dialog_text_inpu_password, null);
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String password = input.getText().toString();
                dialog.dismiss();
               /* WifiUtil wifiUtil = new WifiUtil(WifiSearchActivity.this);

                Log.e("HJLEE", "result : " + result);*/
                //connect(SSID, password);
                Intent intent = new Intent(WifiSearchActivity.this, FindHodoosActivity.class);
                intent.putExtra("ssid", SSID);
                intent.putExtra("bssid", bSSID);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void connect(String WIFI_NAME, String WIFI_PASSWORD) {
        WifiConfiguration wificonfig = new WifiConfiguration();
        wificonfig.SSID = String.format("\"%s\"", WIFI_NAME);
        wificonfig.preSharedKey = String.format("\"%s\"", WIFI_PASSWORD);

        int networkId = wifimanager.addNetwork(wificonfig);
        if (networkId >= 0) {
            // Try to disable the current network and start a new one.
            if (wifimanager.enableNetwork(networkId, true)) {
                Log.e("HJLEE", "Associating to network " + WIFI_NAME);
                wifimanager.saveConfiguration();
            } else {
                Log.e("HJLEE", "Failed to enable network " + WIFI_NAME);
            }
        } else {
            Log.e("HJLEE", "Unable to add network " + WIFI_NAME);
        }
        wifimanager.startScan();
    }


    public void getWIFIScanResult() {
        mScanResult = wifimanager.getScanResults(); // ScanResult
        if(getCurrentSsid(getApplicationContext()) != null){
            Collections.sort(mScanResult, new Comparator<ScanResult>() {
                @Override
                public int compare(ScanResult scanResult, ScanResult t1) {
                    if (scanResult.SSID.toString().matches(getCurrentSsid(getApplicationContext()))) {
                        return -1;
                    }
                    return 0;
                }
            });
        }


        Adapter = new AdapterOfWifiList(WifiSearchActivity.this, R.layout.wifi_listview, mScanResult);
        binding.listview.setAdapter(Adapter);
        binding.listview.setOnItemClickListener(mItemClickListener);
        binding.loginProgress.setVisibility(View.GONE);
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            ScanResult scanResult = (ScanResult) adapterView.getItemAtPosition(position);
            showPopup(scanResult.SSID.toString(), scanResult.BSSID.toString());
        }
    };

    public void initWIFIScan() {
        // init WIFISCAN
        final IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(mReceiver, filter);
        wifimanager.startScan();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("HJLEE", "onPause");
        //WIFI BRODCAST를 중단한다.
        try {
            unregisterReceiver(mReceiver);
        } catch (IllegalArgumentException e) {

        }
    }


    @Override
    protected void onResume() {
        wifimanager.startScan(); // for refresh
        getWIFIScanResult();
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ACCESS_COARSE_LOCATION:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            initWIFIScan(); // start WIFIScan
                            //wifimanager.startScan();
                        } else {
                            // Toast.makeText(getApplicationContext(), "ACCESS COARSE LOCATION permission denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    public static String getCurrentSsid(Context context) {
        String ssid = null;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
                ssid = connectionInfo.getSSID().replace("\"", "").trim();
            }
        }
        return ssid;
    }
}
