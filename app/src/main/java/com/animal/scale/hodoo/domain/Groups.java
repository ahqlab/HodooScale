package com.animal.scale.hodoo.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class Groups implements Serializable {

	public Groups(int userId, String groupId) {
		this.userId = userId;
		this.groupId = groupId;
	}

	@SerializedName("id")
	@Expose
	private int id;

	@SerializedName("userId")
	@Expose
	private int userId;

	@SerializedName("groupId")
	@Expose
	private String groupId;

	@SerializedName("petId")
	@Expose
	private int petId;
}
