package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.PetPhysicalInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PetPhysicalInfoService {

    @POST("pet/physical/get")
    Call<PetPhysicalInfo> get(@Query("petId") int petId);


    @POST("pet/physical/regist")
    Call<Integer> regist(@Body PetPhysicalInfo petPhysicalInfo);


    @POST("pet/physical/delete")
    Call<Integer> delete(@Query("petId") int petId);
}
