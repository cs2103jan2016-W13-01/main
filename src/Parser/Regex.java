package Parser;
/* @@author A0121535R
 * class  that contains the general Regex
 */
public class Regex {
	static final String REGEX_SPACE = "(\\s+)";
	static final String START = "(?<=^|\\s)";
	static final String END = "(?=\\s|$)";
	static final String MISC_REGEX ="(from?|to?|till?|until?|by?|on?|next?|this?|by next?|by this?)?";
	static final String MONTHNAME = "(jan(?:uary)?|feb(?:ruary)?|mar(?:ch)?|apr(?:il)?|may|jun(?:e)?|jul(?:y)?|aug(?:ust)?|sep(?:tember)?|oct(?:ober)?|nov(?:ember)?|dec(?:ember)?)";
	static final String DATES = "(\\d?\\d)(?:st|rd|nd|th)?";
	static final String YEAR = "(\\d{2}|\\d{4})?";
	static final String DATE_MONTH_REGEX = "(?<=\\s|^)" + DATES + REGEX_SPACE + MONTHNAME + END;
	static final String MONTH_DATE_REGEX = "(?<=\\s|^)" + MONTHNAME +REGEX_SPACE + DATES + END;
	static final String DATE_MONTH_YEAR_REGEX = "(?<=\\s|^)" + DATES + REGEX_SPACE+ MONTHNAME + REGEX_SPACE + YEAR + END;
	static final String MONTH_DATE_YEAR_REGEX = "(?<=\\s|^)" + MONTHNAME +REGEX_SPACE + DATES + REGEX_SPACE + YEAR + END;
	static final String TMR_REGEX = "(today?|tmr?|tomorrow?)";
	static final String DAYNAME = "(next week?|next month?|mon(?:day)?|tue(?:sday)?|wed(?:nesday)?|thur(?:sday)?|fri(?:day)?|sat(?:urday)?|sun(?:day)?)";
	static final String TWELVE_HR_REGEX = "([0-9]?[0-9])([.:][0-9][0-9])?\\s?(am|pm)";
	static final String TWENTYFOUR_HR_REGEX = "(([0-9]?[0-9])[:]([0-9][0-9]))\\s?[?:h|H]?";
	static final String NUM_REGEX= "(\\d+)";
	static final String TIME_REGEX = "(" + Regex.TWELVE_HR_REGEX + "|" + Regex.TWENTYFOUR_HR_REGEX + ")";
	static final String DAY = "(\\d?\\d)";
	static final String MONTH = "(\\d?\\d)";
	static final String DATE_SEP = "[-/]";
	static final String PERIOD_DAY_REGEX = "every"+REGEX_SPACE+"day" + END;
	static final String PERIOD_WEEK_REGEX ="every"+REGEX_SPACE+"week" + END;
	static final String PERIOD_MONTH_REGEX ="every"+REGEX_SPACE+"month" + END;
	static final String PERIOD_YEAR_REGEX ="every"+REGEX_SPACE+"year" + END;
	static final String PERIOD_DAY_REGEX_MUL = "every "+"(\\d+)"+" (day|days)" + END;
	static final String PERIOD_WEEK_REGEX_MUL ="every "+"(\\d+)"+" (week|weeks)" + END;
	static final String PERIOD_MONTH_REGEX_MUL ="every "+"(\\d+)"+" (month|months)" + END;
	static final String PERIOD_YEAR_REGEX_MUL ="every "+"(\\d+)"+" (year|years)" + END;
	static final String RECURRING_REGEX= PERIOD_DAY_REGEX+ "|" +PERIOD_WEEK_REGEX +"|"+PERIOD_MONTH_REGEX+"|"+PERIOD_YEAR_REGEX;
	static final String RECURRING_REGEX_MUL=PERIOD_DAY_REGEX_MUL+ "|" +PERIOD_WEEK_REGEX_MUL +"|"+PERIOD_MONTH_REGEX_MUL+"|"+PERIOD_YEAR_REGEX_MUL;
	static final String RECURRING_REGEX_FINAL = RECURRING_REGEX+"|"+RECURRING_REGEX_MUL;
}