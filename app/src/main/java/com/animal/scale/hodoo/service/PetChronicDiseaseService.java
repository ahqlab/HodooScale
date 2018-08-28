package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.PetChronicDisease;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PetChronicDiseaseService {

    @POST("chronic/desease/delete")
    Call<Integer> delete(@Query("petId") int petId);


    @POST("chronic/desease/regist")
    Call<Integer> insert(@Body PetChronicDisease petChronicDisease);


    @POST("chronic/desease/list")
    Call<List<PetChronicDisease>> list(@Query("petId") int petId);
}
