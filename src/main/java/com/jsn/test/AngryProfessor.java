package com.jsn.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AngryProfessor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		@SuppressWarnings("resource")
		Scanner inputReader = new Scanner(System.in);
		Integer testCases = 0;

		testCases = inputReader.nextInt();
		//		System.out.println("testCases : " + testCases + "\n");
		for (int i = 0; i < testCases; ++i) {
			List<Integer> intime = new ArrayList<Integer>();
			Integer nStudents = inputReader.nextInt();
			Integer nThreshold = inputReader.nextInt();
			Integer nOntime = 0;
			for (int j = 0; j < nStudents; ++j) {
				Integer temp = inputReader.nextInt();
				if (temp <= 0) {
					nOntime++;
				}
				intime.add(temp);
			}
			if (nOntime >= nThreshold) {
				System.out.println("NO");
			} else {
				System.out.println("YES");
			}
			//			System.out.println("nStudents : " + nStudents + "\n");
			//			System.out.println("nThreshold : " + nThreshold + "\n");
			//			System.out.println("intimes : " + intime + "\n");
		}

	}

}
