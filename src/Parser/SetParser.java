package Parser;
/* @@author A0121535R
* Parser for setting storage directory
*/
import java.util.logging.Level;

import logic.commands.Command;
import logic.commands.CommandInvalid;
import logic.commands.CommandSetLocation;
import logic.commands.CommandType;

public class SetParser extends GeneralParser{

	public Command parse(String inputArgs) {
		try{
			CommandType cmd = CommandType.SEARCH;
			Command cmdDetails =null;
			String stringPath = getTitle(inputArgs);
			if(stringPath.equals("")||stringPath==null){
				return cmdDetails = new CommandInvalid();
			}
			//String description = getDescription(inputTokens[1]);
			cmdDetails = new CommandSetLocation(stringPath);

			return cmdDetails;
		}
		catch(Exception e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "processing error", e);
			return new CommandInvalid();
		}

	}
}
