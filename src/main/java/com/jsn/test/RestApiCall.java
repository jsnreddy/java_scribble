package com.jsn.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class RestApiCall {

	//	private static final String TEST_CASES_FILE = "hdfc-sample.tsv";
	//	private static final String TEST_CASES_FILE = "/home/abzooba/work/workspace/test/Untitled spreadsheet - Sheet4.tsv";

	private static final String URL_LINK = "http://exp.xpresso.abzooba.com:9091/abzooba/engine/mongo/";
	private static final Integer ID_FIELD = 0;//0;
	private static final Integer REVIEW_FIELD = 7;//0;
	private static final Integer SOURCE_FIELD = null;//0;
	private static final String DELIMITER = "#&#abz#&#";
	private static final String DOMAIN = "government";
	private static final String SOURCE = "assorted";
	private static final String ANNOTATION = "senti";

	private static final boolean hist = false;
	private static final boolean trend = false;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
	private static String CURRENT_DATE_STR = sdf.format(Calendar.getInstance().getTime());

	private static final String TEST_CASES_FILE = "input/mci_30082016.tsv";
	private static final String OUTPUT_FILE = "output/MCI_" + DOMAIN + "_30082016" + ".tsv";

	public static void read_file(String fileName, Collection<String> collection, boolean ignoreHeader) {
		try {
			//			BufferedReader br = new BufferedReader(new FileReader(fileName));
			//			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			BufferedReader br = new BufferedReader(new FileReader(fileName));

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

	public static StringBuilder buildXpRequestQuery(String review, String src) {
		String api_key = "2ABRKGHYY5RQYA9HZ03527G8A";
		String api_secret = "UPBY1Y5AI0iTlc+gzJenE7FBg5zMBBePRQ3f34blb8A";
		//		String annotation = "expr_trend";
		//		String domain_name = "government"; /* can be left null, if the need is so */
		//		String subject = null; /* can be left null */

		StringBuilder requestQuery = new StringBuilder("apikey=" + api_key + DELIMITER + "apisecret=" + api_secret + DELIMITER + "annotation=" + ANNOTATION);
		//		StringBuilder requestQuery = new StringBuilder("annotation=" + ANNOTATION);
		if (DOMAIN != null) {
			requestQuery.append(DELIMITER).append("domain=" + DOMAIN);
		}

		//		if (subject != null) {
		//			requestQuery.append(DELIMITER).append("subject=" + subject);
		//		}
		if (src == null) {
			requestQuery.append(DELIMITER).append("source=" + SOURCE);
		} else {
			requestQuery.append(DELIMITER).append("source=" + src);
		}
		requestQuery.append(DELIMITER).append("hist=" + hist);
		requestQuery.append(DELIMITER).append("trend=" + trend);

		requestQuery.append(DELIMITER).append("feedback=" + review);

		return requestQuery;
	}

	public static StringBuilder buildWalmartRequestQuery(String emailBody, String emailChain) {

		StringBuilder requestQuery = new StringBuilder("annotation=WM_EMAIL#&#abz#&#from=me@walmart.com#&#abz#&#to=you@walmart.com#&#abz#&#cc=all@walmart.com,we@walmart.com#&#abz#&#date=22 Feb 2015 16:27#&#abz#&#subject=AF sample2#&#abz#&#body=");
		requestQuery.append(emailBody);
		// "We haven’t received our Wine and Beer surveys yet…. Should I have?  Please see below!  Thank you much! Diana Vesosky, CIH, CSP  | Director, EHS & Sustainability| diana.vesosky@cbrands.com  | office: 585.678.7182  | mobile: 585.233.8634  Constellation Brands, Inc.  |  207 High Point Drive, Building 100|  Victor, NY 14564 |  www.cbrands.comOur Vision: Elevate Life with Every Glass Raised.     " + 
		requestQuery.append("#&#abz#&#emailChain=");
		requestQuery.append(emailChain);
		requestQuery.append("#&#abz#&#club=sams#&#abz#&#campaign=sc#&#abz#&#ms=merchant#&#abz#&#role=");
		return requestQuery;

	}

	public static JSONObject getXpressoOutput(StringBuilder requestQ) {
		try {

			URL url = new URL(URL_LINK);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			//			connection.setRequestProperty("Content-Type", "text/plain");
			/*
			 * SET THIS PROPERTY WHEN SENDING REQUEST, charset=utf-8
			 */
			connection.setRequestProperty("Content-Type", "text/plain;charset=utf-8");
			//			connection.setRequestProperty("Accept", "*/*");
			//			connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
			//			connection.setRequestProperty("Accept-Encoding", "identity");
			//			connection.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
			//			connection.setRequestProperty("Accept-Charset", "UTF-8");

			BufferedWriter httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

			// BufferedWriter httpRequestBodyWriter = new BufferedWriter(new
			// InputStreamWriter(connection.getInputStream()));

			/*
			 * This part is for walmart rest API call
			 */
			// Thanks for reaching out. The tentative plan is to launch the survey campaign during or right after YBM in mid February. Please let me know if you have any more questions.
			//Can you send me the link to the survey.
			//From: Sunit Sushil - Vendor [mailto:Sunit.Sushil@walmart.com] Sent: 16 May 2016 15:58 To: Sunit Sushil Gmail <sunit.sushil23@gmail.com>Subject: RE: Buyer update This is a sample auto response from Index help desk. ________________________________ From: Sunit Sushil Gmail Sent: Monday, May 16, 2016 10:27:48 AM To: Sunit Sushil - Vendor Subject: Please send me the link to register. From: Sunit Sushil Gmail Sent: Monday, May 16, 2016 10:27:48 AM To: Sunit Sushil - Vendor Subject: Buyer update Thomas, I sent a note to the buyers and asked if some of the suppliers I was unsure of if the supplier is still in business.  Below is the response I received from Brigid Allen.  I am thinking since by the end of the year Molor will no longer be in business they will not need to complete the survey. Linda Do we still do business with the following suppliers?  If so please send me a contact name and email address. Lights of America Molor products- yes (item is still active, but they are closing their doors, so by EOY, no) Outdoor rec- yes Pet Head Straight Arrow Thank you Linda This email and any files transmitted with it are confidential and intended solely for the individual or entity to whom they are addressed. If you have received this email in error destroy it immediately. *** Walmart Confidential *** From: Sunit Sushil Gmail Sent: Monday, May 16, 2016 10:27:48 AM To: Sunit Sushil - Vendor Subject: Buyer update Please update the DMM.

			//			StringBuilder requestQ = new StringBuilder();

			//			requestQ = buildWalmartRequestQuery(review);

			System.out.println("requestQuery : " + requestQ);
			httpRequestBodyWriter.write(requestQ.toString());
			httpRequestBodyWriter.close();
			StringBuilder builder = new StringBuilder();
			Map<String, List<String>> map = connection.getHeaderFields();
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				if (entry.getKey() == null) {
					continue;
				}
				builder.append(entry.getKey()).append(": ");

				List<String> headerValues = entry.getValue();
				Iterator<String> it = headerValues.iterator();
				if (it.hasNext()) {
					builder.append(it.next());

					while (it.hasNext()) {
						builder.append(", ").append(it.next());
					}
				}

				builder.append("\n");
			}
			String line;
			String jsonString = "";

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			while ((line = br.readLine()) != null) {
				jsonString += line + "\n";
			}
			br.close();
			connection.disconnect();

			//			JSONObject xpResponse = new JSONObject(jsonString);

			System.out.println(jsonString);
			if (jsonString == "") {
				return new JSONObject("{}");
			} else {
				return new JSONObject(jsonString);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static void getDomain(JSONObject xpResponse) {

		Object xpDomain = xpResponse.get("Domain");
		System.out.println("Domain : " + xpDomain.toString());
	}

	public static JSONArray getTrendingTopics(JSONObject xpOutputJSON) {
		if (xpOutputJSON != null && !xpOutputJSON.isNull("trending_topics")) {
			JSONArray trendingTopics = xpOutputJSON.getJSONArray("trending_topics");
			if (trendingTopics != null) {
				return trendingTopics;
			}
		}

		return null;
	}

	public static JSONArray getEntityList(JSONObject xpOutputJSON) {
		JSONArray entityList = xpOutputJSON.getJSONArray("entity_list");
		if (entityList != null) {
			return entityList;
		}
		return null;
	}

	public static JSONObject getReviewSentiment(JSONObject xpOutputJSON) {
		JSONObject reviewSentiment = xpOutputJSON.getJSONObject("Review Sentiment");
		if (reviewSentiment != null) {
			return reviewSentiment;
		}
		return null;
	}

	private static void runBatch() throws FileNotFoundException {

		PrintWriter pw = new PrintWriter(OUTPUT_FILE);
		//		pw.println("ID\tReview\tTrending Topics length\tTrending Topics");
		//		pw.println("ID\tReview\tPositiveScore\tNegativeScore\tNeutralScore\tEntityList\tTrending Topics length\tTrending Topics");
		//		pw.println("Trending Topics length\tTrending Topics");
		pw.println("Trending Topic\tTrending Topic Score");
		String[] strArr;
		String id = null;
		String review = null;
		String src = null;
		List<String> strLines = new ArrayList<String>();
		read_file(TEST_CASES_FILE, strLines, true);
		//		int totalCount = strLines.size();
		int count = 0;
		int trendLen = 0;
		JSONArray trend_topics = null;
		for (String line : strLines) {

			strArr = line.split("\t");
			count = count + 1;
			//			if (count > 30) {
			//				break;
			//			}
			System.out.println("Line No.: " + count);
			System.out.println("Length: " + strArr.length);
			if (strArr.length >= 8) {
				id = strArr[ID_FIELD];
				review = strArr[REVIEW_FIELD];
				//				src = strArr[SOURCE_FIELD];
				//			} else if (strArr.length == 2) {
				//				id = strArr[ID_FIELD];
				//				review = strArr[REVIEW_FIELD];
			} else if (strArr.length == 1) {
				id = strArr[ID_FIELD];
				review = "";
			}
			review = review.trim();
			String xpressoOutput = null;
			if (review != null && !review.isEmpty()) {
				JSONObject xpOutputJSON = getXpressoOutput(buildXpRequestQuery(review, SOURCE));
				xpressoOutput = xpOutputJSON.toString();
				trend_topics = getTrendingTopics(xpOutputJSON);
				//			JSONArray entityList = getEntityList(xpOutputJSON);
				//			JSONObject reviewSentimentJSON = getReviewSentiment(xpOutputJSON);
				trendLen = trend_topics.length();
			}

			System.out.println("trend length : " + trendLen);
			//			pw.write(id + "\t" + review + "\t" + trendLen + "\t" + trend_topics.toString() + "\n");
			//			pw.write(id + "\t" + review + "\t" + reviewSentimentJSON.getDouble("Positive") + "\t" + reviewSentimentJSON.getDouble("Negative") + "\t" + reviewSentimentJSON.getDouble("Neutral") + "\t" + entityList.toString() + "\t" + trendLen + "\t" + trend_topics.toString() + "\n");
			//			pw.write(id + "\t" + review + "\t" + trendLen + "\t" + trend_topics.toString() + "\n");
			System.out.println(id + "\t" + review + "\t" + xpressoOutput);
			//			pw.write(id + "\t" + review + "\t" + xpressoOutput + "\n");
		}
		System.out.println(trendLen + "\t" + trend_topics.toString() + "\n");
		extractTrendingTopics(trend_topics, pw);
		pw.close();
	}

	/**
	 * @param trend_topics
	 * @param pw
	 */
	private static void extractTrendingTopics(JSONArray trend_topics, PrintWriter pw) {
		// TODO Auto-generated method stub
		for (int i = 0; i < trend_topics.length(); ++i) {
			JSONObject objects = trend_topics.getJSONObject(i);
			String topic = objects.getString("topic");
			Double score = objects.getDouble("score");
			pw.write(topic + "\t" + score + "\n");
		}

	}

	private static void runXPInteractive() {
		String review = "";
		System.out.println("Enter review : \n");
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		review = reader.nextLine();
		//		String review = "They have ruined my experience while filing my taxes"; /*tax*/
		//		review = "The building is on fire";
		//		review = "HDFC Bank has launched ‘10-second paperless instant loan plan’ for its existing customers. “The whole process of availing the loan is completely paperless, and users can simply log into their bank account via net-banking or mobile banking and avail this loan just at a click,” HDFC State Circle Head Zubair Iqbal told CNS. The scheme was launched by the bank in 2015 “but due to lack of awareness, very few customers know about it,” he said.Pertinently, the 10-second loan is another offering under HDFC Bank’s digital banking platform GoDigital. “Under the platform, the bank launched PayZapp application enabling the customer to buy everything on mobile.";
		//		String review = "The food was good but the service was really bad"; /*hospitality*/
		//		String review = "My medicine for hypertension is out of stock for last 2 month."; /*pharmacy*/
		//		String review = "Building house here is easy. But using tap-water directly is a hazard."; /*city*/
		//		String review = "The latest lego figure has been improved drastically."; /*toys*/
		//		String review = "Recently lot of study and research has been done on tsunami and its prevention."; /*environment*/
		String src = "assorted";
		JSONObject xpOutput = getXpressoOutput(buildXpRequestQuery(review, src));
		System.out.println(xpOutput.toString());
		//		JSONObject xpResponse = new JSONObject(xpOutput);
		//		getDomain(xpResponse);
	}

	private static void runWalmartInteractive() {
		String emailBody = "";
		System.out.println("Enter Email Body : \n");
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		emailBody = reader.nextLine();

		String emailChain = "";
		System.out.println("Enter Email Chain : \n");
		@SuppressWarnings("resource")
		Scanner readerChain = new Scanner(System.in);
		emailChain = readerChain.nextLine();

		String xpOutput = getXpressoOutput(buildWalmartRequestQuery(emailBody, emailChain)).toString();
		System.out.println(xpOutput);

	}

	/**
	 * 
	 */
	private static void runMongoInteractive() {
		String q = "language=en#&#abz#&#domain=government#&#abz#&#annotation=search#&#abz#&#entity=bus#&#abz#&#aspect=bus";
		StringBuilder requestQuery = new StringBuilder(q);
		JSONObject output = getXpressoOutput(requestQuery);
		System.out.println("Mongo Output : " + output.toString());
	}

	public static void main(String[] args) throws FileNotFoundException {

		//		runBatch();
		//		runXPInteractive();
		//		runWalmartInteractive();
		runMongoInteractive();

	}

}
