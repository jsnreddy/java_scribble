package com.jsn.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 * @param <T>
 *
 */

public class App<T> {
	private static final String INPUT_PATH = "input/walmart";
	private static final String INPUT_FILE_NAME = "test.tsv";
	private static Pattern SIGN_PATTERN;
	public static String sign = "";
	private static String regex = "(*?)" + sign + "$(*?)";
	private static List<String> signDelimiters = new ArrayList<String>();
	static List<String> MerchantEmailCategories = Arrays.asList("DESK_CHANGE", "CHANGE_REQUEST", "RESEARCH", "QUERY", "UNDELIVERABLE", "UNCLASSIFIED");
	static List<String> RCSupplierEmailCategories = Arrays.asList("CHANGE_REQUEST", "FORWARD_SAP", "RESEARCH", "QUERY", "UNDELIVERABLE", "UNCLASSIFIED");
	static List<String> SCSupplierEmailCategories = Arrays.asList("CHANGE_REQUEST", "DECLINE_SURVEY", "ADD_FIND_SURVEY", "FORWARD_SAP", "FORWARD_TSC", "RESEARCH", "QUERY", "UNDELIVERABLE", "UNCLASSIFIED");
	static String signatureDelimitersFileName = "signatures.txt";

	public static void init() {
		SIGN_PATTERN = Pattern.compile(regex);
	}

	private static void extractSign(String text) {
		Matcher mSign = SIGN_PATTERN.matcher(text);
		System.out.println("Found sign");
		System.out.println("Body : " + mSign.group(1));
		System.out.println("Sign : " + mSign.group(2));
	}

	public enum EmailCategories {
		DESK_CHANGE, CHANGE_REQUEST, DECLINE_SURVEY, ADD_FIND_SURVEY, FORWARD_SAP, FORWARD_TSC, UNDELIVERABLE, RESEARCH, QUERY, UNCLASSIFIED;
	}

	public static void setCategory(EmailCategories currCategory) {
		System.out.println("setting current category as " + currCategory);
	}

	public static EmailCategories convertCategory(String category) {
		switch (category) {
			case ("CHANGE_REQUEST"):
				return EmailCategories.CHANGE_REQUEST;
			case ("DESK_CHANGE"):
				return EmailCategories.DESK_CHANGE;
			case ("QUERY"):
				return EmailCategories.QUERY;
			case ("RESEARCH"):
				return EmailCategories.RESEARCH;
			case ("UNDELIVERABLE"):
				return EmailCategories.UNDELIVERABLE;
			case ("DECLINE_SURVEY"):
				return EmailCategories.DECLINE_SURVEY;
			case ("ADD_FIND_SURVEY"):
				return EmailCategories.ADD_FIND_SURVEY;
			case ("FORWARD_SAP"):
				return EmailCategories.FORWARD_SAP;
			case ("FORWARD_TSC"):
				return EmailCategories.FORWARD_TSC;
			default:
				return EmailCategories.UNCLASSIFIED;
		}

	}

	public static void getHierarchy(String cat1, String cat2, EmailCategories emailCat) {
		if (cat2.equalsIgnoreCase("supplier")) {
			if (emailCat.toString().equals("DESK_CHANGE")) {
				emailCat = convertCategory(RCSupplierEmailCategories.get(0));
				setCategory(emailCat);
				System.out.println("in " + cat2 + " and it is " + RCSupplierEmailCategories.get(0));
			} else if (cat1.equalsIgnoreCase("rc")) {
				//				EmailCat = (List<T>) java.util.Arrays.asList(EnumValues.RCSupplierEmailCategories.values());
				//				System.out.println(EmailCat);

			} else if (cat1.equalsIgnoreCase("sc")) {
				//				EmailCat = (List<T>) java.util.Arrays.asList(EnumValues.MerchantEmailCategories.values());
				//				System.out.println(EmailCat);
			}
		} else if (cat2.equalsIgnoreCase("merchant")) {
			if (MerchantEmailCategories.contains(emailCat.toString())) {
				//				System.out.println(MerchantEmailCategories.get(MerchantEmailCategories.size() - 1));
				System.out.println("it is " + emailCat.toString());
			} else {
				System.out.println("in " + cat2 + " and " + "it is " + EmailCategories.UNCLASSIFIED);
			}
		}
	}

