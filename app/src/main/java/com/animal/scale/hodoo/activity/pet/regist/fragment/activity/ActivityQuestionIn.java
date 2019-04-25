package com.animal.scale.hodoo.activity.pet.regist.fragment.activity;

import android.content.Context;

import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.PetUserSelectionQuestion;

/**
 * Created by SongSeokwoo on 2019-04-24.
 */
public interface ActivityQuestionIn {
    interface View {
        void setPetUserSelectQuestion (CommonResponce<PetUserSelectionQuestion> petUserSelectQuestion);
        void setNavigation();
    }
    interface Presenter {
        void loadData(Context context);
        void getPetUserSelectQuestion( int petIdx );
    }
}
