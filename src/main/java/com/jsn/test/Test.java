package com.jsn.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//		JSONObject newJSON = new JSONObject();
		//		JSONObject nullJSON = null;
		//
		//		JSONObject netJSON = new JSONObject();
		//		netJSON.put("newJSON", newJSON);
		//		if (nullJSON == null) {
		//			nullJSON = new JSONObject();
		//			netJSON.put("nullJSON", nullJSON);
		//		}
		//
		//		Set<String> intentObjectSet = new HashSet<String>(Arrays.asList("a", "b"));
		//		;
		//		if (intentObjectSet == null) {
		//			intentObjectSet = new HashSet<String>();
		//			netJSON.put("Intent Object", intentObjectSet);
		//		} else {
		//			netJSON.put("Intent Object", intentObjectSet);
		//		}
		//
		//		Map<String, String> namedEntitiesMap = new HashMap<String, String>();
		//		namedEntitiesMap.put("1", "1");
		//		namedEntitiesMap.put("2", "2");
		//		if (namedEntitiesMap == null) {
		//			namedEntitiesMap = new HashMap<String, String>();
		//			netJSON.put("Named Entities", namedEntitiesMap);
		//		} else {
		//			netJSON.put("Named Entities", namedEntitiesMap);
		//		}
		//		System.out.println(newJSON);
		//		System.out.println(nullJSON);
		//		System.out.println(netJSON);

		System.out.println("OS : " + System.getProperty("os.name"));
		System.out.println("User : " + System.getProperty("user.name"));
		Runtime rt = Runtime.getRuntime();
		Process p = null;
		try {
			if (System.getProperty("os.name").toLowerCase().contains("linux")) {

				p = rt.exec("which python");
			} else if (System.getProperty("os.name").toLowerCase().contains("windows")) {
				p = rt.exec("where python");
			}
			if (p != null) {
				BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String strLine = in.readLine();
				System.out.println("Python Path : " + strLine);
			} else {
				System.out.println("P is null");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
