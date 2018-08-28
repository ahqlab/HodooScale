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
import android.net.NetworkRequest;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.adapter.AdapterOfWifiList;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityWifiSearchBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;

import java.util.List;

public class WifiSearchActivity extends BaseActivity<WifiSearchActivity> {

    private static final String TAG = "WIFIScanner";

    private static final int REQUEST_ACCESS_COARSE_LOCATION = 1;

    // WifiManager variable
    WifiManager wifimanager;

    ActivityWifiSearchBinding binding;

    private List<ScanResult> mScanResult; // ScanResult List

    AdapterOfWifiList Adapter;

    ConnectivityManager manager;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wifi_search);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.wifi_list_title)));
        super.setToolbarColor();
        // Setup WIFI
        manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        wifimanager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        // Log.d(TAG, "Setup WIfiManager getSystemService");
        // if WIFIEnabled
        if (wifimanager.isWifiEnabled() == false) {
            wifimanager.setWifiEnabled(true);
        }
        int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permissionCamera == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(WifiSearchActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ACCESS_COARSE_LOCATION);
        } else {
            //Toast.makeText(getApplicationContext(), "ACCESS COARSE LOCATION permission authorized", Toast.LENGTH_SHORT).show();
            initWIFIScan();
        }

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        manager.registerNetworkCallback(
                builder.build(),
                new ConnectivityManager.NetworkCallback() {
                    @Override
                    public void onAvailable(Network network) {
                        initWIFIScan();
                        //네트워크 연결됨
                        Toast.makeText(getApplicationContext(), "연결됨", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onLost(Network network) {
                        //네트워크 끊어짐
                        Toast.makeText(getApplicationContext(), "끊어짐", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    @Override
    protected BaseActivity<WifiSearchActivity> getActivityClass() {
        return WifiSearchActivity.this;
    }


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                getWIFIScanResult(); // get WIFISCanResult
                wifimanager.startScan(); // for refresh
            } else if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                Log.e("HJLEE", "CHANGE");
                sendBroadcast(new Intent("wifi.ON_NETWORK_STATE_CHANGED"));
            }
        }
    };



    public void showPopup(final String SSID) {
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
                connect(SSID, password);
                dialog.dismiss();
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
        Adapter = new AdapterOfWifiList(WifiSearchActivity.this, R.layout.wifi_listview, mScanResult);
        binding.listview.setAdapter(Adapter);
        binding.listview.setOnItemClickListener(mItemClickListener);
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            ScanResult scanResult = (ScanResult) adapterView.getItemAtPosition(position);
            showPopup(scanResult.SSID.toString());
            //Toast.makeText(getApplicationContext(), "scanResult.SSID" + scanResult.SSID, Toast.LENGTH_SHORT).show();
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
        //WIFI BRODCAST를 중단한다.
        //unregisterReceiver(mReceiver);
    }


    @Override
    protected void onResume() {
        initWIFIScan();
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
                        } else {
                            // Toast.makeText(getApplicationContext(), "ACCESS COARSE LOCATION permission denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }
}
