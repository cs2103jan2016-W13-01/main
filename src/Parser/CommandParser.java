package Parser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.commands.Command;
import logic.commands.CommandInvalid;
import logic.commands.CommandType;
import logic.commands.CommandUndo;

public class CommandParser {
	
	private static final String REGEX_SPACE = "\\s";
	
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
		
		switch(cmd){
		
		case ADD:
			AddParser ap = new AddParser();
			cmdDetails = ap.parse(inputTokens[1]);
			break;
			
			
		case DELETE:
			DeleteParser dp = new DeleteParser();
			cmdDetails = dp.parse(inputTokens[1]);
			break;
			
			
		case EDIT:
			EditParser ep = new EditParser();
			cmdDetails = ep.parse(inputTokens[1]);
			break;
			
			
		case UNDO:
			cmdDetails = new CommandUndo();
			break;
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
		case SEARCH:
			SearchParser sp = new SearchParser();
			cmdDetails = sp.parse(inputTokens[1]);
			break;
		default:
			cmd=CommandType.INVALID;
			cmdDetails = new CommandInvalid();
		}
		return cmdDetails;
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
		default:
			return CommandType.INVALID;
		}
	}
}
