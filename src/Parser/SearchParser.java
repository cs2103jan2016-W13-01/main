package Parser;
/* @@author A0121535R
* Parser for search command
*/
import java.util.Date;
import java.util.logging.Level;

import logic.Tasks.Task;
import logic.commands.Command;
import logic.commands.CommandInvalid;
import logic.commands.CommandSearch;
import logic.commands.CommandType;

public class SearchParser extends GeneralParser {
	
	protected Command parse(String inputArgs){
		try{
		Command cmdDetails =null;
		String searchKey = getTitle(inputArgs);
		
		if(searchKey.equals("")||searchKey==null){
			return cmdDetails = new CommandInvalid();
		}
		//String description = getDescription(inputTokens[1]);
		cmdDetails = new CommandSearch(searchKey);
		
		return cmdDetails;
		}
		catch(Exception e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "processing error", e);
			return new CommandInvalid();
		}
		
	}
}