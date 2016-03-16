package Parser;

import java.util.Date;
import java.util.logging.Level;

import logic.Command;
import logic.CommandType;
import logic.Task;

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
			return cmdDetails = new Command(cmd,null);
		}
		//String description = getDescription(inputTokens[1]);
		Task task = new Task(title,date);
		cmdDetails = new Command(cmd,task,inputNum);
		
		return cmdDetails;
		}
		catch(Exception e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "processing error", e);
			return new Command(CommandType.INVALID,null);
		}
		
	}
}