import static org.junit.Assert.*;

import org.junit.Test;

import Parser.GeneralParser;

public class TestGetPeriod {
	
	@Test
	public void test() {
		int period = GeneralParser.getRecurring("lunch 2pm every day");
		assertEquals(1, period);
	}
	
	@Test
	public void test1() {
		int period = GeneralParser.getRecurring("lunch 2pm every 2 days");
		assertEquals(2, period);
	}
	
	@Test
	public void test2() {
		int period = GeneralParser.getRecurring("lunch 2pm every 3 weeks");
		assertEquals(21, period);
	}
	
	@Test
	public void test3() {
		int period = GeneralParser.getRecurring("lunch 2pm every 2 months");
		assertEquals(-3, period);
	}
	
	@Test
	public void test4() {
		int period = GeneralParser.getRecurring("lunch 2pm every 3 years");
		assertEquals(-6, period);
	}
	
	@Test
	public void test5() {
		int period = GeneralParser.getRecurring("lunch at every day restaurant every week");
		assertEquals(7, period);
	}
}
