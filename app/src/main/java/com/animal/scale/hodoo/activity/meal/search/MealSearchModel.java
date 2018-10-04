package com.animal.scale.hodoo.activity.meal.search;

import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.SearchParam;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MealSearchModel extends CommonModel {

    public void getAllFeed(final DomainListCallBackListner<AutoCompleateFeed> domainListCallBackListner) {
        Call<List<AutoCompleateFeed>> call = NetRetrofit.getInstance().getFeedService().getAllFeedList();
        new AbstractAsyncTaskOfList<AutoCompleateFeed>() {
            @Override
            protected void doPostExecute(List<AutoCompleateFeed> d) {
                domainListCallBackListner.doPostExecute(d);
            }

            @Override
            protected void doPreExecute() {
                domainListCallBackListner.doPreExecute();
            }
        }.execute(call);
    }

    public void getSearchFeed(String s ,final DomainListCallBackListner<AutoCompleateFeed> domainListCallBackListner) {
        Call<List<AutoCompleateFeed>> call = NetRetrofit.getInstance().getFeedService().getSearchFeedList(new SearchParam(s));
        new AbstractAsyncTaskOfList<AutoCompleateFeed>() {
            @Override
            protected void doPostExecute(List<AutoCompleateFeed> d) {
                domainListCallBackListner.doPostExecute(d);
            }

            @Override
            protected void doPreExecute() {
                domainListCallBackListner.doPreExecute();
            }
        }.execute(call);
    }
}
