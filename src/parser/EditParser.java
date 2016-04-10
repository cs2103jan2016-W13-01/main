package parser;
//@@author A0121535R
//Parser for editing a task

import java.util.Calendar;
import java.util.HashSet;
import java.util.logging.Level;
import logic.commands.Command;
import logic.commands.CommandEdit;
import logic.commands.CommandInvalid;
import logic.commands.CommandEditRecurring;
import logic.tasks.Task;

public class EditParser extends GeneralParser {

	private static EditParser ep;
	private HashSet<String> epHs;

	//Returns a command object
	protected Command parse(String inputArgs){
		try{
			inputArgs=inputArgs.trim();
			inputArgs=inputArgs.toLowerCase();

			String[] inputTokens = inputArgs.split(Regex.REGEX_SPACE,2);

			if(epHs.contains(inputTokens[0])){
				return getRecurringEdit(inputTokens);
			} else{
				return getNormalEdit(inputTokens);
			}
		} catch(NullPointerException e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "edit processing error", e);
			return new  CommandInvalid();
		}

	}

	//returns CommandRecurringEdit
	private Command getRecurringEdit(String[] inputTokens) {
		Command cmdDetails;
		try{
			String[] recurringInputTokens = inputTokens[1].split(Regex.REGEX_SPACE,2);
			int inputNum = getInputNum(recurringInputTokens[0]);
			Task task = getEditTask(recurringInputTokens[1]);
			if(task ==null){
				return new CommandInvalid();
			}
			cmdDetails= new CommandEditRecurring(inputNum,task);
			return cmdDetails;
		} catch(NullPointerException e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "edit null pointer error", e);
			return new  CommandInvalid();
		} catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "edit array index out of bound error", e);
			return new  CommandInvalid();
		}
	}

	//returns CommandEdit
	private Command getNormalEdit(String[] inputTokens) {
		Command cmdDetails;
		try{
			int inputNum = getInputNum(inputTokens[0]);
			Task task = getEditTask(inputTokens[1]);
			if(task ==null){
				return new CommandInvalid();
			}
			cmdDetails = new CommandEdit(inputNum,task);
			return cmdDetails;
		} catch(NullPointerException e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "edit processing error", e);
			return new  CommandInvalid();
		}
	}

	private Task getEditTask(String inputArgs) {
		String notTitleToken = inputArgs;
		int[] startEndArray = new int[2];
		String title=null;
		boolean check = checkAbsoluteTitle(inputArgs,startEndArray);
		if(check == true){
			title=inputArgs.substring(startEndArray[0]+1,startEndArray[1]);
			notTitleToken = inputArgs.substring(startEndArray[1]+1);
		}
		Task task = consolidateEditFields(inputArgs, notTitleToken, title);
		return task;
	}

	//creates an Command object from the given fields
	private Task consolidateEditFields(String inputArgs, String notTitleToken, String title) {
		Calendar[] date = getDateArray(notTitleToken);
		int recurring = getRecurring(inputArgs);
		Task task = createTask(title,date,recurring);
		return task;
	}

	//check if there is "<title>" as well as obtain the index array if true
	private static boolean checkAbsoluteTitle(String inputArgs, int[] array) {
		int absIndexStart = inputArgs.indexOf("\""); 
		if(absIndexStart>=0){
			int absIndexEnd = inputArgs.indexOf("\"",absIndexStart+1);
			if(absIndexEnd >absIndexStart && absIndexEnd>=0){
				array[0]=absIndexStart;
				array[1]=absIndexEnd;
				return true;
			}
		}
		return false;
	}


	private void init() {	
		epHs = new HashSet<String>();
		epHs.add("repeating");
		epHs.add("routine");
		epHs.add("recurring");
		epHs.add("repeat");
		epHs.add("recur");
	}

	public static EditParser getInstance() {
		if (ep == null){
			ep= new EditParser();
			ep.init();
		}
		return ep;
	}

}