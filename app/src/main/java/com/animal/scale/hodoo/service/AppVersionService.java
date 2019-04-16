package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.AppVersion;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AppVersionService {

    @POST("app/version/import.do")
    Call<CommonResponce<AppVersion>> appVersionImport();
}
