package Parser;
/* @@author A0121535R
* parser that obtains the title
*/
public class newTitleParser {
	private static final String START = "(?<=^|\\s)";
	private static final String END = "(?=\\s|$)";
	private static final String MISC_REGEX ="(by?|at?|on?|next?|this?|by next?|by this?)"+"\\s";
	private static final String MONTHNAME = "(jan(?:uary)?|feb(?:ruary)?|mar(?:ch)?|apr(?:il)?|may|jun(?:e)?|jul(?:y)?|aug(?:ust)?|sep(?:tember)?|oct(?:ober)?|nov(?:ember)?|dec(?:ember)?)";
	private static final String DATES = "(\\d?\\d)(?:st|rd|nd|th)?";
	private static final String YEAR = "(\\d{2}|\\d{4})";
	
	private static final String DATE_MONTH_REGEX = "(?<=\\s|^)" + DATES + "\\s" + MONTHNAME + "(?=\\s|$)";
	private static final String MONTH_DATE_REGEX = "(?<=\\s|^)" + MONTHNAME + "\\s" + DATES + "(?=\\s|$)";
	private static final String DATE_MONTH_YEAR_REGEX = "(?<=\\s|^)" + DATES + "\\s" + MONTHNAME + "?\\s?" + YEAR + "(?=\\s|$)";
	private static final String MONTH_DATE_YEAR_REGEX = "(?<=\\s|^)" + MONTHNAME + "\\s" + DATES + "?\\s?" + YEAR + "(?=\\s|$)";
	
	
	private static final String FIRST_DATE_REGEX = "("+ DATE_MONTH_YEAR_REGEX+ "|" + MONTH_DATE_YEAR_REGEX  +")";
	private static final String MISC_FIRST_DATE_REGEX = "("+ DATE_MONTH_YEAR_REGEX+ "|" + MONTH_DATE_YEAR_REGEX  +")";
	private static final String SECOND_DATE_REGEX="("+MISC_REGEX+ DATE_MONTH_REGEX+ "|" + MISC_REGEX+MONTH_DATE_REGEX  +")";
	private static final String MISC_SECOND_DATE_REGEX = "("+MISC_REGEX+ DATE_MONTH_YEAR_REGEX+ "|" +MISC_REGEX+ MONTH_DATE_YEAR_REGEX  +")";

	private static final String TMR_REGEX = "((tmr|tomorrow).*(?=\\s|$))";
	
	private static final String FIRST_DAY_REGEX="("+ MISC_REGEX+TMR_REGEX+ ")";
	private static final String DAYNAME = "(month?|mon(?:day)?|tue(?:day)?|wed(?:nesday)?|thur(?:sday)?|fri(?:day)?|sat(?:urday)?|sun(?:day)?)";
	private static final String SECOND_DAY_REGEX="("+ MISC_REGEX + DAYNAME+"|"+DAYNAME  +")";
	
	private static final String DAY = "(\\d?\\d)";
	private static final String MONTH = "(\\d?\\d)";
	private static final String DATE_SEP = "[-/]";
	private static final String NO_YEAR_FORMATTED_DATE_REGEX = START + DAY + "[-/]" + MONTH + END;
	private static final String FORMATTED_DATE_WITH_YEAR_REGEX = START + DAY + DATE_SEP + MONTH + DATE_SEP + YEAR + END;
	private static final String NUMBERED_DATE_REGEX = NO_YEAR_FORMATTED_DATE_REGEX + "|" + FORMATTED_DATE_WITH_YEAR_REGEX;
	private static final String MISC_NUMBERED_DATE_REGEX = MISC_REGEX+NO_YEAR_FORMATTED_DATE_REGEX + "|" + MISC_REGEX+FORMATTED_DATE_WITH_YEAR_REGEX;
	private static final String TWELVE_HR_REGEX = "([0-9]?[0-9])([.:][0-9][0-9])?\\s?(am|pm)?(\\s?(?:-|to|until|til|till)\\s?([0-9]?[0-9])([.:][0-9][0-9])?\\s?)?(am|pm)(?=\\s|$)";
	private static final String TWENTYFOUR_HR_REGEX = "(([0-9]?[0-9])[:]([0-9][0-9]))\\s?[?:h|H]?\\s?((?:-|to|until|til|till)?\\s?(([0-9]?[0-9])[:]([0-9][0-9])))?\\s?[?:h|H]?(?=\\s|$)";
		
	private static final String TIME_REGEX = "(" + TWELVE_HR_REGEX + "|" + TWENTYFOUR_HR_REGEX + ")";
	
	public static String getTitle(String inputArgs) {
		
		if (inputArgs == null) {
			return null;
		}
		inputArgs =inputArgs.replaceAll(FIRST_DATE_REGEX,"");
		System.out.println(inputArgs);
		inputArgs =inputArgs.replaceAll(SECOND_DATE_REGEX,"");
		System.out.println(inputArgs);
		inputArgs =inputArgs.replaceAll(MISC_FIRST_DATE_REGEX,"");
		System.out.println(inputArgs);
		inputArgs =inputArgs.replaceAll(MISC_SECOND_DATE_REGEX,"");
		System.out.println(inputArgs);
		inputArgs =inputArgs.replaceAll(FIRST_DAY_REGEX,"");
		System.out.println(inputArgs);
		inputArgs =inputArgs.replaceAll(SECOND_DAY_REGEX,"");
		System.out.println(inputArgs);
		inputArgs =inputArgs.replaceAll(MISC_NUMBERED_DATE_REGEX,"");
		System.out.println(inputArgs);
		inputArgs =inputArgs.replaceAll(NUMBERED_DATE_REGEX,"");
		System.out.println(inputArgs);
		inputArgs =inputArgs.replaceAll(TIME_REGEX,"");
		System.out.println(inputArgs);
		return inputArgs.trim();
	}















}
