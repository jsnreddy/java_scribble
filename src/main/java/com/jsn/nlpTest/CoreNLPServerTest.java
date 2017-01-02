/**
 * 
 */
package com.jsn.nlpTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Surendranath Reddy
 *
 */
public class CoreNLPServerTest {

	private static final String URL_LINK = "http://localhost:9000/";
	private static final String URL_LINK_STR = "localhost:9000/?properties={";
	private static final String ANNOTATORS = "tokenize,ssplit,pos";
	private static final String OUTPUT_FORMAT = "text"; //json,xml,text(default)
	private static final String SERIALIZER = "edu.stanford.nlp.pipeline.ProtobufAnnotationSerializer";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String text = "The quick brown fox jumped over the lazy dog";

		//		String document = getCoreNLPOutput(buildRequestQuery(text));

		String document = getCoreNLPOutput(text);
		System.out.println("Document : " + document);
	}

	public static String buildRequestQuery(String text) {

		/*wget --post-data 'the quick brown fox jumped over the lazy dog'
		 *  'localhost:9000/?properties={"annotators": "tokenize,ssplit,pos", "outputFormat": "json"}'*/

		/*{"outputFormat": "serialized", "serializer": "edu.stanford.nlp.pipeline.ProtobufAnnotationSerializer"}*/

		StringBuilder requestQuery = new StringBuilder("'");
		requestQuery.append(text).append("\' \'");
		requestQuery.append(URL_LINK_STR).append("\"");
		requestQuery.append("annotators").append("\": \"");
		requestQuery.append(ANNOTATORS).append("\", ");
		requestQuery.append("outputFormat").append("\": ");
		requestQuery.append(OUTPUT_FORMAT);

		if (("serialized").equals(OUTPUT_FORMAT)) {
			requestQuery.append("\", ").append("\"serializer\": \"");
			requestQuery.append(SERIALIZER);
			requestQuery.append("\"}'");
		} else {
			requestQuery.append("\"}'");
		}

		System.out.println("Request Query : " + requestQuery.toString());
		return requestQuery.toString();

	}

	public static String getCoreNLPOutput(String requestQ) {
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

			/*
			 * Trying this to set properties - annotators, outputFormat
			 */

			connection.setRequestProperty("annotators", ANNOTATORS);
			connection.setRequestProperty("outputFormat", OUTPUT_FORMAT);
			if (("serialized").equals(OUTPUT_FORMAT)) {
				connection.setRequestProperty("serializer", SERIALIZER);
			}
			BufferedWriter httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

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

			System.out.println("Response : " + jsonString);
			return jsonString;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
