package com.animal.scale.hodoo.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class RealTimeWeight implements Serializable {
	
	private int id;
	
	private String mac;
	
	private float value;
	
	private String createDate;

}