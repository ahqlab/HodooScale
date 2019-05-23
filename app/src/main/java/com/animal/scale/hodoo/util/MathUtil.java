package com.animal.scale.hodoo.util;

import java.text.DecimalFormat;
import java.util.Random;

public class MathUtil {

	private static Random random = new Random();

	public static int random(int num) {
		return random.nextInt(num);
	}

	public static String getGroupId() {
		String tempPassword = "";
		for (int i = 0; i < 12; i++) {
			int rndVal = (int) (Math.random() * 62);
			if (rndVal < 10) {
				tempPassword += rndVal;
			} else if (rndVal > 35) {
				tempPassword += (char) (rndVal + 61);
			} else {
				tempPassword += (char) (rndVal + 55);
			}
		}
		//out.println("tempPassword : " + tempPassword);
		return tempPassword;
	}

	public static String DecimalCut(float value){
		DecimalFormat fmt = new DecimalFormat("0.#");
		String decimal =  fmt.format(value);
		return decimal;
	}

	public static String DecimalCut(double value){
		DecimalFormat fmt = new DecimalFormat("0.#");
		String decimal =  fmt.format(value);
		return decimal;
	}

	public static double cmToInch(double cm){
		return cm/2.54;
	}

	public static double InchToCm(double Inch){
		return Inch * 2.54;
	}

	public static float kgTolb( float kg ) {
		return (kg * (1f / 2.2f));
	}
	public static float lbToKg( float lb ) {
		return ( lb * (1f * 2.2f) );
	}
}
