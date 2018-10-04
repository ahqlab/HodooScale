package com.animal.scale.hodoo.activity.meal.list;

import android.content.Context;

import com.animal.scale.hodoo.activity.meal.search.AutoCompleateFeed;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class FeedListModel extends CommonModel {

    Context context;

    private SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

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
        };
    }
}
