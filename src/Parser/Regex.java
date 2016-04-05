package Parser;
/* @@author A0121535R
* class  that contains the general Regex
*/
public class Regex {
static final String REGEX_SPACE = "(\\s+)";
static final String START = "(?<=^|\\s)";
static final String END = "(?=\\s|$)";
static final String MISC_REGEX ="(at?|from?|to?|till? |until?|by?|at?|on?|next?|this?|by next?|by this?)"+REGEX_SPACE;
static final String MONTHNAME = "(jan(?:uary)?|feb(?:ruary)?|mar(?:ch)?|apr(?:il)?|may|jun(?:e)?|jul(?:y)?|aug(?:ust)?|sep(?:tember)?|oct(?:ober)?|nov(?:ember)?|dec(?:ember)?)";
static final String DATES = "(\\d?\\d)(?:st|rd|nd|th)?";
static final String YEAR = "(\\d{2}|\\d{4})?";

static final String DATE_MONTH_REGEX = "(?<=\\s|^)" + DATES + REGEX_SPACE + MONTHNAME + END;
static final String MONTH_DATE_REGEX = "(?<=\\s|^)" + MONTHNAME +REGEX_SPACE + DATES + END;
static final String DATE_MONTH_YEAR_REGEX = "(?<=\\s|^)" + DATES + REGEX_SPACE+ MONTHNAME + REGEX_SPACE + YEAR + END;
static final String MONTH_DATE_YEAR_REGEX = "(?<=\\s|^)" + MONTHNAME +REGEX_SPACE + DATES + REGEX_SPACE + YEAR + END;

static final String TMR_REGEX = "(today?|tmr?|tomorrow?)";

static final String DAYNAME = "(week?|month?|mon(?:day)?|tue(?:sday)?|wed(?:nesday)?|thur(?:sday)?|fri(?:day)?|sat(?:urday)?|sun(?:day)?)";
static final String TWELVE_HR_REGEX = "([0-9]?[0-9])([.:][0-9][0-9])?\\s?(am|pm)";
static final String TWENTYFOUR_HR_REGEX = "(([0-9]?[0-9])[:]([0-9][0-9]))\\s?[?:h|H]?";

static final String TIME_REGEX = "(" + Regex.TWELVE_HR_REGEX + "|" + Regex.TWENTYFOUR_HR_REGEX + ")";


static final String DAY = "(\\d?\\d)";
static final String MONTH = "(\\d?\\d)";
static final String DATE_SEP = "[-/]";


}