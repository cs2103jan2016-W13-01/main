package parser;
//@@author A0121535R
//class for getting an input number

import java.util.logging.Level;

public class InputNumParser {

	private static final String REGEX_SPACE = "\\s";

	public static int getInputNum(String inputArgs) {
		String[] inputTokens = inputArgs.split(REGEX_SPACE);
		String inputNum=inputTokens[0];
		int num;
		try{
			num=Integer.parseInt(inputNum);
		} catch(NumberFormatException e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "inputNumParser no inputnum", e);
			num=0;
		}
		if(num<1){
			num=0;
		}
		return num;
	}
}
