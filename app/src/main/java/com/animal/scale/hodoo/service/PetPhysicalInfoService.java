package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.PetPhysicalInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PetPhysicalInfoService {

    @POST("pet/physical/get.do")
    Call<PetPhysicalInfo> getPhysicalIformation(@Query("groupCode") String groupCode, @Query("petIdx") int petIdx);


    @POST("android/pet/physical/regist.do")
    Call<CommonResponce<Integer>> regist(@Query("petIdx") int petIdx, @Query("groupCode") String groupCode, @Body PetPhysicalInfo petPhysicalInfo);

    @POST("android/pet/physical/update.do")
    Call<CommonResponce<PetPhysicalInfo>> update(@Body PetPhysicalInfo petPhysicalInfo);


    @POST("pet/physical/delete.do")
    Call<Integer> delete(@Query("petIdx") int petIdx, @Query("id") int id);
}
