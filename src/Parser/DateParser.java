package Parser;
//@@author A0121535R
// parser that obtains the different date Strings then use natty parser to parse
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.time.DateUtils;

public class DateParser {

	static final String START_DATE_KEYWORD = "(" + "((?<=\\s|^)(at?|from?))?" + ")";
	static final String CONNECT_DATE_KEYWORD =  "(to |till |until )";

	static final String ALL_DATE_REGEX = "("+ TitleParser.FIRST_DATE_REGEX +"|"+ TitleParser.SECOND_DATE_REGEX +"|"+ TitleParser.MISC_FIRST_DATE_REGEX +"|"+ TitleParser.MISC_SECOND_DATE_REGEX +"|"+
			TitleParser.FIRST_DAY_REGEX +"|"+ TitleParser.SECOND_DAY_REGEX +"|"+ TitleParser.MISC_NUMBERED_DATE_REGEX +"|"+ TitleParser.NUMBERED_DATE_REGEX +")";
	
	//initial method called by other parser classes
	public static Calendar[] getDates(String input) {
		System.out.println("this is input to getDates "+input);
		ArrayList<String> dateList = new ArrayList<String>();
		ArrayList<String> timeList = new ArrayList<String>();
		Calendar[] cal =  getCalendar(input, dateList, timeList);
		return cal;
	}
	
	//obtain the part of the string that has to do with dates
	//obtain the part of the string that has to do with times
	private static Calendar[] getCalendar(String input, ArrayList<String> dateList, ArrayList<String> timeList) {
		extractDays(input, dateList); 
		extractTimes(input, timeList); 
		Calendar[] cal = sortByDateSize(dateList, timeList);
		return cal;
	}

	private static void extractTimes(String input, ArrayList<String> timeList) {
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(Regex.TIME_REGEX);
		matcher = pattern.matcher(input);	
		getTimes(timeList, matcher);
	}

	private static void extractDays(String input, ArrayList<String> dateList) {
		Pattern pattern = Pattern.compile(ALL_DATE_REGEX);
		Matcher matcher = pattern.matcher(input);	
		getDays(dateList, matcher);
	}
	
	//sort by the number of dates input
	//e.g 15 may and/or 03/03/2013
	private static Calendar[] sortByDateSize(ArrayList<String> dateList, ArrayList<String> timeList) {
		try{
			if(dateList.size()==2){
				Date[] dates = dateSizeTwo(dateList, timeList);
				return convertFromDate(dates); 
			} else if(dateList.size()==0){
				Calendar[] cal = dateSizeZero(timeList);
				return cal;
			} else {
				return dateSizeOne(dateList, timeList);
			}
		} catch(NullPointerException e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "date nullpointer error", e);
			return convertFromDate(new Date[0]);
		}
	}
	
	//if input date is only one
	private static Calendar[] dateSizeOne(ArrayList<String> dateList, ArrayList<String> timeList) {
		String startTime;
		String endTime;
		if(timeList.size()==2){
			Date[] dates = new Date[2];
			startTime = timeList.get(0)+" "+dateList.get(0);
			endTime= timeList.get(1) +" "+dateList.get(0);
			obtainDatesArray(startTime, endTime, dates);
			return convertFromDate(dates); 
		} else if(timeList.size()==0){
			Date[] dates = new Date[1];
			startTime = dateList.get(0);
			dates[0]=NattyDateParser.getDate(startTime);
			return convertFromDate(dates); 
		} else{
			Date[] dates = new Date[1];
			startTime = timeList.get(0)+" "+dateList.get(0);
			dates[0]=NattyDateParser.getDate(startTime);
			return convertFromDate(dates); 
		}
	}
	
	//if no input date
	private static Calendar[] dateSizeZero(ArrayList<String> timeList) {
		String startTime;
		String endTime;
		if(timeList.size()==2){
			Date[] dates = new Date[2];
			startTime = timeList.get(0);
			endTime= timeList.get(1);
			obtainDatesArray(startTime, endTime, dates);
			return convertFromDate(dates); 
		} else if(timeList.size()==0){
			return convertFromDate(new Date[0]);
		} else{
			Date[] dates = new Date[1];
			startTime = timeList.get(0);
			dates[0]=NattyDateParser.getDate(startTime);
			return convertFromDate(dates); 
		}
	}
	
	//if input date is two
	private static Date[] dateSizeTwo(ArrayList<String> dateList, ArrayList<String> timeList) {
		String startTime;
		String endTime;
		Date[] dates = new Date[2];
		if(timeList.size()==2){		
			startTime = obtainFullDateString(timeList.get(0),dateList.get(0));
			endTime = obtainFullDateString(timeList.get(1),dateList.get(1));
			obtainDatesArray(startTime, endTime, dates);
		} else if(timeList.size()==0){
			startTime = dateList.get(0);
			endTime = dateList.get(1);
			obtainDatesArray(startTime, endTime, dates);
		} else{
			startTime = obtainFullDateString(timeList.get(0),dateList.get(0));
			endTime = obtainFullDateString(timeList.get(0),dateList.get(1));
			obtainDatesArray(startTime, endTime, dates);
		}
		return dates;
	}
	
	//concat the time and date together
	private static String obtainFullDateString (String time , String date){
		String string = time+" "+date;
		return string;
	}
	
	private static void obtainDatesArray(String startTime, String endTime, Date[] dates) {
		dates[0]=NattyDateParser.getDate(startTime);
		dates[1]=NattyDateParser.getDate(endTime);
	}

	private static void getTimes(ArrayList<String> timeList, Matcher matcher) {
		while (matcher.find( )) {
			System.out.println("Found TIME value: " + matcher.group() );
			timeList.add(matcher.group());
		}
	}

	private static void getDays(ArrayList<String> dateList, Matcher matcher) {
		while (matcher.find( )) {
			System.out.println("Found DATE value: " + matcher.group() );
			dateList.add(matcher.group());
		}
	}

	public static Calendar[] convertFromDate(Date[] dates) {
		Calendar[] result = new Calendar[dates.length];
		for (int i=0; i<dates.length; i++) {
			Calendar cal = obtainCalendarDates(dates, result, i);
			System.out.println(cal);
		}
		return result;
	}

	private static Calendar obtainCalendarDates(Date[] dates, Calendar[] result, int i) {
		Calendar cal;
		Date date = dates[i];
		cal = getCal(date);
		result[i] = cal;
		return cal;
	}

	private static Calendar getCal(Date date) {
		Calendar cal;
		if (date != null) {
			cal = Calendar.getInstance();
			cal.setTime(DateUtils.truncate(date, Calendar.SECOND));
		} else {
			cal = null;
		}
		return cal;
	}



}
