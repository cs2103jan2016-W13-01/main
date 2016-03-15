package Parser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.CommandDetails;
import logic.CommandType;

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
<<<<<<< HEAD
	
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
	public CommandDetails parseInput(String input) {
		String[] inputTokens = getToken(input);
		CommandType cmd = getCmdType(inputTokens[0]);
		CommandDetails cmdDetails=null;
		
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
			
		/*	
		case UNDO:
			cmdDetails = new CommandDetails(cmd,task);
			break;
			
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
			cmdDetails = new CommandDetails(cmd,null);
		}
=======

	/**
	 * Requires input format add title date
	 */
	public static CommandDetails parseInput(String input) {
		input = input.trim();
		input = input.replaceAll("\\s+", " ");
		String[] inputTokens = input.split(" ");
		CommandType cmd = getCmd(inputTokens[0]);
		Task task = null;
		int inputNum;
		CommandDetails cmdDetails;

		switch (cmd) {

			case ADD:
				task = getTask(input);
				cmdDetails = new CommandDetails(cmd, task);
				break;
	
			case DELETE:
				inputNum = getInputNum(inputTokens);
				if (inputNum < 0) {
					cmd = CommandType.INVALID;
				}
				cmdDetails = new CommandDetails(cmd, task);
				cmdDetails.setInputNum(inputNum);
				break;
	
			case EDIT:
				task = getTask(input);
				inputNum = getInputNum(inputTokens);
				if (inputNum < 0) {
					cmd = CommandType.INVALID;
				}
				cmdDetails = new CommandDetails(cmd, task);
				cmdDetails.setInputNum(inputNum);
				break;
	
			case UNDO:
				cmdDetails = new CommandDetails(cmd, task);
				break;
	
			case MARK:
				inputNum = getInputNum(inputTokens);
				if (inputNum < 0) {
					cmd = CommandType.INVALID;
				}
				cmdDetails = new CommandDetails(cmd, task);
				cmdDetails.setInputNum(inputNum);
				break;
>>>>>>> a4fb3a2bda54b2d0b98ca12255f9b79d62744ba8
	
			case UNMARK:
				inputNum = getInputNum(inputTokens);
				if (inputNum < -1) {
					cmd = CommandType.INVALID;
				}
				cmdDetails = new CommandDetails(cmd, task);
				cmdDetails.setInputNum(inputNum);
				break;
	
			default:
				cmdDetails = new CommandDetails(cmd, task);

		}

		return cmdDetails;
	}

<<<<<<< HEAD
	private String[] getToken(String input) {
		input = input.trim();
		input=input.replaceAll(REGEX_SPACE," ");
		String[] inputTokens = input.split(" ",2); 
		return inputTokens;
	}



	

	private static CommandType getCmdType(String string) {
=======
	private static Task getTask(String input) {

		String[] inputTokens = input.split(" ", 2);
		String title = titleParser.getTitle(inputTokens[1]);
		Date date = dateParser.getDate(inputTokens[1]);

		Task task = new Task(title, date);
		return task;
	}

	private static int getInputNum(String[] inputTokens) {
		String inputNum = inputTokens[1];
		int num;
		try {
			num = Integer.parseInt(inputNum);
		} catch (Exception e) {
			num = -1;
		}
		System.out.println("deleted num = " + num);
		return num;
	}

	private static String getCommandDescription(String[] inputTokens) {
		String description = "";
		for (int i = 0; i < inputTokens.length; i++) {
			if (inputTokens[i].charAt(0) == '/') {
				StringBuilder sb = new StringBuilder(inputTokens[i]);
				sb = sb.deleteCharAt(0);
				description = sb.toString();
				break;
			}
		}
		return description;
	}

	private static CommandType getCmd(String string) {
>>>>>>> a4fb3a2bda54b2d0b98ca12255f9b79d62744ba8
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
<<<<<<< HEAD
		case "s":
		case "search":
			return CommandType.SEARCH;
=======

>>>>>>> a4fb3a2bda54b2d0b98ca12255f9b79d62744ba8
		default:
			return CommandType.INVALID;
		}
	}
}
