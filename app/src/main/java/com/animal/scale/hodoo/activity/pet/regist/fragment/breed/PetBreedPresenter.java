package com.animal.scale.hodoo.activity.pet.regist.fragment.breed;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.PetBreed;

import java.util.List;

/**
 * Created by SongSeokwoo on 2019-05-14.
 */
public class PetBreedPresenter implements PetBreedIn.Presenter {
    private PetBreedIn.View view;
    private PetBreedModel model;
    PetBreedPresenter ( PetBreedIn.View view ) {
        this.view = view;
        model = new PetBreedModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }
    @Override
    public void getAllPetBreed(String location, int typeIdx) {
        model.getAllPetBreed(location, typeIdx, new CommonModel.ObjectCallBackListner<CommonResponce<List<PetBreed>>>() {
            @Override
            public void doPostExecute(CommonResponce<List<PetBreed>> d) {
                view.getAllPetBreed(d);
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
