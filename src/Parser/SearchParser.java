package Parser;

import java.util.Date;
import java.util.logging.Level;

import logic.Task;
import logic.commands.Command;
import logic.commands.CommandInvalid;
import logic.commands.CommandSearch;
import logic.commands.CommandType;

public class SearchParser extends GeneralParser {
	
	protected Command parse(String inputArgs){
		try{
		CommandType cmd = CommandType.SEARCH;
		Command cmdDetails =null;
		String title = getTitle(inputArgs);
		Date date = null;
		int inputNum = getInputNum(inputArgs);
		if(title.equals("")||title==null||inputNum==-1){
			cmd=CommandType.INVALID;
			return cmdDetails = new CommandInvalid();
		}
		//String description = getDescription(inputTokens[1]);
		Task task = new Task(title,date);
		cmdDetails = new CommandSearch(title);
		
		return cmdDetails;
		}
		catch(Exception e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "processing error", e);
			return new CommandInvalid();
		}
		
	}
}