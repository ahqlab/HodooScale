package com.animal.scale.hodoo.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseService {

    protected static Object retrofit(Class<?> className)
    {
        String host = "http://192.168.0.49:8181/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(className);
    }

}
