package com.animal.scale.hodoo.domain;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import lombok.Data;

@Data
public class PetBasicInfo implements Serializable{

	//구분자
	private int id;
	//이미지 경로
	private String profileFilePath;
	//이미지 이름
	private String profileFileName;
	//성병
	private String sex;
	//반려견 이름
	private String petName;
	//견종
	private String petBreed;
	//생일
	private String birthday;

	//return 나이
	@RequiresApi(api = Build.VERSION_CODES.O)
	public int currentYear(){
		return getPeriod().getYears();
	}

	//return 개월수
	@RequiresApi(api = Build.VERSION_CODES.O)
	public int currentMonth(){
		return getPeriod().getMonths();
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	private Period getPeriod(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
		LocalDate today = LocalDate.now();
		LocalDate birthday = LocalDate.parse(getBirthday(), formatter);

		Period p = Period.between(birthday, today);
		return p;
	}
	//중성화 여부 (YES || NO)
	private String neutralization;
}
