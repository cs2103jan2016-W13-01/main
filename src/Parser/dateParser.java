package Parser;

import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class dateParser {

	public static Date getDate(String input) {
		Date date = new Date();
		Parser parser = new Parser();
		List<DateGroup> dateGroup= parser.parse(input);
		if (dateGroup.isEmpty()) { 
			return null; 
		}
		List<Date> dates = dateGroup.get(0).getDates();
		date = dates.get(0);
		return date;
	}

}
