import java.io.IOException;

import org.junit.Test;

//@@author: A0134185H
public class testAddAndDelete {
		
	@Test
	public void test() throws IOException {
		/*
		String output = FileUtils.readFileToString(IntegratedTestRunner.runTest("in1.txt"), "UTF-8");
		String expected = FileUtils.readFileToString(IntegratedTestRunner.getExpectedFile("in1.txt.expected"), "UTF-8");
		assertEquals(expected, output);
		*/
		IntegratedTestRunner.runTest("in1.txt");
	}
}
