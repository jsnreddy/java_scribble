package com.jsn.test;

import java.util.Scanner;

public class SquareNumbers {

	public static boolean isSquareNumber(Integer num) {
		Double numSqrt = Math.sqrt(num);
		if (numSqrt - numSqrt.intValue() == 0.0) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		@SuppressWarnings("resource")
		Scanner inputReader = new Scanner(System.in);
		Integer testCases = 0;

		testCases = inputReader.nextInt();
		//System.out.println("testCases : " + testCases + "\n");
		for (int i = 0; i < testCases; ++i) {
			Integer lowNumber = inputReader.nextInt();
			Integer highNumber = inputReader.nextInt();
			Double lowsqrt = Math.sqrt(lowNumber);
			Double highsqrt = Math.sqrt(highNumber);
			Double diff = Math.floor(highsqrt) - Math.ceil(lowsqrt);
			System.out.println(diff.intValue() + 1);
			//			Double output = Math.floor(diff);
			//			if (isSquareNumber(lowNumber)) {
			//				if (isSquareNumber(highNumber)) {
			//					System.out.println(output.intValue() + 1);
			//				} else {
			//					System.out.println(output.intValue() + 1);
			//				}
			//			} else {
			//				if (isSquareNumber(highNumber)) {
			//					System.out.println(output.intValue() + 1);
			//				} else {
			//					System.out.println(output.intValue());
			//				}
			//			}

		}
	}

}
