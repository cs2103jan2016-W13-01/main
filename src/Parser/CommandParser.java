package Parser;
/* @@author A0121535R
 * inital parser that sort the input to the respective parser classes
 */
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.commands.Command;
import logic.commands.CommandClear;
import logic.commands.CommandDisplay;
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
		}
		return cmdParser;
	}

	/**for add
	 * Requires input format <add> <title> <date> 
	 * for e.g add meet at john's house at next wednesday
	 * 
	 * for delete
	 * Requires input format <delete> <num>
	 * for e.g delete 2
	 * 
	 * for edit
	 * Requires input format <edit> <num> <title>/<date>
	 * for e.g edit 3 meet at jack's place 
	 * or edit 3 at next tuesday
	 * should check the value of TASK to see if the respective title and date fields 
	 * are null or not.
	 * 
	 * for mark
	 * Requires input format <mark> <num>
	 * for e.g mark 1
	 * 
	 * for unmark
	 * Requires input format <unmark> <num>
	 * for e.g unmark 1
	 * 
	 * for undo
	 * inputformat <undo>
	 */
	public static Command parseInput(String input) {
		String[] inputTokens = getToken(input);
		CommandType cmd = getCmdType(inputTokens[0]);
		Command cmdDetails;
		if(inputTokens.length==2){
			switch(cmd){

				case ADD:
					AddParser ap = new AddParser();
					return cmdDetails = ap.parse(inputTokens[1]);

				case DELETE:
					DeleteParser dp = new DeleteParser();
					return cmdDetails = dp.parse(inputTokens[1]);

				case EDIT:
					EditParser ep = new EditParser();
					return cmdDetails = ep.parse(inputTokens[1]);

				case MARK:
					int inputNum = getInputNum(inputTokens[1]);
					if(inputNum==-1){
						cmd=CommandType.INVALID;
					}
					return cmdDetails= new CommandMark(inputNum);

				case UNMARK:
					inputNum = getInputNum(inputTokens[1]);
					if(inputNum==-1){
						cmd=CommandType.INVALID;
					}
					return cmdDetails= new CommandUnmark(inputNum);

				case SET:
					SetParser setParser = new SetParser();
					return cmdDetails = setParser.parse(inputTokens[1]);

				case SEARCH:
					SearchParser searchP = new SearchParser();
					return cmdDetails = searchP.parse(inputTokens[1]);

				case DISPLAY:
					return cmdDetails = getDisplayCommand(inputTokens[1]);
	

				default:
					return cmdDetails = new CommandInvalid();
			}
		}
		else if(inputTokens.length==1){
			switch(cmd){
				case REDO:
					return cmdDetails = new CommandRedo();

				case UNDO:
					return cmdDetails = new CommandUndo();

				case HELP:
					return cmdDetails =  new CommandHelp();

				case CLEAR:
					return cmdDetails = new CommandClear();

				default:
					return cmdDetails = new CommandInvalid();
			}
		}
		else{
			return cmdDetails = new CommandInvalid();
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
				returnString="repeat";
				break;
			case"done":
			case "completed":
			case "finished":
				returnString="done";
				break;
			case "undone":
			case "uncompleted":
			case "unfinished":
			case "pending":
				returnString="undone";	
				break;
			case "past":
				returnString ="past";
				break;
			default:
				return new CommandInvalid();
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
				return CommandType.ADD;
			case "d":
			case "delete":
				return CommandType.DELETE;
			case "u":
			case "undo":
				return CommandType.UNDO;
			case "r":
			case "redo":
				return CommandType.REDO;
			case "m":
			case "mark":
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
				return CommandType.CLEAR;
			case "set":
				return CommandType.SET;
			case "dis":
			case "display":
				return CommandType.DISPLAY;
			default:
				return CommandType.INVALID;
		}
	}
}
