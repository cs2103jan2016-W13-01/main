import static org.junit.Assert.*;

import org.junit.Test;

import Parser.titleParser;

public class TitleParserTest {

	@Test
	public void test() {
	titleParser tp = new titleParser();
	String string1 = "meeting by the seaside at johnsons place";
	assertEquals(string1,tp.getTitle("meeting by the seaside at johnsons place at tomorrow 2359 "));
	
	String string2 = "lunch with jack at jack's place";
	assertEquals(string2,tp.getTitle("lunch with jack at jack's place on 1st april"));
	
	String string3 = "";
	assertEquals(string3,tp.getTitle("1st april"));
	
	}

}
