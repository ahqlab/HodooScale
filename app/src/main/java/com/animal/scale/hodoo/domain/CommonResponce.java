package com.animal.scale.hodoo.domain;

import com.animal.scale.hodoo.message.ResultMessage;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonResponce<D> implements Domain {
	
	public ResultMessage resultMessage;

	public D domain;

	public int status;

	public String error;

	public String exception;

	public String message;

	public String path;
}
