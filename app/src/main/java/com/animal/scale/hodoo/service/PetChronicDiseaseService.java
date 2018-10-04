package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.PetChronicDisease;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PetChronicDiseaseService {

    @POST("chronic/desease/delete")
    Call<Integer> delete(@Query("petIdx") int petIdx, @Query("diseaseIdx") int diseaseIdx);


    @POST("chronic/desease/list")
    Call<List<PetChronicDisease>> list(@Query("groupId") int groupId);


    @POST("chronic/desease/get")
    Call<PetChronicDisease> getDiseaseformation(@Query("groupCode") String groupCode, @Query("petIdx") int petIdx);


    @POST("chronic/desease/regist")
    Call<Integer> registDiseaseformation(@Body PetChronicDisease domain,  @Query("petIdx") int petIdx);
}
