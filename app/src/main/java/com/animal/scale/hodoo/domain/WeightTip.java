package com.animal.scale.hodoo.domain;

import lombok.Data;

@Data
public class WeightTip implements Domain{

	public WeightTip() {
	}

	public WeightTip(String country, int bcs) {
		this.language = country;
		this.bcs = bcs;
	}

	private int tipIdx;
	
	private String language;
	
	private int bcs;
	
	private String title;
	
	private String content;


}
