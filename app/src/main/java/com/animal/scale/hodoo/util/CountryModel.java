package com.animal.scale.hodoo.util;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.Country;
import com.animal.scale.hodoo.service.NetRetrofit;

import java.util.List;

import retrofit2.Call;

public class CountryModel extends CommonModel {
    public void getAllCountry (int country, final CountryModel.DomainListCallBackListner listCallback ) {
        country += 1;
        Call<List<Country>> call = NetRetrofit.getInstance().getCountryService().getAllCountry(country);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTaskOfList<Country>() {
            @Override
            protected void doPostExecute(List<Country> d) {
                listCallback.doPostExecute(d);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }
}
