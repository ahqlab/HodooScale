package com.animal.scale.hodoo.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetRetrofit {
    private static NetRetrofit ourInstance = new NetRetrofit();

    public static NetRetrofit getInstance() {
        return ourInstance;
    }

    private NetRetrofit() {

    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://121.183.234.14:7171/hodoo/")
            .addConverterFactory(GsonConverterFactory.create()) // 파싱등록
            .build();

    UserService service = retrofit.create(UserService.class);
    PetBasicInfoService petBasicInfoService = retrofit.create(PetBasicInfoService.class);
    GroupsService groupsService = retrofit.create(GroupsService.class);
    PetChronicDiseaseService petChronicDiseaseService = retrofit.create(PetChronicDiseaseService.class);
    PetPhysicalInfoService petPhysicalInfoService = retrofit.create(PetPhysicalInfoService.class);
    PetWeightInfoService petWeightInfoService = retrofit.create(PetWeightInfoService.class);
    RealTimeWeightService realTimeWeightService = retrofit.create(RealTimeWeightService.class);

    public UserService getUserService() {
        return service;
    }

    public PetBasicInfoService getPetBasicInfoService() {
        return petBasicInfoService;
    }

    public GroupsService getGroupsService() {
        return groupsService;
    }

    public PetChronicDiseaseService getPetChronicDiseaseService() { return petChronicDiseaseService; }

    public PetPhysicalInfoService getPetPhysicalInfoService() { return petPhysicalInfoService; }

    public PetWeightInfoService getPetWeightInfoService() { return petWeightInfoService; }

    public RealTimeWeightService getRealTimeWeightService() { return realTimeWeightService; }
}

