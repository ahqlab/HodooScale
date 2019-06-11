package com.animal.scale.hodoo.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class PetWeightInfo implements Serializable{

	private int id;

	private int bcs;

	private String createDate;

	private String selectedBfi;
}