package com.animal.scale.hodoo.adapter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.animal.scale.hodoo.R;

import java.util.List;

public class AdapterOfWifiList extends BaseAdapter {

    Context context;
    private LayoutInflater inflater;
    private List<ScanResult> data;
    private int layout;

    public AdapterOfWifiList(Context context, int layout, List<ScanResult> data) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ScanResult scanResult = data.get(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.ssid);
            viewHolder.rssi = (TextView) convertView.findViewById(R.id.rssi);
            viewHolder.wifiStrength = (ImageView) convertView.findViewById(R.id.wifi_strength);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(getCurrentSsid(context) == null){
            viewHolder.name.setText(scanResult.SSID.toString());
            viewHolder.name.setTextColor(ContextCompat.getColor(context, R.color.hodoo_text_black));
            viewHolder.rssi.setText("");
            viewHolder.wifiStrength.setImageDrawable(context.getResources().getDrawable(getWifiStrengthResource(scanResult.level)));
        }else{
            if (scanResult.SSID.toString().matches(getCurrentSsid(context))) {
                //현재 연결된 WIFI
                viewHolder.name.setText(scanResult.SSID.toString());
                viewHolder.name.setTextColor(ContextCompat.getColor(context, R.color.hodoo_pink));
                viewHolder.rssi.setText("현재폰에 연결된 WIFI 사용");
                viewHolder.wifiStrength.setImageDrawable(context.getResources().getDrawable(getWifiStrengthResource(scanResult.level)));
            }else{
                viewHolder.name.setText(scanResult.SSID.toString());
                viewHolder.name.setTextColor(ContextCompat.getColor(context, R.color.hodoo_text_black));
                viewHolder.rssi.setText("");
                viewHolder.wifiStrength.setImageDrawable(context.getResources().getDrawable(getWifiStrengthResource(scanResult.level)));
            }
        }
        return convertView;
    }

    private static class ViewHolder {
        public TextView name;
        public TextView rssi;
        public ImageView wifiStrength;
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

    public int getWifiStrengthResource(int rssi) {
        if (rssi >= -25) {
            return R.drawable.wifi_list_middle_bullet_4step_24_17;
        } else if (rssi >= -50) {
            return R.drawable.wifi_list_middle_bullet_3step_24_17;
        } else if (rssi >= -75) {
            return R.drawable.wifi_list_middle_bullet_2step_24_17;
        } else if (rssi >= -100) {
            return R.drawable.wifi_list_middle_bullet_1step_24_17;
        }
        return 0;
    }
}
