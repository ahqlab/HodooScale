package com.animal.scale.hodoo.activity.wifi.find;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.Device;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.animal.scale.hodoo.util.MathUtil;

import retrofit2.Call;

public class FindHodoosModel extends CommonModel {

    public void registDevice(String bssid, final DomainCallBackListner<Integer> domainCallBackListner) {
        Device device = new Device();
        device.setGroupCode(sharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE));
        device.setSerialNumber(bssid);
        Call<Integer> call = NetRetrofit.getInstance().getDeviceService().insertDevice(device);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<Integer>() {
            @Override
            protected void doPostExecute(Integer result) {
                domainCallBackListner.doPostExecute(result);
            }
            @Override
            protected void doPreExecute() {
                domainCallBackListner.doPreExecute();
            }
        }.execute(call), limitedTime, interval, true).start();
    }
}
