package com.animal.scale.hodoo.domain;

import com.animal.scale.hodoo.base.FragmentTip;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class WeightTip  extends FragmentTip implements Domain{

	public WeightTip() {
	}

	public WeightTip(String language, int bcs) {
		this.language = language;
		this.bcs = bcs;
	}

	private int tipIdx;
	
	private String language;
	
	private int bcs;
	
	private String title;
	
	private String content;


}
