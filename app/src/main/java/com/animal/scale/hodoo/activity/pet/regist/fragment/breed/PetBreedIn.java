package com.animal.scale.hodoo.activity.pet.regist.fragment.breed;

import android.content.Context;

import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.PetBreed;

import java.util.List;

/**
 * Created by SongSeokwoo on 2019-05-14.
 */
public interface PetBreedIn {
    interface View {
        void getAllPetBreed(CommonResponce<List<PetBreed>> breeds);
    }
    interface Presenter {
        void loadData(Context context);
        void getAllPetBreed(String location, int typeIdx);
    }
}
