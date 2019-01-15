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

	//return 나이
	@RequiresApi(api = Build.VERSION_CODES.O)
	public int currentYear(){
//		if ( android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
//
//		}
		return getPeriod().getYears();
	}

	//return 개월수
	@RequiresApi(api = Build.VERSION_CODES.O)
	public int currentMonth(){
		if ( android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
			Log.e("songseokwooooooooo", String.format("getPeriod().getMonths() : %d", getPeriod().getMonths()));
			return getPeriod().getMonths();
		} else {
			Log.e("songseokwooooooooo", String.format("getCompareMonth() : %d", getCompareMonth()));
			return (int) getCompareMonth();
		}

	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	private Period getPeriod(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
		LocalDate today = LocalDate.now();
		LocalDate birthday = LocalDate.parse(getBirthday(), formatter);

		Period p = Period.between(birthday, today);
		return p;
	}

	private long getCompareMonth() {
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = new Date();
		try {
			Date eDate = fm.parse(getBirthday());
			String startDate = fm.format(eDate);
			String sd2 = startDate.substring(4, 6);

			long diff = eDate.getTime() - sDate.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000) ;

			long difMonth = (diffDays+1)/30;
			long chkNum = 0;

			int j=0;
			for(int i=Integer.parseInt(sd2); j<difMonth; i++) {
				if(i==1 || i==3 || i==5 || i==7 || i==8 || i==10 || i==12 ) {
					chkNum += 31;
				}else if(i==4 || i==6 || i==9 || i==11 ) {
					chkNum += 30;
				}
				if(i==2) {
					if( ((Integer.parseInt(sd2))%400) == 0 ) {
						chkNum+=29;
					} else {
						chkNum+=28;
					}
				}
				j++;
				if(i>12) { i=1; j=j-1;}
			}
			return Math.abs(difMonth);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return 0;
	}
	//중성화 여부 (YES || NO)
	private String neutralization;
}
