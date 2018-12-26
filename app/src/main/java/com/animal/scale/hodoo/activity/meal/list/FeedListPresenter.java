package com.animal.scale.hodoo.activity.meal.list;

import android.content.Context;
import android.content.Intent;

import com.animal.scale.hodoo.activity.home.fragment.meal.MealFragmentModel;
import com.animal.scale.hodoo.activity.meal.regist.MealRegistrationModel;
import com.animal.scale.hodoo.activity.meal.search.AutoCompleateFeed;
import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.MealHistoryContent;
import com.animal.scale.hodoo.domain.PetAllInfos;

import java.util.List;

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
        model.getList(date, new FeedListModel.DomainListCallBackListner<MealHistoryContent>() {
            @Override
            public void doPostExecute(List<MealHistoryContent> d) {
                view.setListView(d);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void initSeekbar() {

    }

    @Override
    public void getTodaySumCalorie() {
        mealRegistrationModel.getTodaySumCalorie(new MealRegistrationModel.DomainCallBackListner<MealHistory>() {
            @Override
            public void doPostExecute(MealHistory mealHistory) {
                view.setTodaySumCalorie(mealHistory);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void getPetAllInfo() {
        mealRegistrationModel.getPetAllInfo(new MealFragmentModel.DomainCallBackListner<PetAllInfos>() {
            @Override
            public void doPostExecute(PetAllInfos petAllInfos) {
                view.setPetAllInfo(petAllInfos);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void deleteMealHistory(int historyIdx) {
        model.deleteMealHistory(historyIdx, new FeedListModel.DomainCallBackListner<Integer>(){

            @Override
            public void doPostExecute(Integer integer) {
                view.deleteResult(integer);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
