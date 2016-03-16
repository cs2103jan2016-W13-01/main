package Parser;

import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class DateParser {

	public static Date getDate(String input) {
		Date date = null;
		Parser parser = new Parser();
		List<DateGroup> dateGroup= parser.parse(input);
		if (dateGroup.isEmpty()) { 
			System.out.println(date);
			return null; 
		}
		List<Date> dates = dateGroup.get(0).getDates();
		date = dates.get(0);
		System.out.println(date);
		return date;
	}

}
