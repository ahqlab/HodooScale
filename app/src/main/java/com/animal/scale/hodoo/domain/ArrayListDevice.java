package com.animal.scale.hodoo.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class ArrayListDevice {

    @SerializedName("devices")
    @Expose
    private List<Device> devices;

    public ArrayListDevice(List<Device> devices) {
        this.devices = devices;
    }
}
