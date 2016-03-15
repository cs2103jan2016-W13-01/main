package Parser;

import java.util.Date;
import java.util.logging.Level;

import logic.CommandDetails;
import logic.CommandType;
import logic.Task;

public class SearchParser extends GeneralParser {
	
	protected CommandDetails parse(String inputArgs){
		try{
		CommandType cmd = CommandType.SEARCH;
		CommandDetails cmdDetails =null;
		String title = getTitle(inputArgs);
		Date date = null;
		int inputNum = getInputNum(inputArgs);
		if(title.equals("")||title==null||inputNum==-1){
			cmd=CommandType.INVALID;
			return cmdDetails = new CommandDetails(cmd,null);
		}
		//String description = getDescription(inputTokens[1]);
		Task task = new Task(title,date);
		cmdDetails = new CommandDetails(cmd,task,inputNum);
		
		return cmdDetails;
		}
		catch(Exception e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "processing error", e);
			return new CommandDetails(CommandType.INVALID,null);
		}
		
	}
}