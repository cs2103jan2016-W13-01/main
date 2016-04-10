package parser;
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
	private static HashMap<String, String> displayHashmap;
	private static HashMap<String,CommandType> cmdTypeHm;

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
		displayHashmap = new HashMap<String, String>(); 
		cmdTypeHm = new HashMap<String,CommandType>();
		displayHmInit();
		cmdTypeHmInit();
	}

	private void cmdTypeHmInit() {
		cmdHashAdd();
		cmdHashDelete();
		cmdHashEdit();
		cmdHashSearch();
		cmdHashUndo();
		cmdHashRedo();
		cmdHashSet();
		cmdHashMark();
		cmdHashUnMark();
		cmdHashGet();
		cmdHashDisplay();
		cmdHashClear();
		cmdHashHelp();	
	}

	private void cmdHashEdit() {
		CommandParser.cmdTypeHm.put("edit",CommandType.EDIT);
		CommandParser.cmdTypeHm.put("e",CommandType.EDIT);
	}

	private void cmdHashSearch() {
		CommandParser.cmdTypeHm.put("search",CommandType.SEARCH);
		CommandParser.cmdTypeHm.put("s",CommandType.SEARCH);
	}

	private void cmdHashUndo() {
		CommandParser.cmdTypeHm.put("undo",CommandType.UNDO);
		CommandParser.cmdTypeHm.put("u",CommandType.UNDO);
	}

	private void cmdHashGet() {
		CommandParser.cmdTypeHm.put("get",CommandType.GET);
		CommandParser.cmdTypeHm.put("g",CommandType.GET);
		CommandParser.cmdTypeHm.put("dir",CommandType.GET);
		CommandParser.cmdTypeHm.put("cd",CommandType.GET);
	}

	private void cmdHashSet() {
		CommandParser.cmdTypeHm.put("set",CommandType.SET);
		CommandParser.cmdTypeHm.put("save",CommandType.SET);
	}

	private void cmdHashMark() {
		CommandParser.cmdTypeHm.put("mark",CommandType.MARK);
		CommandParser.cmdTypeHm.put("m",CommandType.MARK);
		CommandParser.cmdTypeHm.put("complete",CommandType.MARK);
		CommandParser.cmdTypeHm.put("finish",CommandType.MARK);
	}

	private void cmdHashDisplay() {
		CommandParser.cmdTypeHm.put("display",CommandType.DISPLAY);
		CommandParser.cmdTypeHm.put("dis",CommandType.DISPLAY);
	}

	private void cmdHashClear() {
		CommandParser.cmdTypeHm.put("clear",CommandType.CLEAR);
		CommandParser.cmdTypeHm.put("kill",CommandType.CLEAR);
	}

	private void cmdHashHelp() {
		CommandParser.cmdTypeHm.put("help",CommandType.HELP);
		CommandParser.cmdTypeHm.put("halp",CommandType.HELP);

	}

	private void cmdHashUnMark() {
		CommandParser.cmdTypeHm.put("unmark",CommandType.UNMARK);
		CommandParser.cmdTypeHm.put("um",CommandType.UNMARK);
	}

	private void cmdHashRedo() {
		CommandParser.cmdTypeHm.put("redo",CommandType.REDO);
		CommandParser.cmdTypeHm.put("r",CommandType.REDO);
	}

	private void cmdHashDelete() {
		CommandParser.cmdTypeHm.put("delete",CommandType.DELETE);
		CommandParser.cmdTypeHm.put("d",CommandType.DELETE);
		CommandParser.cmdTypeHm.put("remove",CommandType.DELETE);
	}

	private void cmdHashAdd() {
		CommandParser.cmdTypeHm.put("add",CommandType.ADD);
		CommandParser.cmdTypeHm.put("a",CommandType.ADD);
		CommandParser.cmdTypeHm.put("create",CommandType.ADD);
	}

	private void displayHmInit() {
		displayHashAddAll();
		displayHashAddFloat();
		displayHashAddDeadline();
		displayHashAddSession();
		displayHashAddRecurring();
		displayHashAddDone();
		displayHashAddUndone();
		displayHashAddUpcoming();
		displayHashAddPast();
		displayHashAddHelp();
	}

	private void displayHashAddHelp() {
		CommandParser.displayHashmap.put(DISPLAY_HELP, DISPLAY_HELP);
		CommandParser.displayHashmap.put("halp",DISPLAY_HELP);
	}


	private void displayHashAddPast() {
		CommandParser.displayHashmap.put(DISPLAY_PAST, DISPLAY_PAST);
	}


	private void displayHashAddUpcoming() {
		CommandParser.displayHashmap.put(DISPLAY_UPCOMING, DISPLAY_UPCOMING);
		CommandParser.displayHashmap.put("coming", DISPLAY_UPCOMING);

	}


	private void displayHashAddUndone() {
		CommandParser.displayHashmap.put(DISPLAY_UNDONE, DISPLAY_UNDONE);
		CommandParser.displayHashmap.put("uncompleted", DISPLAY_UNDONE);
		CommandParser.displayHashmap.put("unfinished", DISPLAY_UNDONE);
		CommandParser.displayHashmap.put("pending", DISPLAY_UNDONE);
		CommandParser.displayHashmap.put("incomplete", DISPLAY_UNDONE);
		CommandParser.displayHashmap.put("in progress", DISPLAY_UNDONE);
		CommandParser.displayHashmap.put("uncomplete", DISPLAY_UNDONE);
		CommandParser.displayHashmap.put("incompleted", DISPLAY_UNDONE);
	}


	private void displayHashAddDone() {
		CommandParser.displayHashmap.put(DISPLAY_DONE, DISPLAY_DONE);
		CommandParser.displayHashmap.put("completed", DISPLAY_DONE);
		CommandParser.displayHashmap.put("finished", DISPLAY_DONE);
		CommandParser.displayHashmap.put("complete", DISPLAY_DONE);
	}


	private void displayHashAddRecurring() {
		CommandParser.displayHashmap.put("repeating", DISPLAY_RECURRING);
		CommandParser.displayHashmap.put("routine", DISPLAY_RECURRING);
		CommandParser.displayHashmap.put(DISPLAY_RECURRING, DISPLAY_RECURRING);
		CommandParser.displayHashmap.put("repeat", DISPLAY_RECURRING);
		CommandParser.displayHashmap.put("recur", DISPLAY_RECURRING);
	}


	private void displayHashAddSession() {
		CommandParser.displayHashmap.put(DISPLAY_SESSION, DISPLAY_SESSION);
		CommandParser.displayHashmap.put("event",  DISPLAY_SESSION);
	}


	private void displayHashAddDeadline() {
		CommandParser.displayHashmap.put(DISPLAY_DEADLINE,DISPLAY_DEADLINE);
		CommandParser.displayHashmap.put("normal", DISPLAY_DEADLINE);
		CommandParser.displayHashmap.put("norm", DISPLAY_DEADLINE);
	}


	private void displayHashAddFloat() {
		CommandParser.displayHashmap.put(DISPLAY_FLOAT, DISPLAY_FLOAT);
		CommandParser.displayHashmap.put("undecided",DISPLAY_FLOAT );
		CommandParser.displayHashmap.put("floating", DISPLAY_FLOAT);
	}


	private void displayHashAddAll() {
		CommandParser.displayHashmap.put("", DISPLAY_ALL);
		CommandParser.displayHashmap.put(DISPLAY_ALL,DISPLAY_ALL);
	}
	
	/*
	 * It obtains the command type then calls the other parser classes to obtain the Command Object.
	*/
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
			return getInputLengthTwo(inputTokens, cmd);
		} else if(inputTokens.length==1){
			return getInputLengthOne(cmd);
		} else{
			return new CommandInvalid();
		}
	}

	private static Command getInputLengthOne(CommandType cmd) {
		try{
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
		} catch(NullPointerException e){
			return new CommandInvalid();
		}
	}

	private static Command getInputLengthTwo(String[] inputTokens, CommandType cmd) {
		try{
			switch(cmd){
				case ADD:
					AddParser ap = new AddParser();
					return ap.parse(inputTokens[1]);

				case DELETE:
					DeleteParser dp =DeleteParser.getInstance();
					return dp.parse(inputTokens[1]);

				case EDIT:
					EditParser ep = EditParser.getInstance();
					return ep.parse(inputTokens[1]);

				case MARK:
					int inputNum = getInputNum(inputTokens[1]);
					if(inputNum==-1){
						return new CommandInvalid();
					}
					return new CommandMark(inputNum);

				case UNMARK:
					inputNum = getInputNum(inputTokens[1]);
					if(inputNum==-1){
						return new CommandInvalid();
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
				
				case HELP:
					return new CommandHelp(inputTokens[1]);

				default:
					return new CommandInvalid();
			}
		} catch (NullPointerException e){
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
			returnString = displayHashmap.get(input);
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
		string = string.trim();
		try{
			CommandType cmdType = cmdTypeHm.get(string);
			return cmdType;
		} catch (NullPointerException e){
			return CommandType.INVALID;
		}
	}
}
