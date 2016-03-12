import static org.junit.Assert.*;

import org.junit.Test;

import Parser.titleParser;

public class TitleParserTest {

	@Test
	public void test() {
	titleParser tp = new titleParser();
	tp.getTitle("meeting by the seaside at johnsons place at tomorrow 2359");
	}

}
