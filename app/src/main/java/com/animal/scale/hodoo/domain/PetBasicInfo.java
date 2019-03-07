package com.animal.scale.hodoo.domain;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

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

	private int currentYear;

	private int currentMonth;

	//중성화 여부 (YES || NO)
	private String neutralization;
}
