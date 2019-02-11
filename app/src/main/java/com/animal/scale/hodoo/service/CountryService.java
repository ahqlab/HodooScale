package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CountryService {
    @GET("country/{country}/getAllCountry.do")
    Call<List<Country>> getAllCountry(@Path(value = "country") int country);
}
