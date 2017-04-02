/**
 * 
 */
package com.jsn.codingPractice;

import java.util.Arrays;

/**
 * @author Surendranath Reddy
 *
 */
public class PascalTriangle {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int n = 4;
		int[] result = new int[n + 1];

		for (int i = 0; i < n + 1; ++i) {
			if (i == 0) {
				result[i] = 1;
				continue;
			}
			result[i] = (result[i - 1] * (n + 1 - i)) / i;
		}

		System.out.println(Arrays.toString(result));
	}

}
