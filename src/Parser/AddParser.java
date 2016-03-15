package Parser;

import java.util.Date;
import java.util.logging.Level;

import logic.CommandDetails;
import logic.CommandType;
import logic.Task;

public class AddParser extends GeneralParser {
	
	public CommandDetails parse(String inputArgs){
		try{
		CommandType cmd = CommandType.ADD;
		String title = getTitle(inputArgs);
		Date date = getDate(inputArgs);
		//String description = getDescription(inputArgs);
		Task task = new Task(title,date);
		CommandDetails cmdDetails = new CommandDetails(cmd,task);
		
		return cmdDetails;
		}
		catch(Exception e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "processing error", e);
			return new CommandDetails(CommandType.INVALID,null);
		}
	
		
	}
}
