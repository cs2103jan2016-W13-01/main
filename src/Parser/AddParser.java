package Parser;
/* @@author A0121535R
* Parser for adding a task
*/
import java.util.Date;
import java.util.logging.Level;

import logic.commands.Command;
import logic.commands.CommandAdd;
import logic.commands.CommandInvalid;
import logic.commands.CommandType;
import logic.tasks.Task;

public class AddParser extends GeneralParser {
	
	public Command parse(String inputArgs){
		try{
		String title = getTitle(inputArgs);
		Date date = getDate(inputArgs);
		//String description = getDescription(inputArgs);
		Task task = new Task(title,date);
		Command cmdDetails = new CommandAdd(task);
		
		return cmdDetails;
		}
		catch(Exception e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "processing error", e);
			return new CommandInvalid();
		}
	
		
	}
}
