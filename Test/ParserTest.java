import static org.junit.Assert.*;

import org.junit.Test;

public class ParserTest {

	@Test
	public void test() {
		Parser parserTester = new Parser();
		parserTester.init();
		assertFalse(parserTester.checkDate("31/20/2012"));
		assertTrue(parserTester.checkDate("29/04/2012"));
		assertFalse(parserTester.checkDate("2014/05/19"));
	}

}
