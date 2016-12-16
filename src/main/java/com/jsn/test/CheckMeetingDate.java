package com.jsn.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class CheckMeetingDate {

	public static void main(String[] args) throws IOException, ParseException {
		String strToTokenize = " Met Adam at office. Mostly interested in funds like New World Fund. Booked a breakfast date on 10/21 and 10/22. He is currently using Vanguard's ETF, but not satisfied with return. He seems little concerned about Brexit fiasco";
		System.out.println("Meeting date = " + checkDate(strToTokenize));

		Date presentDate = new Date();
		System.out.println("Present Date : " + presentDate.toString());
	}

	public static String checkDate(String note) throws IOException, ParseException {
		//		List<String> lSentences = TextTokenizer.tokenizeSentence(note);
		//		String nextMeeting = "NA";
		//		for (Iterator<String> iterator = lSentences.iterator(); iterator.hasNext();) {
		//			String sentence = (String) iterator.next();
		//		List<Date> datesInSentence = DateParser.getDateFromString(sentence);
		Date nextMeetingDate = new Date();
		Date presentDate = new Date();
		System.out.println("Present Date : " + presentDate.toString());
		System.out.println("Next Meeting Date (initial) : " + nextMeetingDate.toString());

		Set<Date> datesInSentence = DateParser.getDateFromString(note);
		if (datesInSentence != null && !datesInSentence.isEmpty()) {
			if (datesInSentence.size() == 1) {
				Iterator<Date> itr = datesInSentence.iterator();
				Date date = itr.next();
				if (date.after(presentDate)) {
					nextMeetingDate = date;
				}
			} else {
				for (Date date : datesInSentence) {
					if (date.after(presentDate)) {
						Date possibleNextMeetingDate = date;
						if (nextMeetingDate.equals(presentDate)) {
							nextMeetingDate = possibleNextMeetingDate;
						} else {
							if (possibleNextMeetingDate.before(nextMeetingDate)) {
								nextMeetingDate = possibleNextMeetingDate;
							}
						}
					}
				}
			}
		}

		//		for (Iterator<Date> iterator2 = datesInSentence.iterator(); iterator2.hasNext();) {
		//			Date date = (Date) iterator2.next();
		//			if (date.after(new Date())) {
		//				//if (sentence.toLowerCase().contains("meeting") ){
		//				//if(!nextMetting.equals("NA")){
		//
		//				//				DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		//				DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		//				nextMeeting = formatter.format(date);
		//				System.out.println("format 1 : " + formatter.format(date));
		//				//				System.out.println("format 1 : " + formatter.format(formatter.parse(date.toString())));
		//				//				System.out.println("format 2 : " + formatter1.format(formatter.parse(date.toString())));
		//
		//				//}
		//				//nextMetting = "Next meeting dates ";
		//				//nextMetting = nextMetting + date.toString(); 
		//				//}
		//			}
		//		}
		//		Date nearestMeetingDate = new Date();
		//		for (Iterator<Date> iterator = datesInSentence.iterator(); iterator.hasNext();) {
		//			Date date = (Date) iterator.next();
		//			if (date.after((Date) Calendar.getInstance().get(Calendar.DATE)) && date.before(nearestMeetingDate)) {
		//				nearestMeetingDate = date;
		//			}
		//		}

		//		}
		if (nextMeetingDate.after(presentDate)) {
			return nextMeetingDate.toString();
		} else {
			return "NA";
		}

	}

}
