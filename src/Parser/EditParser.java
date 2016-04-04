package Parser;
import java.util.Calendar;
/* @@author A0121535R
 * Parser for editing a task
 */
import java.util.Date;
import java.util.logging.Level;

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

		int titleIndex;
		Task task;
		Calendar[] cal;
		String temp = inputArgs.toLowerCase();
		int dateIndex = temp.indexOf("d:");
		if(dateIndex==-1){
			cal = new Calendar[0];
			titleIndex = temp.indexOf("t:");
			if(titleIndex==-1){
				return null;
			}
			titleIndex+=2;
			String title = getTitle(inputArgs.substring(titleIndex).trim());
			task = new Task(title,cal);
		}
		else{
			String dateString = inputArgs.substring(dateIndex+=2);
			cal = getDateArray(dateString);
			titleIndex = temp.indexOf("t:");
			if(titleIndex==-1){
				task = new Task(null,cal);
			}
			titleIndex+=2;
			String title = getTitle(inputArgs.substring(titleIndex,dateIndex).trim());

			task = new Task(title,cal);
			System.out.println(title);
		}
		return task;
	}

}