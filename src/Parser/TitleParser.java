package Parser;

import logic.commands.CommandType;

/* @@author A0121535R
 * parser that obtains the title
 */
public class TitleParser {
	static final String NO_YEAR_FORMATTED_DATE_REGEX = Regex.START + Regex.DAY + "[-/]" + Regex.MONTH + Regex.END;
	static final String FORMATTED_DATE_WITH_YEAR_REGEX = Regex.START + Regex.DAY + Regex.DATE_SEP + Regex.MONTH + Regex.DATE_SEP + Regex.YEAR + Regex.END;
	static final String NUMBERED_DATE_REGEX = NO_YEAR_FORMATTED_DATE_REGEX + "|" + FORMATTED_DATE_WITH_YEAR_REGEX;
	static final String MISC_NUMBERED_DATE_REGEX = Regex.MISC_REGEX+NO_YEAR_FORMATTED_DATE_REGEX + "|" + Regex.MISC_REGEX+FORMATTED_DATE_WITH_YEAR_REGEX;
	static final String FIRST_DATE_REGEX = Regex.DATE_MONTH_YEAR_REGEX+ "|" + Regex.MONTH_DATE_YEAR_REGEX+"|"+Regex.MONTH_DATE_REGEX+"|"+Regex.DATE_MONTH_REGEX;
	static final String MISC_FIRST_DATE_REGEX =  Regex.DATE_MONTH_YEAR_REGEX+ "|" + Regex.MONTH_DATE_YEAR_REGEX;
	static final String SECOND_DATE_REGEX=Regex.MISC_REGEX+ Regex.DATE_MONTH_REGEX+ "|" + Regex.MISC_REGEX+Regex.MONTH_DATE_REGEX;
	static final String MISC_SECOND_DATE_REGEX = Regex.MISC_REGEX+ Regex.DATE_MONTH_YEAR_REGEX+ "|" +Regex.MISC_REGEX+ Regex.MONTH_DATE_YEAR_REGEX;
	static final String FIRST_DAY_REGEX=Regex.START+Regex.MISC_REGEX+Regex.TMR_REGEX+ Regex.END +"|"+Regex.START+Regex.TMR_REGEX+ Regex.END ;

	static final String MISC_TIME_REGEX = Regex.MISC_REGEX + Regex.TWELVE_HR_REGEX + "|" + Regex.MISC_REGEX+ Regex.TWENTYFOUR_HR_REGEX;
	static final String SECOND_DAY_REGEX=Regex.MISC_REGEX + Regex.DAYNAME+Regex.END+"|"+Regex.DAYNAME+ Regex.END;

	static final String NOT_TITLE_REGEX = "("+ Regex.RECURRING_REGEX +"|"+ FIRST_DATE_REGEX +"|"+ SECOND_DATE_REGEX +"|"+ MISC_FIRST_DATE_REGEX +"|"+ MISC_SECOND_DATE_REGEX +"|"+
			FIRST_DAY_REGEX +"|"+ SECOND_DAY_REGEX +"|"+ MISC_NUMBERED_DATE_REGEX +"|"+ NUMBERED_DATE_REGEX +"|"+ Regex.TIME_REGEX+"|"+MISC_TIME_REGEX +"|"+Regex.TMR_REGEX+")";

	public static String getParsedTitle(String inputArgs,CommandType cmdType) {

		if (inputArgs == null) {
			return null;
		}
		if(cmdType.equals(CommandType.ADD)){
		String title = getAbsoluteTitle(inputArgs);
		if(title == null){
			title = getCustomTitle(inputArgs);
		}
		return title;
		}
		else{
			String title = getAbsoluteTitle(inputArgs);
			return title;
		}
	}

	private static String getCustomTitle(String inputArgs) {
		String[] strTok = inputArgs.split(NOT_TITLE_REGEX,2);
		System.out.println("title parsed = "+strTok[0]);
		String title = strTok[0].trim();
		return title;
	}

	private static String getAbsoluteTitle(String inputArgs) {
		int absIndexStart = inputArgs.indexOf("\""); 
		if(absIndexStart>=0){
			int absIndexEnd = inputArgs.indexOf("\"",absIndexStart+1);
			if(absIndexEnd >absIndexStart && absIndexEnd>=0){
				String title = inputArgs.substring(absIndexStart+1, absIndexEnd);
				title = title.trim();
				return title;
			}
		}
		return null;
	}















}
