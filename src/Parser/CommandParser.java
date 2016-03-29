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
import logic.commands.CommandType;
import logic.commands.CommandUndo;

public class CommandParser {

	private static final String REGEX_SPACE = "\\s+";

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
		Command cmdDetails=null;

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

				/*	
			case MARK:
				inputNum = getInputNum(inputTokens);
				if(inputNum==-1){
					cmd=CommandType.INVALID;
				}
				cmdDetails= new CommandDetails(cmd,task,inputNum);
				break;

			case UNMARK:
				inputNum = getInputNum(inputTokens);
				if(inputNum==-1){
					cmd=CommandType.INVALID;
				}
				cmdDetails= new CommandDetails(cmd,task,inputNum);
				break;
				 */
			case SET:
				SetParser setParser = new SetParser();
				return cmdDetails = setParser.parse(inputTokens[1]);
				
			case SEARCH:
				SearchParser searchP = new SearchParser();
				return cmdDetails = searchP.parse(inputTokens[1]);
	
			default:
				return cmdDetails = new CommandInvalid();
			}
		}
		else if(inputTokens.length==1){
			switch(cmd){			
			case UNDO:
				return cmdDetails = new CommandUndo();

			case HELP:
				return cmdDetails =  new CommandHelp();
	
			case CLEAR:
				return cmdDetails = new CommandClear();

			case DISPLAY:
				return cmdDetails = new CommandDisplay();
	
			default:
				return cmdDetails = new CommandInvalid();
			}
		}
		else{
			return cmdDetails = new CommandInvalid();
		}
	}


	private static String[] getToken(String input) {
		input = input.trim();
		input=input.replaceAll(REGEX_SPACE," ");
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
