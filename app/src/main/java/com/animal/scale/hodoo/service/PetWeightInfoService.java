package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.PetWeightInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PetWeightInfoService {

    @POST("pet/weight/get")
    Call<PetWeightInfo> getPetWeightInformation(@Query("groupCode") String groupId, @Query("petIdx") int petId);

    @POST("pet/weight/delete")
    Call<Integer> delete(@Query("petIdx") int petIdx, @Query("id") int id);

    @POST("pet/weight/regist")
    Call<Integer> regist(@Query("petIdx") int petIdx, @Body PetWeightInfo petWeightInfo);

    @POST("pet/weight/bcs")
    Call<PetWeightInfo> getBcs(@Query("basicIdx") int basicIdx);




}
