package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.PetPhysicalInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PetPhysicalInfoService {

    @POST("pet/physical/get.do")
    Call<PetPhysicalInfo> getPhysicalIformation(@Query("groupCode") String groupCode, @Query("petIdx") int petIdx);


    @POST("pet/physical/regist.do")
    Call<Integer> regist(@Query("petIdx") int petIdx, @Body PetPhysicalInfo petPhysicalInfo);


    @POST("pet/physical/delete.do")
    Call<Integer> delete(@Query("petIdx") int petIdx, @Query("id") int id);
}
