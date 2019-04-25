package com.animal.scale.hodoo.activity.pet.regist.fragment.activity;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.PetUserSelectionQuestion;

/**
 * Created by SongSeokwoo on 2019-04-24.
 */
public class ActivityQuestionPresenter implements ActivityQuestionIn.Presenter {
    private ActivityQuestionIn.View view;
    private ActivityQuestionModel model;

    ActivityQuestionPresenter ( ActivityQuestionIn.View view ) {
        this.view = view;
    }
    public void loadData(Context context){
        model = new ActivityQuestionModel();
        model.loadData(context);
        view.setNavigation();
    }

    @Override
    public void getPetUserSelectQuestion(int petIdx) {
        model.registPetUserSelectQuestion(petIdx, new CommonModel.ObjectCallBackListner<CommonResponce<PetUserSelectionQuestion>>() {
            @Override
            public void doPostExecute(CommonResponce<PetUserSelectionQuestion> petUserSelectionQuestion) {
                view.setPetUserSelectQuestion(petUserSelectionQuestion);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });;
    }
}
