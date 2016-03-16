package Parser;

import java.util.Date;

import logic.CommandDetails;

public abstract class GeneralParser {
	
	private static final String REGEX_SPACE = "\\s";

	protected abstract CommandDetails parse(String inputArgs);
	
	
	protected String getTitle(String input){
		String title = titleParser.getTitle(input);
		return title;
	}
	
	protected Date getDate(String input){
		Date date = DateParser.getDate(input);
		return date;
	}

	protected int getInputNum(String inputArgs) {
		int num = InputNumParser.getInputNum(inputArgs);
		return num;
	}
	
	protected boolean checkInputArgs(String inputArgs, int size){
		String[] inputToken = inputArgs.split(REGEX_SPACE);
		if(inputToken.length<size){
			return false;
		}
		return true;
	}
	
	/*
	private static String getDescription(String[] inputTokens) {
		String description = "";
		for (int i = 0; i < inputTokens.length; i++) {
			if (inputTokens[i].charAt(0) == '/') {
				StringBuilder sb = new StringBuilder(inputTokens[i]);
				sb = sb.deleteCharAt(0);
				description = sb.toString();
				break;
			}
		}
		return description;
	}
*/
}
