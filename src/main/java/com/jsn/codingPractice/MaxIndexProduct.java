/**
 * 
 */
package com.jsn.codingPractice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author Surendranath Reddy
 *
 */
public class MaxIndexProduct {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();

		List<Integer> numList = new ArrayList<Integer>();

		for (int i = 0; i < n; ++i) {
			numList.add(s.nextInt());
		}

		List<Integer> leftIndexList = new ArrayList<Integer>(n);
		List<Integer> rightIndexList = new ArrayList<Integer>(n);

		Stack<Integer> st = new Stack<Integer>();

		for (int i = 0; i < n; ++i) {
			int leftIndex = 0;
			while (!st.empty() && numList.get(st.peek()) <= numList.get(i)) {
				st.pop();
			}
			if (st.empty()) {
				leftIndex = 0;
				leftIndexList.add(i, leftIndex);
			} else {
				leftIndex = st.peek();
				leftIndexList.add(i, leftIndex + 1);
			}

			st.push(i);
		}

		Stack<Integer> st1 = new Stack<Integer>();

		for (int i = n - 1; i >= 0; --i) {
			//System.out.println(i);
			int rightIndex = 0;
			while (!st1.empty() && numList.get(st1.peek()) <= numList.get(i)) {
				st1.pop();
			}
			if (st1.empty()) {
				rightIndex = 0;
				rightIndexList.add(n - i - 1, rightIndex);
			} else {
				rightIndex = st1.peek();
				rightIndexList.add(n - i - 1, rightIndex + 1);
			}

			st1.push(i);
		}

		//Collections.reverse(rightIndexList);
		//System.out.println(leftIndexList);
		//System.out.println(rightIndexList);

		int max = Integer.MIN_VALUE;
		for (int i = 0; i < n; ++i) {
			int leftIndex = leftIndexList.get(i);
			int rightIndex = rightIndexList.get(n - i - 1);

			if (leftIndex * rightIndex > max) {
				max = leftIndex * rightIndex;
			}
		}

		System.out.println(max);

	}

}
