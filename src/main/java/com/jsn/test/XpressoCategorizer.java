
package com.jsn.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class XpressoCategorizer {

	static String strVersion = "";
	static Statement statement_xpresso = null;
	static String strSQL = "";
	String _sourcename = "";
	static String _url;
	static String _uid;
	static String _pwd;
	static Connection conn;
	static String strXpressoOutput = "";
	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static Properties prop = new Properties();
	static Properties xpresso_prop = new Properties();
	final static Logger logger = Logger.getLogger(XpressoCategorizer.class);
	static List<Object[]> listCategorized = new ArrayList<Object[]>();

	//These variables are for Signature delimiters, fileName can be put in props file
	private static List<String> signDelimiters = new ArrayList<String>();
	private static String signatureDelimitersFileName = "domainKB/signature-patterns.txt";

	public XpressoCategorizer() throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, SQLException {
		logger.info("Calling init()");
		init();
		logger.info("Init() loaded successfully");
	}

	public static void read_file(String fileName, Collection<String> collection, boolean ignoreHeader) {
		try {
			//			BufferedReader br = new BufferedReader(new FileReader(fileName));
			//			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			File file = new File(fileName);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

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
			System.out.println("Signature Delimiters File loaded");
			br.close();
		} catch (Exception e) {
			System.out.println("Signature Delimiters File could not be loaded");
			e.printStackTrace();
		}
	}

	private static String splitBySignature(String emailBody) {
		List<Integer> splitEmailIndices = new ArrayList<Integer>();
		//		List<String> splitEmailBodies = new ArrayList<String>();
		splitEmailIndices.add(0);
		for (String delimiter : signDelimiters) {
			Pattern delimiterPattern = Pattern.compile(delimiter);
			Matcher delimiterMatcher = delimiterPattern.matcher(emailBody);
			while (delimiterMatcher.find()) {
				if (delimiterMatcher.start() > 0) {
					int index = delimiterMatcher.start();
					splitEmailIndices.add(index - 1);
				}
			}
		}
		Collections.sort(splitEmailIndices);
		String formattedEmailBody = "";
		for (int i = 0; i < splitEmailIndices.size() - 1; ++i) {
			//			String newSplit = emailBody.substring(splitEmailIndices.get(i), splitEmailIndices.get(i + 1));
			//			splitEmailBodies.add(newSplit);
			formattedEmailBody += emailBody.substring(splitEmailIndices.get(i), splitEmailIndices.get(i + 1));
		}

		return formattedEmailBody;
	}

	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, JSONException, ParseException {
		/*String strSenderEmail="sahith.reddy@abzooba.com";
		String strReceipentEmail="biswajit.saha@abzooba.com";
		String strEmailSubject="action required";
		//String strEmailBody="No longer do business with the supplier on the list";
		String strEmailBody="Please remove contact from the list";
		XpressoCategorizer xpressocategorizer=new XpressoCategorizer();
		xpressocategorizer.processExecutor(strSenderEmail,strReceipentEmail,strEmailSubject,strEmailBody);*/
		/*Map<String, Object> dataMap = null;
		dataMap = new LinkedHashMap<String, Object>();
		dataMap.put("ebody", "chicken is good. service is bad");
		XpressoCategorizer.processExecutor(dataMap);*/
		//xpressojsonparser("{\"chicken is good.\":{\"About\":[{\"Entity\":\"Overall\",\"Aspect\":\"Overall\",\"Sentiment\":\"Positive\",\"StatementType\":\"Advocacy\",\"Indicative Snippet\":\"chicken is good.\"}]},\"service is bad.\":{\"About\":[{\"Entity\":\"service\",\"Aspect\":\"CUSTOMER SERVICES\",\"Sentiment\":\"Negative\",\"StatementType\":\"Complaint\",\"Indicative Snippet\":\"service is bad.\"},{\"Entity\":\"Overall\",\"Aspect\":\"Overall\",\"Sentiment\":\"Negative\",\"StatementType\":\"Complaint\",\"Indicative Snippet\":\"service is bad.\"}]},\"Domain\":\"banking\",\"Version\":\"4.2.4_5\"}");

		String emailBody = "Hi, Thanks for the quick response given from the team. The pen I bought did not wrote: anything. Thanks, JSN";
		System.out.println("emailBody : " + emailBody);
		read_file(signatureDelimitersFileName, signDelimiters, true);
		String formattedEmailBody = splitBySignature(emailBody);

		System.out.println("formattedEmailBody : " + formattedEmailBody);
	}

	//private static void processExecutor(String strSenderEmail,String strReceipentEmail,String strEmailSubject,String strEmailBody) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, JSONException, ParseException{
	public static List<Object[]> processExecutor(Map<String, Object> dataMap, Map<String, String> categoryMap, String messageId) throws Exception {
		logger.info("Calling Xpresso starts..");
		init();
		String strXpressoCategory = "";
		List<Object[]> listOfCategorized = new ArrayList<Object[]>();
		//String strDomain=strSenderEmail+"--"+strReceipentEmail;
		//String strDomain=strSenderEmail+"#&#"+strReceipentEmail;

		strXpressoOutput = callXpresso(dataMap);
		logger.info("XpressoCategorization: Xpresso output :" + strXpressoOutput);

		if (strXpressoOutput != null || !strXpressoOutput.isEmpty()) {
			logger.info("Saving Xpresso output to table");
			//savexpressodata(strXpressoOutput);
			listOfCategorized = xpressojsonparser(strXpressoOutput, categoryMap, messageId);
			logger.info("XpressoCategorization: Xpresso output saved successfully:" + strXpressoCategory);
		}
		return listOfCategorized;
	}

	//calling xpresso enggine
	private static String callXpresso(Map<String, Object> dataMap) throws IOException {
		logger.info("Calling xpresso engine");
		String responseText = "";
		String XpressoURLBody = "";

		String xpressoURL = "http://" + xpresso_prop.getProperty("server") + ":" + xpresso_prop.getProperty("port") + "/abzooba/engine/result/";

		logger.info("callXpresso method :Url to call xpresso" + xpressoURL);
		URL url = new URL(xpressoURL);

		HttpURLConnection connection = null;
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		//connection.setRequestProperty("Content-Type", "text/plain;charset=utf-8");
		connection.setRequestProperty("Content-Type", "text/plain;");

		BufferedWriter httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

		Document doc = Jsoup.parse(dataMap.get("ebody").toString());
		String strEmailBody = doc.text();
		logger.info("Actual Email body:" + strEmailBody);
		String formattedEmailBody = splitBySignature(strEmailBody);
		logger.info("Formatted Email Body : " + formattedEmailBody);
		//strEmailBody="chicken is good. service is bad.";

		XpressoURLBody = "apikey=6QDFA3L5R3GEH8GZNBOCTWYC0" + xpresso_prop.getProperty("delimeter") + "apisecret=O7f0L2wckh00SEZAaSaDsXN5+/b2WuOz4iJnk3Gcbag" + xpresso_prop.getProperty("delimeter") + "annotation=" + xpresso_prop.getProperty("annotation");
		XpressoURLBody += xpresso_prop.getProperty("delimeter") + "domain=" + xpresso_prop.getProperty("domain");
		//		XpressoURLBody += xpresso_prop.getProperty("delimeter") + "feedback=" + strEmailBody;
		XpressoURLBody += xpresso_prop.getProperty("delimeter") + "feedback=" + formattedEmailBody;
		System.out.println("url:" + XpressoURLBody);

		try {

			httpRequestBodyWriter.write(XpressoURLBody);
			httpRequestBodyWriter.close();
			System.out.println("responseText:" + responseText);

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				responseText += line + '\n';
			}
			reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("callXpresso method : output of xpresso engine:" + responseText);
		return responseText;
	}

	//Saving xpresso output
	private static List<Object[]> xpressojsonparser(String strXpressoOutput, Map<String, String> categoryMap, String messageId) throws Exception {
		logger.info("Savinging xpresso output ");
		HashMap<String, String> hStatementType = null;
		if (strXpressoOutput.length() > 3) {
			if (!strXpressoOutput.equals("{}")) {
				String sql = "";
				String intent = "";
				//saving review aspect and sentiment into the table;
				JSONObject jsonObject = new org.json.JSONObject(strXpressoOutput);
				JSONObject jsonObject1 = new org.json.JSONObject(strXpressoOutput);
				JSONObject jsonObjectforEmotion = new org.json.JSONObject(strXpressoOutput);
				strVersion = jsonObject.getString("Version");
				System.out.println("TwitterBolt:Xpresso version:" + strVersion);

				//statement_xpresso = conn.createStatement();

				Iterator iterator = jsonObject.keys();
				while (iterator.hasNext()) {
					String strSnippet = "";
					String strStatementType = "";
					String strEntitty = "";
					String strAspect = "";
					String strSentiment = "";
					String strKey = (String) iterator.next();
					System.out.println("Outside if strKey:" + strKey);
					if (!(strKey.trim().equalsIgnoreCase("Version")) && !(strKey.trim().equalsIgnoreCase("Domain"))) {
						jsonObject1 = (JSONObject) jsonObject.get(strKey);

						JSONArray aboutObjectArray = jsonObject1.getJSONArray("About");
						System.out.println("length of array:" + aboutObjectArray.length());

						hStatementType = new HashMap<String, String>();

						for (int i = 0; i < aboutObjectArray.length(); i++) {

							JSONObject innerObj = (JSONObject) aboutObjectArray.get(i);
							strSnippet = innerObj.getString("Indicative Snippet");
							strSnippet = strSnippet.replace("\\", "\\\\");
							strSnippet = strSnippet.replace("''", "\\''");
							//strSnippet = strSnippet.replace("'", "\\'");
							strSnippet = strSnippet.replace("\"", "\\\"");
							strSnippet = strSnippet.replace("\"", "\\\"");
							strSnippet = strSnippet.replace("'", "''");

							System.out.println("strSnippet:" + strSnippet);

							strStatementType = innerObj.getString("StatementType");
							strEntitty = innerObj.getString("Entity");
							strAspect = innerObj.getString("Aspect");
							strSentiment = innerObj.getString("Sentiment");
							String strStatementTypeAspect = strAspect + "-" + strStatementType;
							logger.info("Aspect-StatementType:" + strStatementTypeAspect);

							if (hStatementType.size() == 0) {
								//if (i==0){
								listCategorized = prepareMsgCatgList(listCategorized, messageId, categoryMap.get(strStatementType.toUpperCase()), "");
								listCategorized = prepareMsgCatgList(listCategorized, messageId, categoryMap.get(strStatementTypeAspect.toUpperCase()), strSnippet);
							} else {
								boolean bFound = false;
								for (Map.Entry m : hStatementType.entrySet()) {
									if (m.getValue().toString().equalsIgnoreCase(strStatementType)) {
										bFound = true;
										break;
									}
								}
								if (bFound) {
									listCategorized = prepareMsgCatgList(listCategorized, messageId, categoryMap.get(strStatementTypeAspect.toUpperCase()), strSnippet);
								} else {
									listCategorized = prepareMsgCatgList(listCategorized, messageId, categoryMap.get(strStatementType.toUpperCase()), "");
									listCategorized = prepareMsgCatgList(listCategorized, messageId, categoryMap.get(strStatementTypeAspect.toUpperCase()), strSnippet);
								}
							}
							hStatementType.put(strStatementType, strStatementType);

						} //end of for
					}
					System.out.println("savexpressodata method: xpresso output saved successfully");
				} //end of while
					//statement_xpresso.close();
			}
		}
		return listCategorized;
	}

	private static void init() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		logger.info("Initializer");
		//DBInfo properties
		/*		prop.load(XpressoCategorizer.class.getClassLoader().getResourceAsStream("dbinfo.properties"));
				logger.info("XpressoCategorization: Creating connection");
				String strDriver = prop.getProperty("mysqldb_driver");
				if (strDriver != null) {
					DriverManager.registerDriver(((java.sql.Driver) Class.forName(strDriver).newInstance()));
					_url = prop.getProperty("mysqldb_url").trim();
					logger.info("_url:"+_url);
					_uid = prop.getProperty("mysqldb_user").trim();
					logger.info("_uid:"+_uid);
					_pwd = prop.getProperty("mysqldb_password").trim();
					connect();
				}
		*/
		/*final Properties dbprop = new Properties();
		
		//loading dbinfo properties
		//dbprop.load(EmailFetcher.class.getClassLoader().getResourceAsStream("dbinfo.properties"));
		
		InputStream inputStream = EmailFetcher.class.getClassLoader().getResourceAsStream("dbinfo.properties");
		 
		if (inputStream != null) {
			dbprop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file  not found in the classpath");
		}*/

		/*ConnectionManager connection_provider = ConnectionManager.getObject(dbprop);
		con = connection_provider.getConnection();
		*/
		//Xpresso Properties
		xpresso_prop.load(XpressoCategorizer.class.getClassLoader().getResourceAsStream("xpresso_config.properties"));

		//Loading sign delimiters file
		read_file(signatureDelimitersFileName, signDelimiters, true);
	}
	/*private static void connect() throws SQLException {
		// TODO Auto-generated method stub
		conn = DriverManager.getConnection(_url, _uid, _pwd);
		
	}*/

	public static List<Object[]> prepareMsgCatgList(List<Object[]> listMsgCatg, Object messageId, Object categoryId, Object strSnippet) throws Exception {

		try {
			Object[] arrMsgCat = new Object[3];
			arrMsgCat[0] = String.valueOf(messageId);
			arrMsgCat[1] = String.valueOf(categoryId);
			arrMsgCat[2] = String.valueOf(strSnippet);
			listMsgCatg.add(arrMsgCat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listMsgCatg;

	}
}
