package Parser;
/* @@author A0121535R
 * parser that obtains the different date Strings then use natty parser to parse
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;


public class DateParser {

	static final String START_DATE_KEYWORD = "(" + "((?<=\\s|^)(at?|from?))?" + ")";
	static final String CONNECT_DATE_KEYWORD =  "(to |till |until )";

	static final String ALL_DATE_REGEX = "("+ TitleParser.FIRST_DATE_REGEX +"|"+ TitleParser.SECOND_DATE_REGEX +"|"+ TitleParser.MISC_FIRST_DATE_REGEX +"|"+ TitleParser.MISC_SECOND_DATE_REGEX +"|"+
			TitleParser.FIRST_DAY_REGEX +"|"+ TitleParser.SECOND_DAY_REGEX +"|"+ TitleParser.MISC_NUMBERED_DATE_REGEX +"|"+ TitleParser.NUMBERED_DATE_REGEX +"|"+TitleParser.MISC_TIME_REGEX +"|"+Regex.TMR_REGEX+")";
	static final String DATE_KEYWORD = "("+START_DATE_KEYWORD + TitleParser.NOT_TITLE_REGEX+"|"+TitleParser.NOT_TITLE_REGEX+")";

	public static Calendar[] getDates(String input) {
		System.out.println("this is input to getDates "+input);
		ArrayList<String> dateList = new ArrayList<String>();
		ArrayList<String> timeList = new ArrayList<String>();


		Pattern pattern = Pattern.compile(ALL_DATE_REGEX);
		Matcher matcher = pattern.matcher(input);	

		while (matcher.find( )) {
			System.out.println("Found DATE value: " + matcher.group() );
			dateList.add(matcher.group());
		} 

		pattern = Pattern.compile(Regex.TIME_REGEX);
		matcher = pattern.matcher(input);	

		while (matcher.find( )) {
			System.out.println("Found TIME value: " + matcher.group() );
			timeList.add(matcher.group());
		} 

		try{
			String startTime="";
			String endTime="";
			if(dateList.size()==2){
				Date[] dates = new Date[2];
				if(timeList.size()==2){
					startTime = timeList.get(0)+" "+dateList.get(0);
					endTime= timeList.get(1) +" "+dateList.get(1);
					dates[0]=NattyDateParser.getDate(startTime);
					dates[1]=NattyDateParser.getDate(endTime);

				}
				else if(timeList.size()==0){
					startTime = dateList.get(0);
					endTime = dateList.get(1);
					dates[0]=NattyDateParser.getDate(startTime);
					dates[1]=NattyDateParser.getDate(endTime);
				}
				else{
					startTime = timeList.get(0)+" "+dateList.get(0);
					endTime= timeList.get(0) +" "+dateList.get(1);
					dates[0]=NattyDateParser.getDate(startTime);
					dates[1]=NattyDateParser.getDate(endTime);

				}
				return convertFromDate(dates); 
			}
			else if(dateList.size()==0){
				if(timeList.size()==2){
					Date[] dates = new Date[2];
					startTime = timeList.get(0);
					endTime= timeList.get(1);
					dates[0]=NattyDateParser.getDate(startTime);
					dates[1]=NattyDateParser.getDate(endTime);
					return convertFromDate(dates); 
				}
				else if(timeList.size()==0){
					return convertFromDate(new Date[0]);
				}
				else{
					Date[] dates = new Date[1];
					startTime = timeList.get(0);
					dates[0]=NattyDateParser.getDate(startTime);
					return convertFromDate(dates); 
				}
			}
			else {
				if(timeList.size()==2){
					Date[] dates = new Date[2];
					startTime = timeList.get(0)+" "+dateList.get(0);
					endTime= timeList.get(1) +" "+dateList.get(0);
					dates[0]=NattyDateParser.getDate(startTime);
					dates[1]=NattyDateParser.getDate(endTime);
					return convertFromDate(dates); 
				}
				else if(timeList.size()==0){
					Date[] dates = new Date[1];
					startTime = dateList.get(0);
					dates[0]=NattyDateParser.getDate(startTime);
					return convertFromDate(dates); 
				}
				else{
					Date[] dates = new Date[1];
					startTime = timeList.get(0)+" "+dateList.get(0);
					dates[0]=NattyDateParser.getDate(startTime);
					return convertFromDate(dates); 
				}
			}
		}catch(NullPointerException e){
			return convertFromDate(new Date[0]);
		}

	}

	public static Calendar[] convertFromDate(Date[] dates) {
		Calendar[] result = new Calendar[dates.length];
		for (int i=0; i<dates.length; i++) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(DateUtils.truncate(dates[i], Calendar.SECOND));
			result[i] = cal;
		}
		return result;
	}



}
