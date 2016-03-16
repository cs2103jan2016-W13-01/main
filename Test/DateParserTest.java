import static org.junit.Assert.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import Parser.DateParser;

public class DateParserTest {
	
	@Test
	public void test() throws ParseException {
		java.util.Date date = null;
		DateParser dp = new DateParser();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy HHmm");
		sdf.setLenient(false);
		date= sdf.parse("16032016 2359");
		assertEquals(date,dp.getDate("meeting by the seaside at johnsons place 16 march 2016 2359"));
		
		date = sdf.parse("01042016 2359");
		assertEquals(date,dp.getDate("lunch with jack at jack's place on 2359 1st april"));
		
		date = null;
		assertEquals(date,dp.getDate("lunch with jahn"));
		
		
	}
	

}
