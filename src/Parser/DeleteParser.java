package Parser;

import java.util.Date;
import java.util.logging.Level;

import logic.Command;
import logic.CommandType;
import logic.Task;

public class DeleteParser extends GeneralParser {
	
	public Command parse(String inputArgs){
		try{
		CommandType cmd = CommandType.DELETE;
		Command cmdDetails =null;
		String title = null;
		Date date = null;
		int inputNum = getInputNum(inputArgs);
		boolean checkSize = checkInputArgs(inputArgs,1);
		if(inputNum==-1||!checkSize){
			cmd=CommandType.INVALID;
			return cmdDetails = new Command(cmd,null);
		}
		//String description = getDescription(inputTokens[1]);
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