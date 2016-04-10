import static org.junit.Assert.*;

import org.junit.Test;

import parser.newTitleParser;
import parser.titleParser;

public class TitleParserTest {

	@Test
	public void test() {
	newTitleParser tp = new newTitleParser();
	String string1 = "meeting by the seaside at johnsons place";
	assertEquals(string1,tp.getTitle("meeting by the seaside at johnsons place at tomorrow 2359"));
	
	String string2 = "submit cs2106 assignment";
	assertEquals(string2,tp.getTitle("submit cs2106 assignment on 1st april"));
	
	String string3 = "do some random thing";
	assertEquals(string3,tp.getTitle("do some random thing by 02/15"));
	
	String string4 = "do task3";
	assertEquals(string4,tp.getTitle("do task3 next friday"));
	
	String string5 = "do task4";
	assertEquals(string5,tp.getTitle("do task4"));
	
	}

}
