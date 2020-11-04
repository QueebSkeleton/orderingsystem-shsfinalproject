package model;

import java.util.Random;

public class ColorHandler {

	public static String getRandomHexColor() {
		String generatedCode = "#";
		
		String[] hexCodes = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
		Random rand = new Random();
		
		for(int i = 0; i < 6; i++) {
			generatedCode += hexCodes[rand.nextInt(hexCodes.length)];
		}
		
		return generatedCode;
	}
	
	public static String getRandomRGBAColor() {
		String generatedColor = "rgba(";
		
		Random rand = new Random();
		
		for(int i = 0; i < 3; i++) {
			generatedColor += rand.nextInt(255) + ",";
		}
		
		generatedColor += "0.3)";
		
		return generatedColor;
	}
	
}
