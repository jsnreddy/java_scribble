package com.jsn.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class RegexMatch {
	private static Pattern WROTE_PATTERN;
	private static final String INPUT_FILE_NAME = "input/sampleNote.txt";

	public static String readFile() {
		BufferedReader br;
		String text = "";
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(INPUT_FILE_NAME)));

			//			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"));
			String strLine;

			while ((strLine = br.readLine()) != null) {
				text = text + strLine;
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//		String text = readFile();

		//		String regex = "(.*)On (.*)<(.*)> wrote: (.*)";
		//		regex = "\\D*(\\d{6})\\D*"; //regex to find a 6 digit number - check why we didn't use this as this is simpler
		//		//		regex = "(?<!\\d)(\\d{6})(?!\\d)"; //regex to find 6 digit number- vendor id purpose
		//		WROTE_PATTERN = Pattern.compile(regex, Pattern.DOTALL);
		//		//		WROTE_PATTERN = Pattern.compile("(.*?)<(.*?)> wrote:(.*?)");
		//		//		WROTE_PATTERN = Pattern.compile("(.*?)<(.*?)> wrote:(.*?)");
		//
		//		//		String text = "Please see the screenshot below. On Wed, May 18, 2016 at 6:05 PM, Sunit Sushil - Vendor <Sunit.Sushil@walmart.com> wrote: Please provide your vendor id and name";
		//		//		text = "Dear Manuel, I logged in to my SPA account today and we still do not have access to the sustainability Index questionnaire. Is this normal? Best, Julie On Sun, May 15, 2016 at 3:38 PM, Lova Kurumella (UST, IND)<Lova. Kurumella@ust-global. com> wrote: This is a sample auto response from Index help desk.";
		//		//		String text = "On Sun, May 15, 2016 at 3:38 PM, Lova Kurumella (UST, IND)<Lova. Kurumella@ust-global. com> wrote:";
		//		//		text = "";
		//		//		text = ",     123456789$%^*(.";
		//		//		text = "bhoort1123456cricket";
		//		Matcher mWrote = WROTE_PATTERN.matcher(text);
		//		if (mWrote.find()) {
		//			String prevMailBody = mWrote.group(1);
		//			System.out.println("match found");
		//			//			String date = mWrote.group(2)/* + mWrote.group(3) + mWrote.group(3) + mWrote.group(4)*/;
		//			//			String from = mWrote.group(2)/* + mWrote.group(4)*/;
		//			//			String nextMailBody = mWrote.group(3);
		//
		//			System.out.println(prevMailBody /*+  "\n" + date + "\n" + from + "\n" + nextMailBody*/);
		//		} else {
		//			System.out.println("match not found");
		//		}

		//		String[] mailArr = text.split(regex);
		//		for (String mail : mailArr) {
		//			System.out.println(mail);
		//		}

		//		String urlRegex = "(\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])";
		//		String urlRegex = "((https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])";
		//		//		String urlRegex = "(\b(https?|ftp|file)://[-A-Z0-9+&@#/%?=~_|!:,.;]*[A-Z0-9+&@#/%=~_|])";
		//		//		String urlRegex = "(<[/]*\\w>)";
		//		String testText = "https://www.google.co.in/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=identify%20a%20hyperlink%20text%20regex%20java";
		//
		//		testText = "RT @TODAYonline: Meet Emma - a robot masseur in Singapore who apparently gives 'quite good back massages' https://t.co/LqN6vD4nWI https://t…";
		//		testText = "RT @ChannelNewsAsia: Indonesia begins re-inoculating children who received fake vaccines in major drug scam https://t.co/XyZt702Nj0 https:/…";
		//		testText = "RT @TODAYonline: Indian rape survivor gang-raped again by same men: Police https://t.co/bSE9VKkPbP https://t.co/DW4zfcY64q";
		//		Pattern urlRegexPattern = Pattern.compile("((https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])");
		//		Matcher m = urlRegexPattern.matcher(testText);
		//		while (m.find()) {
		//			String tempPattern = m.group();
		//			System.out.println(tempPattern);
		//		}
		//		//		if (Pattern.matches(urlRegex, textText))
		//			System.out.println("Matched");
		//		else {
		//			System.out.println("Not Matched");
		//		}

		//String regex = "a-zA-Z;

		//		String test = "very with $$$...especially thosecitizen...welcome our localJS" + "\n" + "heyy!!";
		//		System.out.println("test: " + test);
		//		//		test = test.replaceAll("[^a-zA-Z0-9- ]", "");
		//		//		test = test.replaceAll("[^\\w\\s\\-]", "");
		//		String currWd = "kee";
		//		String value = "opinion1 opinion2 kee";
		//
		//		String iterValue = Pattern.compile("^$", Pattern.UNICODE_CHARACTER_CLASS).matcher(test).replaceAll("");
		//		System.out.println("itervalue: " + iterValue);
		//		value = Pattern.compile("\\b" + currWd + "\\b", Pattern.UNICODE_CHARACTER_CLASS).matcher(value).replaceAll(iterValue);
		//		System.out.println("value: " + value);
		//		//System.out.println("test: " + test);

		String entityStr = "Back home! On both days at #ASEM, we awoke to shocking news - the terror attack in Nice and attempted coup in Turkey. Both resulted in many lives lost. A stark reminder of a troubled world, and why in Singapore we must stay united and look out for one another. Our MFA is watching the situation in Turkey closely. For overseas Singaporeans, wherever you may be, please e-register with MFA (https://eregister.mfa.gov.sg/) and stay safe. LHL";
		entityStr = "called insured / / informed him about process / / will talk again on monday";
		entityStr = readFile();
		System.out.println("original : " + entityStr);
		//		entityStr = entityStr.toLowerCase().trim();
		//		System.out.println("1 " + entityStr);
		//		entityStr = entityStr.replaceAll("\b(jan(uary)?|feb(ruary)?|mar(ch)?|apr(il)?|may|jun(e)?|jul(y)?|aug(ust)?|sep(tember)?|oct(ober)?|nov(ember)?|dec(ember)?)\b", "");
		//		System.out.println("2 " + entityStr);
		//		entityStr = entityStr.replaceAll("\b(mon(day)?|tue(sday)?|wed(nesday)?|thu(rsday)?|fri(day)?|sat(urday)?|sun(day)?)\b", "");
		//		System.out.println("3 " + entityStr);
		//		entityStr = entityStr.replaceAll("[0-9]+(pm|am)?", "");
		//		System.out.println("4 " + entityStr);
		//		//		entityStr = entityStr.replaceAll("[^\\w\\s\\-]", "");
		//		//		System.out.println("5 " + entityStr);
		//		entityStr = entityStr.trim();
		entityStr = entityStr.replaceAll("/\\s*/", ".");
		System.out.println("final : " + entityStr);
		if (entityStr.isEmpty() || entityStr.equals("")) {
			System.out.println("empty string");
		}

	}

}
