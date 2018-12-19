package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.Weatherbit;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ActivityService {
    @GET
    Call<Weatherbit> getWeather(@Url String url, @Query("lat") String lat, @Query("lon") String lon);
}
