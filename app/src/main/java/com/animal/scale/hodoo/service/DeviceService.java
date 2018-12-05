package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.ArrayListDevice;
import com.animal.scale.hodoo.domain.Device;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DeviceService {

    @POST("device/my/device/list")
    Call<List<Device>> getMyDeviceList(@Query("groupCode") String groupCode);

    @POST("device/insert/device")
    Call<Integer> insertDevice(@Body Device device);

    @POST("device/get/last/collection/data")
    Call<List<Device>> getLastCollectionData(@Query("device[]") List<Device> list);

    @POST("device/change/connect/status")
    Call<Integer> setChangeSwithStatus(@Query("deviceIdx") int deviceIdx , @Query("connect") boolean b);
}
