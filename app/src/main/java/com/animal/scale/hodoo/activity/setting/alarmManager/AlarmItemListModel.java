package com.animal.scale.hodoo.activity.setting.alarmManager;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.AlarmItem;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.service.NetRetrofit;

import java.util.List;

import retrofit2.Call;

public class AlarmItemListModel extends CommonModel {

    Context context;

    public SharedPrefManager sharedPrefManager;

    @Override
    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }


    public void getAlarmItems(final CommonDomainCallBackListner<AlarmItem> domainCallBackListner) {
        Call<CommonResponce<List<AlarmItem>>> call = NetRetrofit.getInstance().getAlarmItemService().getAlarmList(sharedPrefManager.getStringExtra(SharedPrefVariable.CURRENT_COUNTRY));
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<List<AlarmItem>>>() {
            @Override
            protected void doPostExecute(CommonResponce<List<AlarmItem>> listCommonResponce) {
                domainCallBackListner.doPostExecute(listCommonResponce);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }

    public void getAlarm(final ObjectCallBackListner<CommonResponce<Integer>> domainCallBackListner) {
        Call<CommonResponce<Integer>> call = NetRetrofit.getInstance().getAlarmItemService().getAlarm(sharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID));
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<Integer>>() {
            @Override
            protected void doPostExecute(CommonResponce<Integer> listCommonResponce) {
                domainCallBackListner.doPostExecute(listCommonResponce);

            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }

    public void saveAlarm(int number, final ObjectCallBackListner<CommonResponce<Integer>> domainCallBackListner) {
        Call<CommonResponce<Integer>> call = NetRetrofit.getInstance().getAlarmItemService().saveAlarm(sharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID), number);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<Integer>>() {
            @Override
            protected void doPostExecute(CommonResponce<Integer> listCommonResponce) {
                domainCallBackListner.doPostExecute(listCommonResponce);

            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }

}
