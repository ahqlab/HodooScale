package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.PetUserSelectionQuestion;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by SongSeokwoo on 2019-04-24.
 */
public interface PetUserSelectQuestionService {
    @POST("android/pet/question/get.do")
    Call<CommonResponce<PetUserSelectionQuestion>> getPetUserSelectQuestion(@Query("groupCode") String groupCode, @Query("petIdx") int petIdx );
}
