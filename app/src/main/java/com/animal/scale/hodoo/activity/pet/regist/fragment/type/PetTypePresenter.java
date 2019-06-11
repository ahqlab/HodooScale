package com.animal.scale.hodoo.activity.pet.regist.fragment.type;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;

/**
 * Created by SongSeokwoo on 2019-04-09.
 */
public class PetTypePresenter implements PetTypeIn.Presenter {
    private PetTypeIn.View view;
    private PetTypeModel model;
    PetTypePresenter ( PetTypeIn.View view ) {
        this.view = view;
        model = new PetTypeModel();
    }
    @Override
    public void getType(int petIdx) {
        model.getPetType(petIdx, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer petType) {
                if ( petType != null )
                    view.setType(petType);
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
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void setNavigation() {
        view.setNavigation();
    }
}
