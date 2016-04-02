package Parser;
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

	private Deadline getEditTask(String inputArgs) {
		
		int titleIndex;
		Deadline task;
		String temp = inputArgs.toLowerCase();
		int dateIndex = temp.indexOf("d:");
		if(dateIndex==-1){
			date=null;
			titleIndex = temp.indexOf("t:");
			if(titleIndex==-1){
				return null;
			}
			titleIndex+=2;
			title = getTitle(inputArgs.substring(titleIndex).trim());
			task = new Deadline(title,date);
		}
		else{
		date = getDate(inputArgs.substring(dateIndex));

		titleIndex = temp.indexOf("t:");
		if(titleIndex==-1){
			return null;
		}
		titleIndex+=2;
		title = getTitle(inputArgs.substring(titleIndex,dateIndex).trim());
		
		task = new Deadline(title,date);
		System.out.println(title);
		System.out.println(date);
		}
		return task;
	}

}