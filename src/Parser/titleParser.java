/*
* The following code to obtain the title is referenced from 
* https://github.com/CS2103-Aug2015-w15-4j/main.git
*/

package Parser;

public class titleParser {

	private static final String START = "(?<=^|\\s)";
	private static final String END = "(?=\\s|$)";
	private static final String DD = "([0-9]?[0-9])";
	private static final String MM = "([0-9]?[0-9])";
	private static final String DATE_SEP = "[-/.]";
	private static final String YY = "(\\d{4}|\\d{2})";
	private static final String DDMMYY= "(\\d{4}|\\d{6})";
	private static final String NO_YEAR_FORMATTED_DATE_REGEX = START + DD + "[-/]" + MM + END;
	private static final String FORMATTED_DATE_WITH_YEAR_REGEX = START + DD + DATE_SEP + MM + DATE_SEP + YY + END;
	private static final String FORMATTED_DATE_REGEX = NO_YEAR_FORMATTED_DATE_REGEX + "|" + FORMATTED_DATE_WITH_YEAR_REGEX+"|"+" "+DDMMYY;
	
	private static final String MONTHS = "(jan(?:uary)?|feb(?:ruary)?|mar(?:ch)?|apr(?:il)?|may|jun(?:e)?|jul(?:y)?|aug(?:ust)?|sep(?:tember)?|oct(?:ober)?|nov(?:ember)?|dec(?:ember)?)";
	private static final String DATES = "(\\d?\\d)";
	
	private static final String DATE_MONTH_REGEX =  DATES + " " + MONTHS + " " + YY;
	private static final String MONTH_DATE_REGEX = MONTHS + " " + DATES + " " + YY;

	protected static final String FLEXIBLE_DATE_REGEX = "(" + DATE_MONTH_REGEX + "|" + MONTH_DATE_REGEX + ")";

	private static final String MISC_REGEX = "((tmr|tomorrow|tomorow|next|by next|by this|at next|at this).*(?=\\s|$))";
	private static final String FINAL_DATE_REGEX = FORMATTED_DATE_REGEX + "|" + FLEXIBLE_DATE_REGEX + "|" + MISC_REGEX;
	
	private static final String TWELVE_HR_REGEX = "([0-9]?[0-9])([.:][0-9][0-9])?\\s?(am|pm)?(\\s?(?:-|to|until|til|till)\\s?([0-9]?[0-9])([.:][0-9][0-9])?\\s?)?(am|pm)(?=\\s|$)";
	private static final String TWENTYFOUR_HR_REGEX = "(([0-9]?[0-9])[:]([0-9][0-9]))\\s?[?:h|H]?\\s?((?:-|to|until|til|till)?\\s?(([0-9]?[0-9])[:]([0-9][0-9])))?\\s?[?:h|H]?(?=\\s|$)";
		
	private static final String TIME_REGEX = "(" + TWELVE_HR_REGEX + "|" + TWENTYFOUR_HR_REGEX + ")";

	private static final String FINAL_DATE_TIME_REGEX = "(" + TIME_REGEX + "|" + FINAL_DATE_REGEX + ")";
	
	public static String getTitle(String inputArgs) {
		
		if (inputArgs == null) {
			return null;
		}
		inputArgs =inputArgs.replaceAll("(?i)"+FINAL_DATE_TIME_REGEX,"");
		System.out.println(inputArgs);
		return inputArgs.trim();
	}


}
