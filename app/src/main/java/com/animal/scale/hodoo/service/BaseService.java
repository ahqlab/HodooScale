package com.animal.scale.hodoo.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseService {

    protected static Object retrofit(Class<?> className)
    {
        String host = "http://121.183.234.14:7070/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(className);
    }

}
