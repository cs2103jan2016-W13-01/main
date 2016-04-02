package Parser;
/* @@author A0121535R
* Parser for adding a task
*/
import java.util.Date;
import java.util.logging.Level;

import logic.Priority;
import logic.commands.Command;
import logic.commands.CommandAdd;
import logic.commands.CommandInvalid;
import logic.commands.CommandType;
import logic.tasks.Deadline;
import logic.tasks.Task;

public class AddParser extends GeneralParser {
	
	public Command parse(String inputArgs){
		try{
		String title = getTitle(inputArgs);
		Date[] date = getDateArray(inputArgs);
		Priority tag = getTag(inputArgs);
		int recurring = getRecurring(inputArgs);
		Task task = createTask(title,date,tag,recurring);
		Command cmdDetails = new CommandAdd(task);
		
		//String description = getDescription(inputArgs);
		return cmdDetails;

		}
		catch(Exception e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "processing error", e);
			return new CommandInvalid();
		}
		
	}
}
