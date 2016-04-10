package parser;
//@@author A0121535R
//Parser for deleting a task

import java.util.HashSet;
import java.util.logging.Level;
import logic.commands.Command;
import logic.commands.CommandDelete;
import logic.commands.CommandDeleteRecurring;
import logic.commands.CommandInvalid;

public class DeleteParser extends GeneralParser {

	private static DeleteParser dp;
	private HashSet<String> dpHs;
	
	// Returns a Command object 
	public Command parse(String inputArgs){
		inputArgs=inputArgs.trim();
		inputArgs=inputArgs.toLowerCase();
		String[] inputTokens = inputArgs.split(Regex.REGEX_SPACE);
		return obtainDeleteCommand(inputArgs, inputTokens);
	}
	
	//check the size of the input string after the command type
	private Command obtainDeleteCommand(String inputArgs, String[] inputTokens) {
		if(inputTokens.length ==2){
			return getRecurringDelete(inputArgs, inputTokens);
		} else{
			return getNormalDelete(inputArgs);	
		}
	}
	
	//returns a normal CommandDelete if there is no recurring keyword
	private Command getNormalDelete(String inputArgs) {
		Command cmdDetails;
		try{
			int inputNum = getInputNum(inputArgs);
			boolean checkSize = checkInputArgs(inputArgs,1);
			if(inputNum<1||!checkSize){
				return cmdDetails = new CommandInvalid();
			}
			cmdDetails = new CommandDelete(inputNum);
			return cmdDetails;
		} catch(NullPointerException e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "normal delete nullpointer error", e);
			cmdDetails = new CommandInvalid();
			return cmdDetails;
		} catch(NumberFormatException e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "normal delete numberFormat error", e);
			cmdDetails = new CommandInvalid();
			return cmdDetails;
		}
	}
	
	//returns a reccuring delete if there is recurring keyword
	private Command getRecurringDelete(String inputArgs, String[] inputTokens) {
		Command cmdDetails;
		try{
			if(dpHs.contains(inputTokens[0])){
				return obtainRecurringDelete(inputArgs, inputTokens);
			} else{
				cmdDetails = new CommandInvalid();
				return cmdDetails;
			}
		}catch(NullPointerException e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "recurring delete error", e);
			cmdDetails = new CommandInvalid();
			return cmdDetails;
		}
	}

	private Command obtainRecurringDelete(String inputArgs, String[] inputTokens) {
		Command cmdDetails;
		int inputNum = getInputNum(inputTokens[1]);
		boolean checkSize = checkInputArgs(inputArgs,2);
		if(inputNum<1||!checkSize){
			cmdDetails = new CommandInvalid();
			return cmdDetails;
		} else{
			cmdDetails = new CommandDeleteRecurring(inputNum);
			return cmdDetails;
		}
	}

	private void init() {	
		dpHs = new HashSet<String>();
		dpHs.add("repeating");
		dpHs.add("routine");
		dpHs.add("recurring");
		dpHs.add("repeat");
		dpHs.add("recur");
	}

	public static DeleteParser getInstance() {
		if (dp == null){
			dp= new DeleteParser();
			dp.init();
		}
		return dp;
	}
}