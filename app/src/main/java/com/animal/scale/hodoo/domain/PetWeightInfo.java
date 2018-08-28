package com.animal.scale.hodoo.domain;

import lombok.Data;

@Data
public class PetWeightInfo{

	public PetWeightInfo() {
	}
	public PetWeightInfo(int petId) {
		this.petId = petId;
	}

	private int id;

	private int petId;

	private int bcs;

	private String createDate;
}