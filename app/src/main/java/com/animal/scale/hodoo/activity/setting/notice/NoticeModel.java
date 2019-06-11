package com.animal.scale.hodoo.activity.setting.notice;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Notice;
import com.animal.scale.hodoo.service.NetRetrofit;

import java.util.List;

import retrofit2.Call;

public class NoticeModel extends CommonModel {

    @Override
    public void loadData(Context context) {
        super.loadData(context);
    }

    public void getNoticeList(int startRow, int pageSize, final CommonDomainListCallBackListner<Notice> domainCallBackListner) {
        Call<CommonResponce<List<Notice>>> call = NetRetrofit.getInstance().getNoticeService().getNoticeList(startRow, pageSize, sharedPrefManager.getStringExtra(SharedPrefVariable.CURRENT_COUNTRY));
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<List<Notice>>>() {
            @Override
            protected void doPostExecute(CommonResponce<List<Notice>> listCommonResponce) {
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