	public static void showFiles(File[] files) {
		for (File file : files) {
			if (file.isDirectory() && file.getName().contains(".jar")) {
				System.out.println("Directory: " + file.getName());
				showFiles(file.listFiles()); // Calls same method again.
			} else {
				if (file.getName().contains(".jar"))
					System.out.println("File: " + file.getName());
			}
		}
	}

	public static void read_file(String fileName, Collection<String> collection, boolean ignoreHeader) {
		try {
			//			BufferedReader br = new BufferedReader(new FileReader(fileName));
			//			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

			//			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"));
			String strLine;
			int i = 0;

			while ((strLine = br.readLine()) != null) {
				if (i++ == 0) {
					if (ignoreHeader) {
						continue;
					}
				}
				collection.add(strLine);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getSignature(String email) {

		if (email.split(regex).length == 1) {
			return email;
		}
		for (String delimiter : signDelimiters) {
			//			if (email.contains(delimiter)) {
			//				String[] splitEmail = email.split(delimiter);
			//				splitEmail[1] = delimiter + splitEmail[1];
			//				return splitEmail;
			//			}
			if (email.indexOf(delimiter) > 0) {
				String body = email.substring(0, email.indexOf(delimiter));
				return body;
			}
		}
		return email;
	}

	public static String splitBody(String email) {
		String splitEmail = null;

		return splitEmail;
	}

	public static void main(String[] args) {
		//		System.out.println("Hello World!");
		//		init();
		//		List<String> emailLines = new ArrayList<String>();

		//		FileIO.read_file(INPUT_PATH + "/" + INPUT_FILE_NAME, emailLines);

		//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		//		String outputFileName = "output/" + INPUT_FILE_NAME + sdf.format(Calendar.getInstance().getTime()) + ".txt";
		//		PrintWriter pw = new PrintWriter(new FileWriter(outputFileName));
		//		String text = "";
		//		extractSign(text);

		//		String cat1 = "rc";
		//		String cat2 = "supplier";
		//		EmailCategories emailCat = EmailCategories.DESK_CHANGE;
		//		getHierarchy(cat1, cat2, emailCat);
		//		File[] files = new File("/home/abzooba/jars").listFiles();
		//		showFiles(files);
		String test = "the is just a test and is blank";
		String testSplit[] = test.split("is", 2);
		System.out.println("Size : " + testSplit.length);
		System.out.println("array : ");
		for (String testSplitWord : testSplit) {
			System.out.println(testSplitWord + " Size : " + testSplitWord.length());
		}
		//		read_file(signatureDelimitersFileName, signDelimiters, false);
		//		System.out.println(signDelimiters);
		while (true) {

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				System.out.println("===========================================================================================");
				System.out.print("Email Body:\n");
				String email = br.readLine();

				if (email == null) {
					continue;
				}
				String splitEmail = getSignature(email);
				//				String[] splitEmail = email.split("Best Regards");
				if (splitEmail != null) {
					//					String body = splitEmail;
					System.out.println("Body : " + splitEmail);
					//					if (splitEmail.length > 1) {
					//						for (int i = 1; i < splitEmail.length; ++i) {
					//							String signature = splitEmail[i];
					//							System.out.println("Signature " + i + " : " + signature);
					//						}
					//					}
				} else {
					System.out.println("Email cannot be split");
					System.out.println("Email as it is : " + email);
				}

				System.out.println("===========================================================================================");
				// XpEngine.flushTriples();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}