package com.jsn.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CutTheSticks {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		@SuppressWarnings("resource")
		Scanner inputReader = new Scanner(System.in);
		Integer testCases = 0;

		List<Integer> lengths = new ArrayList<Integer>();
		testCases = inputReader.nextInt();
		//System.out.println("testCases : " + testCases + "\n");
		for (int i = 0; i < testCases; ++i) {
			Integer inLength = inputReader.nextInt();
			lengths.add(inLength);
		}
		Comparator comp = Collections.reverseOrder();
		Collections.sort(lengths, comp);
		//		Collections.reverse(lengths);

		Integer nLeft = lengths.size();
		Integer min = lengths.get(nLeft - 1);
		//		System.out.println("nLeft : " + nLeft);
		while (nLeft > 1) {
			//			System.out.println("Array : " + lengths);
			System.out.println(nLeft);

			//			System.out.println("Min : " + min);
			for (int i = 0; i < nLeft; ++i) {
				lengths.set(i, lengths.get(i) - min);
			}
			for (int j = 0; j < nLeft; ++j) {
				if (lengths.get(j) <= 0) {
					nLeft = j;
					break;
				}
			}
			min = lengths.get(nLeft - 1);
		}
		System.out.println(nLeft);
	}

}
