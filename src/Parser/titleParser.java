


/*
 * The following code to obtain the title is obtain from 
 * https://github.com/CS2103-Aug2015-w15-4j/main.git
 */

package Parser;

import java.util.regex.Pattern;

public class titleParser {

	static final String TO_REGEX = "-|to|until|till";
	static final String TASK_ID_REGEX = "(^[0-9]+(?=\\s|$))";
	static final String SEARCH_TASK_ID_REGEX = "(^[0-9]+\\s*(?=$))";
	static final String TAG_REGEX = "(?<=\\s|^)#(\\w+)";
	static final String DESCRIPTION_REGEX = "(?<!\\\\)\"(.*)(?<!\\\\)\"(?!.*((?<!\\\\)\"))";
	static final String ERROR_INVALID_TABID = "Error: Invalid tab ID";

	static final String START = "(?<=^|\\s)";
	static final String END = "(?=\\s|$)";
	static final String DD = "([0-9]?[0-9])";
	static final String MM = "([0-9]?[0-9])";
	static final String DATE_DELIM = "[-/.]";
	static final String YY = "(\\d{4}|\\d{2})";
	static final String NO_YEAR_FORMATTED_DATE_REGEX = START + DD + "[-/]" + MM + END;
	static final String FORMATTED_DATE_WITH_YEAR_REGEX = START + DD + DATE_DELIM + MM + DATE_DELIM + YY + END;
	static final String FORMATTED_DATE_REGEX = NO_YEAR_FORMATTED_DATE_REGEX + "|" + FORMATTED_DATE_WITH_YEAR_REGEX;
	static final String FORMATTED_DATE_FORMAT = "d/M/yy";
	
	private static final String MONTHS = "(jan(?:uary)?|feb(?:ruary)?|mar(?:ch)?|apr(?:il)?|may|jun(?:e)?|jul(?:y)?|aug(?:ust)?|sep(?:tember)?|oct(?:ober)?|nov(?:ember)?|dec(?:ember)?)";
	private static final String DATES = "(\\d?\\d)(?:st|rd|nd|th)?";
	private static final String YEARS = "(\\d{4})?";
	
	private static final String DATE_MONTH_REGEX = "(?<=\\s|^)" + DATES + "\\s" + MONTHS + ",?\\s?" + YEARS + "(?=\\s|$)";
	private static final String MONTH_DATE_REGEX = "(?<=\\s|^)" + MONTHS + "\\s" + DATES + ",?\\s?" + YEARS + "(?=\\s|$)";
	

	protected static final String FLEXIBLE_DATE_REGEX = "(" + DATE_MONTH_REGEX + "|" + MONTH_DATE_REGEX + ")";
	

	static final String TOMORROW_REGEX = "((?<=\\s|^)(tmr|tomorrow|tomorow).*(?=\\s|$))";
	static final String NO_KEYWORD_DATE_REGEX = FORMATTED_DATE_REGEX + "|" 
			+ FLEXIBLE_DATE_REGEX + "|" + TOMORROW_REGEX;
	
	
	
	static final String TWELVE_HR_REGEX = "(?<=\\s|^)([0-9]?[0-9])([.:][0-9][0-9])?\\s?(am|pm)?(\\s?(?:-|to|until|til|till)\\s?([0-9]?[0-9])([.:][0-9][0-9])?\\s?)?(am|pm)(?=\\s|$)";
	static final String TWENTYFOUR_HR_REGEX = "(?<=\\s|^)(([0-9]?[0-9])[:]([0-9][0-9]))\\s?[?:h|H]?\\s?((?:-|to|until|til|till)?\\s?(([0-9]?[0-9])[:]([0-9][0-9])))?\\s?[?:h|H]?(?=\\s|$)";
		
	static final String TIME_REGEX = "(" + TWELVE_HR_REGEX + "|" + TWENTYFOUR_HR_REGEX + ")";
	
	static final Pattern HHMM = Pattern.compile(TWENTYFOUR_HR_REGEX);
	static final Pattern HMMA = Pattern.compile(TWELVE_HR_REGEX);
	
	static final String TWELVE_HR_FORMAT = "h:mma";
	static final String TWENTY_FOUR_HR_FORMAT = "HH:mm";

	
	static final String NO_KEYWORD_DATE_TIME_REGEX = "(" + TIME_REGEX + "|" + NO_KEYWORD_DATE_REGEX + ")";
	private static final String NOT_TITLE_REGEX_WITH_KEYWORD = "(" 
								+ "((?<=\\s|^)(from |fr |at |to |til |till |until |by |on |- ))?" 
								+ NO_KEYWORD_DATE_TIME_REGEX + "|" + TAG_REGEX + "|" 
								+ DESCRIPTION_REGEX + ")";  	
	
	public static String getTitle(String inputArgs) {
		
		if (inputArgs == null) {
			return null;
		}
		inputArgs =inputArgs.replaceAll("(?i)"+NOT_TITLE_REGEX_WITH_KEYWORD,"");
		System.out.println(inputArgs);
		return inputArgs.trim();
	}


}
