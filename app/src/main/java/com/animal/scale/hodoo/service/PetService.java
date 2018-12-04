package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.PetBasicInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PetService {

    @POST("pet/my/pet/list")
    Call<List<Pet>> getMyPetList(@Query("groupCode") String groupCode);


    @POST("pet/basic/get")
    Call<PetBasicInfo> getBasicInformation(@Query("groupCode") String groupCode, @Query("petIdx") int petIdx);

    @POST("pet/all/infos")
    Call<PetAllInfos> petAllInfos(@Query("petIdx") int petIdx);
}
