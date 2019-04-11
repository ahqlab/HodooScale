package com.animal.scale.hodoo.domain;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
public class PetPhysicalInfo implements Serializable{
	
	private int id;

	private String width;
	
	private String height;

	private String weight;
	
	private String createDate;
}
