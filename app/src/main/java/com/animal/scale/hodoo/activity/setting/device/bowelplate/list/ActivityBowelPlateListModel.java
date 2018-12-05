package com.animal.scale.hodoo.activity.setting.device.bowelplate.list;


import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.Device;
import com.animal.scale.hodoo.service.NetRetrofit;

import java.util.List;

import retrofit2.Call;

public class ActivityBowelPlateListModel extends CommonModel {


    public void getMyBowlPlateList(final DomainListCallBackListner<Device> domainListCallBackListner) {
        Call<List<Device>> call  = NetRetrofit.getInstance().getDeviceService().getMyDeviceList(sharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE));
        new AbstractAsyncTaskOfList<Device>() {
            @Override
            protected void doPostExecute(List<Device> d) {
                domainListCallBackListner.doPostExecute(d);
            }
            @Override
            protected void doPreExecute() {

            }
        }.execute(call);
    }

    public void setChangeSwithStatus(int deviceIdx, boolean b, final DomainCallBackListner<Integer> domainCallBackListner) {
        Call<Integer> call  = NetRetrofit.getInstance().getDeviceService().setChangeSwithStatus(deviceIdx, b);
        new AbstractAsyncTask<Integer>() {
            @Override
            protected void doPostExecute(Integer integer) {
                domainCallBackListner.doPostExecute(integer);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(call);
    }
}
