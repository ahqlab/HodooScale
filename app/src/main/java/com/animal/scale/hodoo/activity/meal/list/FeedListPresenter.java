package com.animal.scale.hodoo.activity.meal.list;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.animal.scale.hodoo.activity.home.fragment.meal.MealFragmentModel;
import com.animal.scale.hodoo.activity.meal.regist.MealRegistrationModel;
import com.animal.scale.hodoo.activity.meal.search.AutoCompleateFeed;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.MealHistoryContent;
import com.animal.scale.hodoo.domain.PetAllInfos;

import java.util.List;

import static com.animal.scale.hodoo.activity.setting.pet.accounts.PetAccountsActivity.TAG;

public class FeedListPresenter implements FeedListIn.Presenter {

    FeedListModel model;

    MealRegistrationModel mealRegistrationModel;

    FeedListIn.View view;

    public FeedListPresenter(FeedListIn.View view) {
        this.view = view;
        this.model = new FeedListModel();
        this.mealRegistrationModel = new MealRegistrationModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
        mealRegistrationModel.loadData(context);
    }

    @Override
    public void getList(String date) {
        model.getList(date, new CommonModel.DomainListCallBackListner<MealHistoryContent>() {
            @Override
            public void doPostExecute(List<MealHistoryContent> d) {
                view.setListView(d);
            }

            @Override
            public void doPreExecute() {
                Log.e(TAG, "Feed doPreExecute");
                view.setProgress(true);
            }

            @Override
            public void doCancelled() {
                view.setProgress(false);
            }
        });
    }

    @Override
    public void getTodaySumCalorie(String date) {
        mealRegistrationModel.getTodaySumCalorie(date, new CommonModel.DomainCallBackListner<MealHistory>() {
            @Override
            public void doPostExecute(MealHistory mealHistory) {
                view.setTodaySumCalorie(mealHistory);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void getPetAllInfo() {
        mealRegistrationModel.getPetAllInfo(new CommonModel.DomainCallBackListner<PetAllInfos>() {
            @Override
            public void doPostExecute(PetAllInfos petAllInfos) {
                view.setPetAllInfo(petAllInfos);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void deleteMealHistory(int historyIdx) {
        model.deleteMealHistory(historyIdx, new CommonModel.DomainCallBackListner<Integer>(){

            @Override
            public void doPostExecute(Integer integer) {
                view.deleteResult(integer);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }
}
