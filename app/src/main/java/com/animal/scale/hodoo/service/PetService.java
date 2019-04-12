package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.domain.PetBreed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PetService {

    @POST("pet/my/pet/list.do")
    Call<List<Pet>> getMyPetList(@Query("groupCode") String groupCode);

    @POST("pet/my/pet/listResult.do")
    Call<Integer []> getMyPetResult(@Query("groupCode") String groupCode);

    @POST("android/pet/exist/my/pet.do")
    Call<CommonResponce<Integer>> getExistMyPet(@Query("groupCode") String groupCode);

    @POST("pet/my/pet/myPetFirstIdx.do")
    Call<Integer> myPetFirstIdx(@Query("groupCode") String groupCode);

    @POST("pet/basic/get.do")
    Call<PetBasicInfo> getBasicInformation(@Query("groupCode") String groupCode, @Query("petIdx") int petIdx, @Query("location") String location);

    @POST("pet/all/infos.do")
    Call<PetAllInfos> petAllInfos(@Query("petIdx") int petIdx);

    @POST("pet/all/getBreed.do")
    Call<List<PetBreed>> getAllBreed( @Query("location") String location );

    @POST("pet//make/it/invisible.do")
    Call<CommonResponce<Integer>> deletePet(@Query("petIdx") int petIdx);
}
