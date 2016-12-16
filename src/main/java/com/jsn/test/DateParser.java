package com.jsn.test;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class DateParser {

	public static void main(String[] args) {
		String textToParse = "Have a meeting on 11/26 and next on 11/27";
		getDateFromString(textToParse);

	}

	/**
	 * Parses a String, detects dates and returns a list of detected dates
	 * @param textToParse
	 * @return
	 */
	public static Set<Date> getDateFromString(String textToParse) {
		Parser parser = new Parser();
		List<DateGroup> groups = parser.parse(textToParse);
		Set<Date> dtList = new HashSet<Date>();
		for (DateGroup group : groups) {
			List<Date> dates = group.getDates();
			for (Iterator<Date> iterator = dates.iterator(); iterator.hasNext();) {
				Date date = (Date) iterator.next();
				dtList.add(date);
				System.out.println("Date detected:" + date.toString());

			}
			//int line = group.getLine();
			//System.out.println("Line:" + line);
			//int column = group.getPosition();
			//System.out.println("Column:" + column);
			int abspos = group.getAbsolutePosition();
			String matchingValue = group.getText();
			int matchedlength = matchingValue.length();
			int nextPosToStartFrom = abspos + matchedlength;
			String actualString = group.getFullText();
			//System.out.println("Absolute Position:" + abspos);
			//System.out.println("Full text:" + group.getFullText());			
			//System.out.println("Matching value:" + matchingValue);

			if (nextPosToStartFrom < actualString.length()) {
				String subTextToParse = textToParse.substring(nextPosToStartFrom);
				getDateFromString(subTextToParse);
			}

			//String syntaxTree = group.getSyntaxTree().toStringTree();
			//System.out.println("Syntax tree:" + syntaxTree);
			//Map<String, List<ParseLocation>> parseMap = group.getParseLocations();
			//boolean isRecurreing = group.isRecurring();
			//Date recursUntil = group.getRecursUntil();
			//System.out.println("Date:" + recursUntil);
		}
		return dtList;
	}

}
