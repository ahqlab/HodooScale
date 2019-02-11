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

    @POST("device/my/device/list.do")
    Call<List<Device>> getMyDeviceList(@Query("groupCode") String groupCode);

    @POST("device/insert/device.do")
    Call<Integer> insertDevice(@Body Device device);

    @POST("device/get/last/collection/data.do")
    Call<List<Device>> getLastCollectionData(@Query("device[]") List<Device> list);

    @POST("device/change/connect/status.do")
    Call<Integer> setChangeSwithStatus(@Query("deviceIdx") int deviceIdx , @Query("connect") boolean b);

    @POST("device/change/connection.do")
    Call<Integer> setChangeRegisted(@Query("groupCode") String groupCode, @Query("deviceIdx") int deviceIdx , @Query("isDel") boolean isDel);
}
