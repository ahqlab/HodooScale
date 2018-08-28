package com.animal.scale.hodoo.domain;

import lombok.Data;

@Data
public class PetPhysicalInfo{
	
	private int id;
	
	private int petId;
	
	private String width;
	
	private String height;
	
	private String weight;
	
	private String createDate;

	public PetPhysicalInfo(int petId) {
		this.petId = petId;
	}
}
