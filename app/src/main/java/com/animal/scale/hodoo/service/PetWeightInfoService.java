package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.PetWeightInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PetWeightInfoService {

    @POST("pet/weight/get")
    Call<PetWeightInfo> get(@Query("petId") int petId);


    @POST("pet/weight/regist")
    Call<Integer> regist(@Body PetWeightInfo petWeightInfo);


    @POST("pet/weight/my/bcs")
    Call<String> getMyBcs(@Query("groupId") String groupId);


}
