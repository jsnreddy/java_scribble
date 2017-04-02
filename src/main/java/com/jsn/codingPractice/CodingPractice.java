/**
 * 
 */
package com.jsn.codingPractice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

/**
 * @author Surendranath Reddy
 *
 */
public class CodingPractice {

	public void pascalTriangle(int n) {

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

	public void nProduct() {
		int[] input = { 1, 2, 3, 4, 5, 6 };
		int[] result = new int[input.length];

		int product = 1;

		for (int i = 0; i < input.length; ++i) {
			product *= input[i];
		}

		for (int i = 0; i < input.length; ++i) {
			result[i] = product / input[i];
		}

		System.out.println("Input : " + Arrays.toString(input));
		System.out.println("Output : " + Arrays.toString(result));

	}

	class Interval {
		int start;
		int end;

		@Override
		public String toString() {
			return "(" + this.start + "," + this.end + ")";
		}
	}

	class startTimeComparator implements Comparator<Interval> {

		@Override
		public int compare(Interval a, Interval b) {
			if (a.start < b.start) {
				return -1;
			} else {
				return 1;
			}
		}
	}

	public void mergeIntervals() {
		int[][] intervals = { { 1, 3 }, { 2, 6 }, { 8, 10 }, { 15, 18 } };

		List<Interval> intervalList = new ArrayList<Interval>();
		for (int i = 0; i < intervals.length; ++i) {
			Interval val = new Interval();
			val.start = intervals[i][0];
			val.end = intervals[i][1];
			intervalList.add(val);
		}

		System.out.println("Input Intervals : " + intervalList.toString());
		Collections.sort(intervalList, new startTimeComparator());

		//		System.out.println("Sorted List : " + intervalList.toString());
		Stack<Interval> intStack = new Stack<Interval>();
		intStack.push(intervalList.get(0));

		for (int i = 1; i < intervalList.size(); ++i) {
			Interval top = intStack.peek();
			Interval present = intervalList.get(i);

			if (top.end < present.start) {
				intStack.push(present);
			} else if (top.end < present.end) {
				top.end = present.end;
				intStack.pop();
				intStack.push(top);
			}
			//			System.out.println(Arrays.toString(intStack.toArray()));
		}

		System.out.println("Merged Intervals : " + Arrays.toString(intStack.toArray()));
	}

	public void spiralMatrix() {
		//		int[][] input = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
		int[][] input = { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 }, { 11, 12, 13, 14, 15 } };
		int m = input.length;
		int n = input[0].length;

		int k = 0, l = 0;

		while (k < m && l < n) {
			for (int i = l; i < n; ++i) {
				System.out.print(input[k][i] + " ");
			}
			k++;
			for (int i = k; i < m; ++i) {
				System.out.print(input[i][n - 1] + " ");
			}
			n--;

			//			System.out.println();
			if (k < m) {
				for (int i = n - 1; i >= l; --i) {
					System.out.print(input[m - 1][i] + " ");
				}
				m--;
			}

			if (l < n) {
				for (int i = m - 1; i >= k; --i) {
					System.out.print(input[i][l] + " ");
				}
				l++;
			}

		}

	}

	/*
	 * This takes O(n^2) time and O(n^2) space - Dynamic Programming
	 */
	public void longestPalindromicSubstring() {
		String input = "abcgeeksfofskeegabc";
		//		String input = "gabbar";
		int n = input.length();
		int maxLength = 1;
		int start = 0;
		boolean[][] map = new boolean[n][n];

		for (int i = 0; i < n; ++i) {
			map[i][i] = true;
		}

		for (int i = 0; i < n - 1; ++i) {
			if (input.charAt(i) == input.charAt(i + 1)) {
				maxLength = 2;
				start = i;
				map[i][i + 1] = true;
			}
		}

		for (int k = 3; k <= n; ++k) {
			for (int i = 0; i < n - k + 1; ++i) {
				int j = i + k - 1;

				if (map[i + 1][j - 1] == true && input.charAt(i) == input.charAt(j)) {
					map[i][j] = true;
					if (k > maxLength) {
						maxLength = k;
						start = i;
					}

				}
			}
		}

		//printing matrix
		//		for (int i = 0; i < n; ++i) {
		//			for (int j = 0; j < n; ++j) {
		//				System.out.print(map[i][j] + " ");
		//			}
		//			System.out.println();
		//		}

		System.out.println("Longest SubString : " + input.substring(start, start + maxLength));
	}

	/*
	 * This takes O(n^2) time and O(1) space
	 */
	public void longestPalindromicSubstringV2() {
		//		String input = "abcgeeksfofskeegabc";
		String input = "gabbar";

		for (int i = 0; i < input.length() - 1; ++i) {
			extendSubstring(input, i, i);
			extendSubstring(input, i, i + 1);
		}

		System.out.println("Longest Palindrome - V2 : " + input.substring(start, start + maxLength));
	}

