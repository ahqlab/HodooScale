package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.domain.PetUserSelectionQuestion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetRetrofit {

    private static NetRetrofit ourInstance = new NetRetrofit();

    public static NetRetrofit getInstance() {
        return ourInstance;
    }

    private NetRetrofit() {

    }
    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(HodooConstant.LIMITED_TIME, TimeUnit.SECONDS)
            .readTimeout(HodooConstant.LIMITED_TIME, TimeUnit.SECONDS)
            .writeTimeout(HodooConstant.LIMITED_TIME, TimeUnit.SECONDS)
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SharedPrefVariable.SERVER_ROOT)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()) // 파싱등록
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    UserService service = retrofit.create(UserService.class);
    PetBasicInfoService petBasicInfoService = retrofit.create(PetBasicInfoService.class);
    PetChronicDiseaseService petChronicDiseaseService = retrofit.create(PetChronicDiseaseService.class);
    PetPhysicalInfoService petPhysicalInfoService = retrofit.create(PetPhysicalInfoService.class);
    PetWeightInfoService petWeightInfoService = retrofit.create(PetWeightInfoService.class);
    RealTimeWeightService realTimeWeightService = retrofit.create(RealTimeWeightService.class);
    PetGroupMappingService petGroupMappingService = retrofit.create(PetGroupMappingService.class);
    DeviceService getDeviceService = retrofit.create(DeviceService.class);
    PetService getPetService = retrofit.create(PetService.class);
    CountryService countryService = retrofit.create(CountryService.class);
    InvitationService invitationService = retrofit.create(InvitationService.class);
    FcmService fcmService = retrofit.create(FcmService.class);

    FeedService feedService = retrofit.create(FeedService.class);

    MealHistoryService mealHistoryService = retrofit.create(MealHistoryService.class);
    ActivityService activityService = retrofit.create(ActivityService.class);
    FeederService feederService = retrofit.create(FeederService.class);
    WeightTipService weightTipService =  retrofit.create(WeightTipService.class);
    MealTipService mealTipService =  retrofit.create(MealTipService.class);
    AppVersionService appVersionService =  retrofit.create(AppVersionService.class);
    AlarmItemService alarmItemService =  retrofit.create(AlarmItemService.class);
    NoticeService noticeService =  retrofit.create(NoticeService.class);
    BodyFatRiskService bodyFatRiskService = retrofit.create(BodyFatRiskService.class);

    PetUserSelectQuestionService petUserSelectionQuestionService = retrofit.create(PetUserSelectQuestionService.class);


    public UserService getUserService() {
        return service;
    }

    public PetBasicInfoService getPetBasicInfoService() {
        return petBasicInfoService;
    }
    public InvitationService getInvitationService() {
        return invitationService;
    }

    public PetChronicDiseaseService getPetChronicDiseaseService() { return petChronicDiseaseService; }

    public PetPhysicalInfoService getPetPhysicalInfoService() { return petPhysicalInfoService; }

    public PetWeightInfoService getPetWeightInfoService() { return petWeightInfoService; }

    public RealTimeWeightService getRealTimeWeightService() { return realTimeWeightService; }

    public PetGroupMappingService getPetGroupMappingService() { return petGroupMappingService; }

    public DeviceService getDeviceService() { return getDeviceService; }

    public PetService getPetService() { return getPetService; }

    public FeedService getFeedService() { return feedService; }

    public MealHistoryService getMealHistoryService() { return mealHistoryService; }

    public ActivityService getActivityService() { return activityService; }

    public CountryService getCountryService() {
        return countryService;
    }

    public FeederService getFeederService() {
        return feederService;
    }

    public FcmService getFcmService() {
        return fcmService;
    }

    public WeightTipService getWeightTipService() {
        return weightTipService;
    }

    public MealTipService getMealTipService() {
        return mealTipService;
    }

    public AppVersionService getAppVersionService() { return appVersionService; }

    public AlarmItemService getAlarmItemService() { return alarmItemService; }

    public NoticeService getNoticeService() { return noticeService; }

    public BodyFatRiskService getBodyFatRiskService () { return bodyFatRiskService; }

    public PetUserSelectQuestionService getPetUserSelectionQuestionService() {return petUserSelectionQuestionService;}
}

