package com.animal.scale.hodoo.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Feed implements Serializable {

	private int id;
	private String animalType;
	private String tag;
	private String brand;
	private String name;
	private String manufacturer;
	private String age;
	private float calorie;
	private float calculationCalories;
	private float crudeProtein;
	private float crudeFat;
	private float carbohydrate;
	private float crudeAsh;
	private float crudeFiber;
	private float taurine;
	private float moisture;
	private float calcium;
	private float phosphorus;
	private float omega3;
	private float omega6;
	private String mainIngredient;

}
