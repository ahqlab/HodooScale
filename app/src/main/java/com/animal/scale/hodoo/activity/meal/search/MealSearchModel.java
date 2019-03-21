package com.animal.scale.hodoo.activity.meal.search;

import android.content.Context;
import android.util.Log;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.db.DBHandler;
import com.animal.scale.hodoo.domain.SearchHistory;
import com.animal.scale.hodoo.domain.SearchParam;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.animal.scale.hodoo.custom.view.input.CommonTextWatcher.TAG;

public class MealSearchModel extends CommonModel {

    Context context;

    public SharedPrefManager sharedPrefManager;

    private DBHandler dbHandler;

    @Override
    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
        dbHandler = new DBHandler(context);
        super.loadData(context);
    }

    public void getAllFeed(final DomainListCallBackListner<AutoCompleateFeed> domainListCallBackListner) {
        Call<List<AutoCompleateFeed>> call = NetRetrofit.getInstance().getFeedService().getAllFeedList();
        new AsyncTaskCancelTimerTask(new AbstractAsyncTaskOfList<AutoCompleateFeed>() {
            @Override
            protected void doPostExecute(List<AutoCompleateFeed> d) {
                domainListCallBackListner.doPostExecute(d);
            }

            @Override
            protected void doPreExecute() {
                domainListCallBackListner.doPreExecute();
            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }

    public void getSearchFeed(String s ,final DomainListCallBackListner<AutoCompleateFeed> domainListCallBackListner) {
        Call<List<AutoCompleateFeed>> call = NetRetrofit.getInstance().getFeedService().getSearchFeedList(new SearchParam(s), sharedPrefManager.getStringExtra(SharedPrefVariable.CURRENT_COUNTRY));
        new AsyncTaskCancelTimerTask(new AbstractAsyncTaskOfList<AutoCompleateFeed>() {
            @Override
            protected void doPostExecute(List<AutoCompleateFeed> d) {
                domainListCallBackListner.doPostExecute(d);
            }

            @Override
            protected void doPreExecute() {
                domainListCallBackListner.doPreExecute();
            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }


    public List<SearchHistory> getSearchHistory() {
        return dbHandler.select();
    }
}
