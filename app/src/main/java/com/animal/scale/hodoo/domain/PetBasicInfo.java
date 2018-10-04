package com.animal.scale.hodoo.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class PetBasicInfo implements Serializable{

	private int id;

	private String profileFilePath;

	private String profileFileName;

	private String sex;

	private String petName;

	private String petBreed;

	private String birthday;

	private String neutralization;
}
