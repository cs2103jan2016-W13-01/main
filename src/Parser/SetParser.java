package Parser;
//@@author A0121535R
//Parser for returning storage directory string

import java.util.logging.Level;
import logic.commands.Command;
import logic.commands.CommandInvalid;
import logic.commands.CommandSetLocation;

public class SetParser extends GeneralParser{

	public Command parse(String inputArgs) {
		try{
			Command cmdDetails =null;
			String stringPath = inputArgs;
			if(stringPath.equals("")||stringPath==null){
				return cmdDetails = new CommandInvalid();
			}
			cmdDetails = new CommandSetLocation(stringPath);
			return cmdDetails;
		} catch(NullPointerException e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "processing error", e);
			return new CommandInvalid();
		}

	}
}
