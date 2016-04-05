package Parser;
/* @@author A0121535R
* parser that obtains the title
*/
public class TitleParser {
	static final String NO_YEAR_FORMATTED_DATE_REGEX = Regex.START + Regex.DAY + "[-/]" + Regex.MONTH + Regex.END;
	static final String FORMATTED_DATE_WITH_YEAR_REGEX = Regex.START + Regex.DAY + Regex.DATE_SEP + Regex.MONTH + Regex.DATE_SEP + Regex.YEAR + Regex.END;
	static final String NUMBERED_DATE_REGEX = NO_YEAR_FORMATTED_DATE_REGEX + "|" + FORMATTED_DATE_WITH_YEAR_REGEX;
	static final String MISC_NUMBERED_DATE_REGEX = Regex.MISC_REGEX+NO_YEAR_FORMATTED_DATE_REGEX + "|" + Regex.MISC_REGEX+FORMATTED_DATE_WITH_YEAR_REGEX;
	static final String FIRST_DATE_REGEX = "("+ Regex.DATE_MONTH_YEAR_REGEX+ "|" + Regex.MONTH_DATE_YEAR_REGEX+"|"+Regex.MONTH_DATE_REGEX+"|"+Regex.DATE_MONTH_REGEX+")";
	static final String MISC_FIRST_DATE_REGEX = "("+ Regex.DATE_MONTH_YEAR_REGEX+ "|" + Regex.MONTH_DATE_YEAR_REGEX  +")";
	static final String SECOND_DATE_REGEX="("+Regex.MISC_REGEX+ Regex.DATE_MONTH_REGEX+ "|" + Regex.MISC_REGEX+Regex.MONTH_DATE_REGEX  +")";
	static final String MISC_SECOND_DATE_REGEX = "("+Regex.MISC_REGEX+ Regex.DATE_MONTH_YEAR_REGEX+ "|" +Regex.MISC_REGEX+ Regex.MONTH_DATE_YEAR_REGEX  +")";
	static final String FIRST_DAY_REGEX="("+ Regex.MISC_REGEX+Regex.TMR_REGEX+  "(?=\\s|$)"+ ")";
	
	static final String MISC_TIME_REGEX = "("+Regex.MISC_REGEX + Regex.TWELVE_HR_REGEX + "|" + Regex.MISC_REGEX+ Regex.TWENTYFOUR_HR_REGEX + ")";
	static final String SECOND_DAY_REGEX="("+ Regex.MISC_REGEX + Regex.DAYNAME+"(?=\\s|$)"+"|"+Regex.DAYNAME+ "(?=\\s|$)"  +")";

	static final String NOT_TITLE_REGEX = "("+ FIRST_DATE_REGEX +"|"+ SECOND_DATE_REGEX +"|"+ MISC_FIRST_DATE_REGEX +"|"+ MISC_SECOND_DATE_REGEX +"|"+
			FIRST_DAY_REGEX +"|"+ SECOND_DAY_REGEX +"|"+ MISC_NUMBERED_DATE_REGEX +"|"+ NUMBERED_DATE_REGEX +"|"+ Regex.TIME_REGEX+"|"+MISC_TIME_REGEX +"|"+Regex.TMR_REGEX+")";
	
	public static String getTitle(String inputArgs) {
		
		if (inputArgs == null) {
			return null;
		}
		/*
		inputArgs = inputArgs.replaceAll(NOT_TITLE_REGEX, "");
		System.out.println(inputArgs);
		*/
		String[] strTok = inputArgs.split(NOT_TITLE_REGEX,2);
		System.out.println("title parsed = "+strTok[0]);
		return strTok[0].trim();
		/*inputArgs =inputArgs.replaceAll(FIRST_DATE_REGEX,"");
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
		*/

	}















}
