package Parser;
import java.util.Calendar;
/* @@author A0121535R
 * Parser for editing a task
 */
import java.util.Date;
import java.util.logging.Level;

import logic.Priority;
import logic.commands.Command;
import logic.commands.CommandEdit;
import logic.commands.CommandInvalid;
import logic.commands.CommandType;
import logic.tasks.Deadline;
import logic.tasks.Task;

public class EditParser extends GeneralParser {

	protected Command parse(String inputArgs){
		try{
			Command cmdDetails=null;
			String[] inputTokens = inputArgs.split(Regex.REGEX_SPACE,2);
			int inputNum = getInputNum(inputTokens[0]);

			Task task = getEditTask(inputArgs);
			if(task ==null){
				return new CommandInvalid();
			}
			//String description = getDescription(inputArgs);
			cmdDetails = new CommandEdit(inputNum,task);

			return cmdDetails;
		} catch(Exception e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "processing error", e);
			return new  CommandInvalid();
		}

	}

	private Task getEditTask(String inputArgs) {
		String title = getTitle(inputArgs);
		Calendar[] date = getDateArray(inputArgs);
		Priority tag = getTag(inputArgs);
		int recurring = getRecurring(inputArgs);
		Task task = createTask(title,date,tag,recurring);
		return task;
	}

}