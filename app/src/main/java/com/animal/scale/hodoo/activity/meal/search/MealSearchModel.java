package com.animal.scale.hodoo.activity.meal.search;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.db.DBHandler;
import com.animal.scale.hodoo.domain.SearchHistory;
import com.animal.scale.hodoo.domain.SearchParam;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MealSearchModel extends CommonModel {

    private DBHandler dbHandler;

    @Override
    public void loadData(Context context) {
        dbHandler = new DBHandler(context);
        super.loadData(context);
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

    public List<SearchHistory> getSearchHistory() {
        return dbHandler.select();
    }
}
