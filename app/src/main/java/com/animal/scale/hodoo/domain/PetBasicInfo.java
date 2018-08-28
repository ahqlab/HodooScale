package com.animal.scale.hodoo.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class PetBasicInfo implements Serializable{

	@SerializedName("id")
	@Expose
	private int id;

	@SerializedName("userId")
	@Expose
	private int userId;

	@SerializedName("profileFilePath")
	@Expose
	private String profileFilePath;

	@SerializedName("profileFileName")
	@Expose
	private String profileFileName;

	@SerializedName("sex")
	@Expose
	private String sex;

	@SerializedName("petName")
	@Expose
	private String petName;

	@SerializedName("petBreed")
	@Expose
	private String petBreed;

	@SerializedName("birthday")
	@Expose
	private String birthday;

	@SerializedName("neutralization")
	@Expose
	private String neutralization;
}
