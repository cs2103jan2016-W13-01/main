package Parser;

import java.util.Date;
import java.util.logging.Level;

import logic.Command;
import logic.CommandType;
import logic.Task;

public class EditParser extends GeneralParser {
	
	protected Command parse(String inputArgs){
		try{
		Command cmdDetails=null;
		CommandType cmd = CommandType.EDIT;
		int inputNum = getInputNum(inputArgs);
		boolean checkSize = checkInputArgs(inputArgs,2);
		if(inputNum==-1||!checkSize){
			cmd=CommandType.INVALID;
			return cmdDetails = new Command(cmd,null);
		}
		
		String title = getTitle(inputArgs);
		Date date = getDate(inputArgs);
		//String description = getDescription(inputArgs);
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