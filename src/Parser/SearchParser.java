package Parser;
//@@author A0121535R
//Parser for search command

import java.util.logging.Level;
import logic.commands.Command;
import logic.commands.CommandInvalid;
import logic.commands.CommandSearch;

public class SearchParser extends GeneralParser {

	protected Command parse(String inputArgs){
		try{
			Command cmdDetails =null;
			String searchKey = inputArgs;
			if(searchKey.equals("")||searchKey==null){
				return cmdDetails = new CommandInvalid();
			}
			cmdDetails = new CommandSearch(searchKey);
			return cmdDetails;
		} catch(NullPointerException e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "processing error", e);
			return new CommandInvalid();
		}

	}
}