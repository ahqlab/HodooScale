package com.animal.scale.hodoo.domain;

import lombok.Data;

@Data
public class HodooIndex implements Domain {

	private float hodooIndex;
	
	private String hodooIndexDesc;

	private float maxWeightStd;
	
	private float minWeightStd;
	
	private float weightStd;

}
