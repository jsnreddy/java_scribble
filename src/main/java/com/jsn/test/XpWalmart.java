package com.jsn.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class XpWalmart {

	public static String getXpressoOutput(String review) {
		try {

			final String DELIMITER = "#&#abz#&#";
			String URL_LINK = "http://xpresso.abzooba.com:9090/abzooba/engine/result/";
			URL url = new URL(URL_LINK);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "text/plain");
			//			connection.setRequestProperty("Accept", "*/*");
			//			connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
			//			connection.setRequestProperty("Accept-Language", "en-US,en;q=0.8");

			BufferedWriter httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
			String api_key = "2ABRKGHYY5RQYA9HZ03527G8A";
			String api_secret = "UPBY1Y5AI0iTlc+gzJenE7FBg5zMBBePRQ3f34blb8A";
			String annotation = "wm_email";
			String domain_name = "tax"; /* can be left null, if the need is so */
			String subject = "H&R Block"; /* can be left null */

			// BufferedWriter httpRequestBodyWriter = new BufferedWriter(new
			// InputStreamWriter(connection.getInputStream()));

			StringBuilder requestQuery = new StringBuilder("apikey=" + api_key + DELIMITER + "apisecret=" + api_secret + DELIMITER + "annotation=" + annotation);

			if (domain_name != null) {
				requestQuery.append(DELIMITER).append("domain=" + domain_name);
			}

			if (subject != null) {
				requestQuery.append(DELIMITER).append("subject=" + subject);
			}

			requestQuery.append(DELIMITER).append("feedback=" + review);
			System.out.println("requestQuery : " + requestQuery);
			httpRequestBodyWriter.write(requestQuery.toString());
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
			return jsonString;
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static void getDomain(JSONObject xpResponse) {

		Object xpDomain = xpResponse.get("Domain");
		System.out.println("Domain : " + xpDomain.toString());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String review = "They have ruined my experience while filing my taxes"; /*tax*/
		//		String review = "The food was good but the service was really bad"; /*hospitality*/
		//		String review = "My medicine for hypertension is out of stock for last 2 month."; /*pharmacy*/
		//		String review = "Building house here is easy. But using tap-water directly is a hazard."; /*city*/
		//		String review = "The latest lego figure has been improved drastically."; /*toys*/
		//		String review = "Recently lot of study and research has been done on tsunami and its prevention."; /*environment*/

		String xpOutput = getXpressoOutput(review);
		JSONObject xpResponse = new JSONObject(xpOutput);

		//		getDomain(xpResponse);
	}

}
