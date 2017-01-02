package com.jsn.test;

import java.util.Scanner;

import com.abzooba.xpresso.customer.equian.WordVectorCategories;
import com.abzooba.xpresso.engine.core.XpEngine;

public class EquianTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XpEngine.init(true);
		String review = "";
		System.out.println("Enter review : \n");
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		review = reader.nextLine();

		System.out.println(WordVectorCategories.getDocVector(review));
	}

}
