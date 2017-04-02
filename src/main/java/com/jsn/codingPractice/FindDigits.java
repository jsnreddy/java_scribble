package com.jsn.codingPractice;

import java.util.Scanner;

public class FindDigits {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		@SuppressWarnings("resource")
		Scanner inputReader = new Scanner(System.in);
		Integer testCases = 0;

		testCases = inputReader.nextInt();
		System.out.println("testCases : " + testCases + "\n");
		for (int i = 0; i < testCases; ++i) {
			//			String input = inputReader.nextLine();
			Integer inputNumber = inputReader.nextInt();
			String input = inputNumber.toString();
			Integer output = 0;
			for (int j = 0; j < input.length(); ++j) {
				Integer inputDigit = Integer.parseInt(Character.toString(input.charAt(j)));
				if (inputDigit != 0 && inputNumber % inputDigit == 0) {
					output++;
				}
			}
			System.out.println(output);
		}
	}

}
