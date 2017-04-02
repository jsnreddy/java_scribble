/**
 * 
 */
package com.jsn.codingPractice;

import java.util.Scanner;

/**
 * @author abzooba
 *
 */
public class SherlockBeast {

	public static void printNumbers(Integer num, Integer n) {
		for (int i = 0; i < n; ++i) {
			System.out.print(num);
		}
	}

	public static void printOutput(Integer output) {
		System.out.println(output);
	}

	public static void printOutput(Integer outputFives, Integer outputThrees) {
		printNumbers(5, outputFives);
		printNumbers(3, outputThrees);
		System.out.println();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		@SuppressWarnings("resource")
		Scanner inputReader = new Scanner(System.in);
		Integer testCases = 0;

		testCases = inputReader.nextInt();
		//		System.out.println("testCases : " + testCases + "\n");
		for (int i = 0; i < testCases; ++i) {
			Integer nDigits = inputReader.nextInt();
			if (nDigits < 3) {
				printOutput(-1);
			} else {
				if (nDigits % 3 == 0) {
					printOutput(nDigits, 0);
				} else if (nDigits % 3 == 1) {
					if (nDigits < 10) {
						printOutput(-1);
					} else {
						printOutput(nDigits - 10, 10);
					}
				} else if (nDigits % 3 == 2) {
					printOutput(nDigits - 5, 5);
				} else if (nDigits % 5 == 0) {
					printOutput(0, nDigits);
				}
			}
		}
	}

}
