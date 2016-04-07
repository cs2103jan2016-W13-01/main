package Parser;
/* @@author A0121535R
 * inital parser that sort the input to the respective parser classes
 */
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.commands.Command;
import logic.commands.CommandClear;
import logic.commands.CommandDisplay;
import logic.commands.CommandGetLocation;
import logic.commands.CommandHelp;
import logic.commands.CommandInvalid;
import logic.commands.CommandMark;
import logic.commands.CommandRedo;
import logic.commands.CommandType;
import logic.commands.CommandUndo;
import logic.commands.CommandUnmark;

public class CommandParser {

	public static Logger parserLogger = Logger.getLogger(CommandParser.class.getName());
	public ArrayList<String> list;
	public static SimpleDateFormat sdf;
	HashMap hm;
	public static CommandParser cmdParser;
	FileHandler fh; 

	public CommandParser() throws SecurityException, IOException {
		list = new ArrayList<String>();
		sdf = new SimpleDateFormat("ddMMyyyy HHmm");
		sdf.setLenient(false);
		FileHandler fh = new FileHandler("./log/MyLogFile.txt");  
		parserLogger.addHandler(fh);
		System.out.println("where is my log file "+getClass().getClassLoader().getResource("logging.properties"));
		parserLogger.log(Level.INFO, "log starting");

	}


	public static CommandParser getInstance() throws SecurityException, IOException{
		if (cmdParser == null){
			cmdParser= new CommandParser();
			//cmdParser.init();
		}
		return cmdParser;
	}

	/*
	private void init() {
		hm = new HashMap(); 
		hashAddAll(hm);
		hashAddFloat(hm);
		hashAddDeadline(hm);
		hashAddSession(hm);
		hashAddRecurring(hm);
		hashAddDone(hm);
		hashAddUndone(hm);
		hashAddUpcoming(hm);
		hashAddPast(hm);
		hashAddHelp(hm);
	}


	private void hashAddHelp(HashMap hm) {
		// TODO Auto-generated method stub
		
	}


	private void hashAddPast(HashMap hm) {
		// TODO Auto-generated method stub
		
	}


	private void hashAddUpcoming(HashMap hm) {
		// TODO Auto-generated method stub
		
	}


	private void hashAddUndone(HashMap hm) {
		// TODO Auto-generated method stub
		
	}


	private void hashAddDone(HashMap hm) {
		// TODO Auto-generated method stub
		
	}


	private void hashAddRecurring(HashMap hm) {
		// TODO Auto-generated method stub
		
	}


	private void hashAddSession(HashMap hm) {
		// TODO Auto-generated method stub
		
	}


	private void hashAddDeadline(HashMap hm) {
		// TODO Auto-generated method stub
		
	}


	private void hashAddFloat(HashMap hm) {
			hm.put("float", "float");
			hm.put("undecided", "float");
			hm.put("floating", "float");
	}


	private void hashAddAll(HashMap hm) {
		hm.put("", "all");
		hm.put("all","all");
		
	}
*/

	public static Command parseInput(String input) {
		String[] inputTokens = getToken(input);
		CommandType cmd = getCmdType(inputTokens[0]);
		if(inputTokens.length==2){
			switch(cmd){
				case ADD:
					AddParser ap = new AddParser();
					return ap.parse(inputTokens[1]);

				case DELETE:
					DeleteParser dp = new DeleteParser();
					return dp.parse(inputTokens[1]);

				case EDIT:
					EditParser ep = new EditParser();
					return ep.parse(inputTokens[1]);

				case MARK:
					int inputNum = getInputNum(inputTokens[1]);
					if(inputNum==-1){
						cmd=CommandType.INVALID;
					}
					return new CommandMark(inputNum);

				case UNMARK:
					inputNum = getInputNum(inputTokens[1]);
					if(inputNum==-1){
						cmd=CommandType.INVALID;
					}
					return new CommandUnmark(inputNum);

				case SET:
					SetParser setParser = new SetParser();
					return setParser.parse(inputTokens[1]);

				case SEARCH:
					SearchParser searchP = new SearchParser();
					return searchP.parse(inputTokens[1]);

				case DISPLAY:
					return getDisplayCommand(inputTokens[1]);
					
				default:
					return new CommandInvalid();
			}
		}
		else if(inputTokens.length==1){
			switch(cmd){
				case REDO:
					return new CommandRedo();

				case UNDO:
					return new CommandUndo();

				case HELP:
					return new CommandHelp();

				case CLEAR:
					return new CommandClear();
				case GET:
					return new CommandGetLocation();

				default:
					return new CommandInvalid();
			}
		}
		else{
			return new CommandInvalid();
		}
	}


	private static int getInputNum(String input) {
		int num =-1;
		try{
			num = Integer.parseInt(input);
			return num;
		}catch(NumberFormatException e){
			num=-1;
			return num;
		}
	}


	private static Command getDisplayCommand(String input) {
		input=input.toLowerCase();
		String returnString="";
		switch(input){
			case "all":
			case "":
				returnString="all";
				break;
			case "float":
			case "undecided":
			case "floating":
				returnString="float";
				break;
			case "deadline":
			case "normal":
			case "norm":
				returnString="deadline";
				break;
			case "session":
			case "event":
				returnString="session";
				break;
			case "repeating":
			case "routine":
			case "recurring":
			case "repeat":
			case "recur":
				returnString="recurring";
				break;
			case "done":
			case "completed":
			case "finished":
				returnString="done";
				break;
			case "upcoming":
			case "coming":
				returnString="upcoming";
				break;
			case "undone":
			case "uncompleted":
			case "unfinished":
			case "pending":
			case "incomplete":
			case "in progress":
				returnString="undone";	
				break;
			case "past":
				returnString ="past";
				break;
			case "help":
			case "halp":	
				return new CommandHelp();
			default:
				returnString = input;
		}
		CommandDisplay cmdDisplay = new CommandDisplay(returnString);
		return cmdDisplay;

	}


	private static String[] getToken(String input) {
		input = input.trim();
		input=input.replaceAll(Regex.REGEX_SPACE," ");
		String[] inputTokens = input.split(" ",2); 
		return inputTokens;
	}


	private static CommandType getCmdType(String string) {
		string = string.toLowerCase();
		switch (string) {
			case "a":
			case "add":
			case "create":
				return CommandType.ADD;
			case "d":
			case "delete":
			case "remove":
				return CommandType.DELETE;
			case "u":
			case "undo":
				return CommandType.UNDO;
			case "r":
			case "redo":
				return CommandType.REDO;
			case "m":
			case "mark":
			case "complete":
			case "finished":
				return CommandType.MARK;
			case "um":
			case "unmark":
				return CommandType.UNMARK;
			case "e":
			case "edit":
				return CommandType.EDIT;
			case "s":
			case "search":
				return CommandType.SEARCH;
			case "h":
			case "help":
				return CommandType.HELP;
			case "c":
			case "clear":
			case "kill":
				return CommandType.CLEAR;
			case "set":
			case "save":
				return CommandType.SET;
			case "get":
			case "dir":
			case "cd":
				return CommandType.GET;
			case "dis":
			case "display":
				return CommandType.DISPLAY;
			default:
				return CommandType.INVALID;
		}
	}
}
