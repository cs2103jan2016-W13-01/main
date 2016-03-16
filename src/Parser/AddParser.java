package Parser;

import java.util.Date;
import java.util.logging.Level;

import logic.Command;
import logic.CommandType;
import logic.Task;

public class AddParser extends GeneralParser {
	
	public Command parse(String inputArgs){
		try{
		CommandType cmd = CommandType.ADD;
		String title = getTitle(inputArgs);
		Date date = getDate(inputArgs);
		//String description = getDescription(inputArgs);
		Task task = new Task(title,date);
		Command cmdDetails = new Command(cmd,task);
		
		return cmdDetails;
		}
		catch(Exception e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "processing error", e);
			return new Command(CommandType.INVALID,null);
		}
	
		
	}
}
