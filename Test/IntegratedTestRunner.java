import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import logic.TextUI;
import storage.StorageController;

//@@author: A0134185H
public class IntegratedTestRunner {
	private static final String inputDirName = "./Test/input";
	private static final String outputDirName = "./Test/actual";
	private static final String expectedDirName = "./Test/expected";
	
	public static void runTest(String inputFileName) throws IOException {
		TextUI.initialize();
		StorageController.clearAllTasks();
		File inputDir = new File(inputDirName);
		if (!inputDir.exists()) {
			inputDir.mkdirs();
		}
		File outputDir = new File(outputDirName);
		if (!outputDir.exists()) {
			outputDir.mkdirs();
		}
		File expectedDir = new File(expectedDirName);
		if (!expectedDir.exists()) {
			expectedDir.createNewFile();
		}		
		File expectedFile = new File(expectedDirName + "/" + inputFileName + ".expected");
		if (!expectedFile.exists()) {
			expectedFile.createNewFile();
		}
		File inputFile = new File(inputDirName + "/" + inputFileName);
		File outputFile = new File(outputDirName + "/" + inputFileName + ".actual");
		outputFile.delete();
		outputFile.createNewFile();
		TextUI.mainFileToFile(inputFile, outputFile);
		String output = FileUtils.readFileToString(outputFile, "UTF-8");
		String expected = FileUtils.readFileToString(expectedFile, "UTF-8");
		assertEquals(expected, output);
	}
	
	/*
	public static File getExpectedFile(String expectedFileName) throws IOException {
		File expectedDir = new File(expectedDirName);
		if (!expectedDir.exists()) {
			expectedDir.createNewFile();
		}
		
		File expectedFile = new File(expectedDirName + "/" + expectedFileName);
		if (!expectedFile.exists()) {
			expectedFile.createNewFile();
		}
		return expectedFile;
	}
	*/
}
