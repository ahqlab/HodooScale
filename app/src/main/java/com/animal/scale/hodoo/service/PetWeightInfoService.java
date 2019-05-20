package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.BfiModel;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.PetWeightInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PetWeightInfoService {

    @POST("pet/weight/get.do")
    Call<PetWeightInfo> getPetWeightInformation(@Query("groupCode") String groupId, @Query("petIdx") int petId);

    @POST("pet/weight/delete.do")
    Call<Integer> delete(@Query("petIdx") int petIdx, @Query("id") int id);

    @POST("android/pet/weight/regist.do")
    Call<CommonResponce<Integer>> regist(@Query("petIdx") int petIdx, @Body PetWeightInfo petWeightInfo);

    @POST("pet/weight/bcs.do")
    Call<PetWeightInfo> getBcs(@Query("basicIdx") int basicIdx);

    @POST("pet/weight/bfi.do")
    Call<List<BfiModel>> getBfiQuestion(@Query("location") String location, @Query("type") int type);

    @POST("android/pet/weight/getWeekRate.do")
    Call<CommonResponce<Float>> getWeekRate( @Query("groupCode") String groupCode );

}
