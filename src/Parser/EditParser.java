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

public class EditParser extends GeneralParser {
	
	protected Command parse(String inputArgs){
		try{
		Command cmdDetails=null;
		int inputNum = getInputNum(inputArgs);
		boolean checkSize = checkInputArgs(inputArgs,2);
		if(inputNum==-1||!checkSize){
			return cmdDetails = new  CommandInvalid();
		}
		Deadline task = getEditTask(inputArgs);
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
		String title;
		Date date;
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