package Parser;
/* @@author A0121535R
* Parser for deleting a task
*/

import java.util.logging.Level;
import logic.commands.Command;
import logic.commands.CommandDelete;
import logic.commands.CommandInvalid;


public class DeleteParser extends GeneralParser {
	
	public Command parse(String inputArgs){
		try{
		Command cmdDetails =null;
		int inputNum = getInputNum(inputArgs);
		boolean checkSize = checkInputArgs(inputArgs,1);
		if(inputNum<1||!checkSize){
			return cmdDetails = new CommandInvalid();
		}
		cmdDetails = new CommandDelete(inputNum);
		return cmdDetails;
		}
		catch(Exception e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "processing error", e);
			return new  CommandInvalid();
		}
		
	}
}