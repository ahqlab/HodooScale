package com.animal.scale.hodoo.domain;

import com.animal.scale.hodoo.message.ResultMessage;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonResponce<D extends Domain> implements Domain {
	
	public ResultMessage resultMessage;
	
	public D domain;
}
