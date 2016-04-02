package Parser;
/* @@author A0121535R
 * parser that obtains the different date Strings then use natty parser to parse
 */
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateParser {

	static final String START_DATE_KEYWORD = "(" + "((?<=\\s|^)(at?|from?))?" + ")";
	static final String CONNECT_DATE_KEYWORD =  "(to |till |until )";
	static final String DATE_KEYWORD = "("+START_DATE_KEYWORD + newTitleParser.NOT_TITLE_REGEX+"|"+newTitleParser.NOT_TITLE_REGEX+")";
	public static Date[] getDates(String input) {
		System.out.println("this is input to getDates "+input);

		Pattern r = Pattern.compile(DATE_KEYWORD);
		Matcher m = r.matcher(input);
		if (m.find( )) {
			System.out.println("Found value: " + m.group(0) );
			System.out.println("found value2: "+ m.group(1));
		} else {
			System.out.println("NO MATCH");
			return new Date[0];
		}

		String[] datesToken = m.group(0).split(CONNECT_DATE_KEYWORD,2);
		try{
			if(datesToken.length==2){
				Date[] dates = new Date[2];
				dates[0]=NattyDateParser.getDate(datesToken[0]);
				dates[1]=NattyDateParser.getDate(datesToken[1]);
				return dates;
			}
			else {
				Date[] dates = new Date[1];
				System.out.println("this is size "+datesToken.length);
				System.out.println("DATETOEKN = "+datesToken[0]);
				dates[0]=NattyDateParser.getDate(datesToken[0]);
				return dates;
			}
		}catch(NullPointerException e){
			return new Date[0];
		}

	}



}
