package Parser;
//@@author A0121535R
//inital parser called by logic, that sort the input to the respective parser classes

import java.io.IOException;
import java.text.SimpleDateFormat;
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
	public static SimpleDateFormat sdf;
	public static CommandParser cmdParser;
	static FileHandler fh; 
	private static HashMap<String, String> hashmap;

	private static final String DISPLAY_ALL ="all";
	private static final String DISPLAY_FLOAT ="float";
	private static final String DISPLAY_DEADLINE="deadline";
	private static final String DISPLAY_SESSION="session";
	private static final String DISPLAY_RECURRING="recurring";
	private static final String DISPLAY_DONE="done";
	private static final String DISPLAY_UPCOMING="upcoming";
	private static final String DISPLAY_UNDONE="undone";
	private static final String DISPLAY_PAST="past";
	private static final String DISPLAY_HELP="help";

	public CommandParser() throws SecurityException, IOException {
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
			cmdParser.init();
		}
		return cmdParser;
	}

	private void init() {
		hashmap = new HashMap<String, String>(); 
		hashAddAll();
		hashAddFloat();
		hashAddDeadline();
		hashAddSession();
		hashAddRecurring();
		hashAddDone();
		hashAddUndone();
		hashAddUpcoming();
		hashAddPast();
		hashAddHelp();
	}

	private void hashAddHelp() {
		CommandParser.hashmap.put(DISPLAY_HELP, DISPLAY_HELP);
		CommandParser.hashmap.put("halp",DISPLAY_HELP);
	}


	private void hashAddPast() {
		CommandParser.hashmap.put(DISPLAY_PAST, DISPLAY_PAST);
	}


	private void hashAddUpcoming() {
		CommandParser.hashmap.put(DISPLAY_UPCOMING, DISPLAY_UPCOMING);
		CommandParser.hashmap.put("coming", DISPLAY_UPCOMING);

	}


	private void hashAddUndone() {
		CommandParser.hashmap.put(DISPLAY_UNDONE, DISPLAY_UNDONE);
		CommandParser.hashmap.put("uncompleted", DISPLAY_UNDONE);
		CommandParser.hashmap.put("unfinished", DISPLAY_UNDONE);
		CommandParser.hashmap.put("pending", DISPLAY_UNDONE);
		CommandParser.hashmap.put("incomplete", DISPLAY_UNDONE);
		CommandParser.hashmap.put("in progress", DISPLAY_UNDONE);
		CommandParser.hashmap.put("uncomplete", DISPLAY_UNDONE);
		CommandParser.hashmap.put("incompleted", DISPLAY_UNDONE);
	}


	private void hashAddDone() {
		CommandParser.hashmap.put(DISPLAY_DONE, DISPLAY_DONE);
		CommandParser.hashmap.put("completed", DISPLAY_DONE);
		CommandParser.hashmap.put("finished", DISPLAY_DONE);
		CommandParser.hashmap.put("complete", DISPLAY_DONE);
	}


	private void hashAddRecurring() {
		CommandParser.hashmap.put("repeating", DISPLAY_RECURRING);
		CommandParser.hashmap.put("routine", DISPLAY_RECURRING);
		CommandParser.hashmap.put(DISPLAY_RECURRING, DISPLAY_RECURRING);
		CommandParser.hashmap.put("repeat", DISPLAY_RECURRING);
		CommandParser.hashmap.put("recur", DISPLAY_RECURRING);
	}


	private void hashAddSession() {
		CommandParser.hashmap.put(DISPLAY_SESSION, DISPLAY_SESSION);
		CommandParser.hashmap.put("event",  DISPLAY_SESSION);
	}


	private void hashAddDeadline() {
		CommandParser.hashmap.put(DISPLAY_DEADLINE,DISPLAY_DEADLINE);
		CommandParser.hashmap.put("normal", DISPLAY_DEADLINE);
		CommandParser.hashmap.put("norm", DISPLAY_DEADLINE);
	}


	private void hashAddFloat() {
		CommandParser.hashmap.put(DISPLAY_FLOAT, DISPLAY_FLOAT);
		CommandParser.hashmap.put("undecided",DISPLAY_FLOAT );
		CommandParser.hashmap.put("floating", DISPLAY_FLOAT);
	}


	private void hashAddAll() {
		CommandParser.hashmap.put("", DISPLAY_ALL);
		CommandParser.hashmap.put(DISPLAY_ALL,DISPLAY_ALL);
	}

	//sort the keyword at the start by the commandtypes to the different type of classes
	public static Command parseInput(String input) {
		try{
			if (cmdParser == null){
				cmdParser= new CommandParser();
				cmdParser.init();
			}
		} catch( SecurityException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
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
		} else if(inputTokens.length==1){
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
		} else{
			return new CommandInvalid();
		}
	}


	private static int getInputNum(String input) {
		int num =-1;
		try{
			num = Integer.parseInt(input);
			return num;
		} catch(NumberFormatException e){
			num=-1;
			return num;
		}
	}


	private static Command getDisplayCommand(String input) {
		input=input.toLowerCase();
		input=input.trim();
		System.out.println("input at cmdParser is "+input);
		String returnString;

		try{
			returnString = CommandParser.hashmap.get(input);
		} catch(NullPointerException e){
			System.out.println("returnstring is null");
			returnString=null;
		}

		if(returnString==DISPLAY_HELP){
			return new CommandHelp();
		} else if(returnString==null){
			return new CommandInvalid();
		} else{
			CommandDisplay cmdDisplay = new CommandDisplay(returnString);
			return cmdDisplay;
		}
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