	public void extendSubstring(String input, int l, int h) {
		while (l >= 0 && h < input.length() && input.charAt(l) == input.charAt(h)) {
			l--;
			h++;
		}

		if (h - l - 1 > maxLength) {
			maxLength = h - l - 1;
			start = l + 1;
		}
	}

	private int start = 0;
	private int maxLength = 1;

	public void binarySearch() {
		int[] input = { 1, 5, 2, 3, 8, 6 };
		int length = input.length;
		int data = 6;

		int low = 0;
		int high = length - 1;

		Arrays.sort(input);

		System.out.println("Input sorted : " + Arrays.toString(input));
		//		bsIterative(input, length, data);
		boolean found = bsRecursive(input, low, high, data);
		if (found) {
			System.out.println(data + " found in " + Arrays.toString(input));
		} else {
			System.out.println(data + " not found in " + Arrays.toString(input));
		}
	}

	public void bsIterative(int[] input, int length, int data) {
		System.out.println("Iterative Binary Search");
		int low = 0;
		int high = length - 1;
		boolean found = false;

		while (low <= high) {
			int mid = (low + high) / 2;
			//			System.out.println("Mid : " + mid);
			if (input[mid] == data) {
				found = true;
				System.out.println("Found " + data + " at index " + mid + " in " + Arrays.toString(input));
				break;
			} else if (input[mid] > data) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		if (!found)
			System.out.println(data + " not found in " + Arrays.toString(input));

	}

	public boolean bsRecursive(int[] input, int low, int high, int data) {
		int mid = (low + high) / 2;

		if (input[mid] == data) {
			return true;
		} else if (input[mid] > data) {
			return bsRecursive(input, low, mid - 1, data);
		} else {
			return bsRecursive(input, mid + 1, high, data);
		}

	}

	public void secondSmallest() {
		int[] input = { 1, 1, 1, 1, 2 };

		int smallest = Integer.MAX_VALUE;
		int secondSmallest = Integer.MAX_VALUE;
		for (int i = 0; i < input.length; ++i) {
			if (input.length < 2) {
				System.out.println("Not possible");
			}

			if (input[i] < smallest) {
				secondSmallest = smallest;
				smallest = input[i];
			} else if (input[i] < secondSmallest && input[i] != smallest) {
				secondSmallest = input[i];
			}
		}

		if (secondSmallest == Integer.MAX_VALUE) {
			System.out.println("There is no second smallest element");
		}

		System.out.println("Smallest : " + smallest + "\tSecondSmallest : " + secondSmallest);
	}

	public void maxRepititions() {
		int[] input = { 1, 4, 2, 3, 3, 2 };
		int length = input.length;
		int max = Integer.MIN_VALUE;
		int maxIndex = Integer.MAX_VALUE;

		for (int i = 0; i < length; ++i) {
			input[input[i] % length] += length;
		}

		for (int i = 0; i < length; ++i) {
			if (input[i] / length >= max) {
				max = input[i] / length;
				maxIndex = i;
			}
		}

		System.out.println("Max Repititions : " + max + " repititions of : " + maxIndex);
	}

	public int intReversal(int n) {
		//		int n = 123445;
		int resultInt = 0;
		//		StringBuilder result = new StringBuilder("");

		while (n > 0) {
			int rem = n % 10;
			resultInt = resultInt * 10 + rem;
			n = n / 10;
			//			result.append(Integer.toString(rem));
		}

		System.out.println("ResultInt : " + resultInt);
		return resultInt;
		//		System.out.println("Result Str : " + result.toString());
		//		resultInt = Integer.parseInt(result.toString());
		//		System.out.println("Result : " + resultInt);
	}

	public void floatReversal() {
		String input = "123.456";
		//		float f = Float.parseFloat(input);

		String[] split = input.split("\\.");

		int before = Integer.parseInt(split[0]);
		int after = Integer.parseInt(split[1]);

		int revBefore = intReversal(before);
		int revAfter = intReversal(after);

		String output = String.valueOf(revBefore).concat(".").concat(String.valueOf(revAfter));

		float rev = Float.parseFloat(output);

		System.out.println("Float reverse : " + rev);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//		int i = -2;
		//		System.out.println((i > 0 ? true : false));
		CodingPractice obj = new CodingPractice();
		//		obj.nProduct();
		//		obj.mergeIntervals();
		//		obj.spiralMatrix();
		//		obj.longestPalindromicSubstring();
		//		obj.longestPalindromicSubstringV2();
		//		obj.binarySearch();
		//		obj.maxRepititions();
		//		System.out.println("Integer Reversed : " + obj.intReversal(1234));
		//		obj.floatReversal();
		obj.secondSmallest();
	}

}
