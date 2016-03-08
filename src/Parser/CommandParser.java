package Parser;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import logic.CommandDetails;
import logic.CommandType;
import logic.Task;

public class CommandParser{

	public ArrayList<String> list;
	public static SimpleDateFormat sdf;
	
	public CommandParser(){
		list = new ArrayList<String>();
		sdf = new SimpleDateFormat("ddMMyyyy HHmm");
		sdf.setLenient(false);
	}
	


	/**for now assume the user input the full required string
	 *format e.g: add "cs2103 user guide v0.0" "event"
	 *            "14/02/2016" , A temp user guide for v0.0"
	 * "cmd" "title" "type" "date" "description" where date is in DD/MM/YYYY
	 */
	public static CommandDetails parseInput(String input){
		String[] inputTokens = input.split(",");
		CommandDetails cmdDetails = getCommand(inputTokens);
		return cmdDetails;
	}
	
	//format e.g <cmd>,<title>,<date>,/<desc> ------ note: , and / ------ 
	/**
	 * <cmd> = add or a
	 * <title> = submit progress report
	 * <date> = 09/03/2016 23:59
	 * <desc> = any random description
	 */
	private static CommandDetails getCommand(String[] inputTokens) {
		CommandType cmd = getCmd(inputTokens[0]);
		trim(inputTokens);
		String title = getCommandTitle(inputTokens);
		String description = getCommandDescription(inputTokens);
		Date date = getCommandDate(inputTokens);
		String taskType = getCommandTaskType(inputTokens);
		Task pc = new Task(title,description,date);	
		CommandDetails cd = new CommandDetails(cmd,pc);
		return cd;
	}

// removes extra spaces
	private static void trim(String[] inputTokens) {
		for(int i=0;i<inputTokens.length;i++){
			inputTokens[i]=inputTokens[i].trim();				
		}	
	}


	private static String getCommandTaskType(String[] inputTokens) {
		// TODO Auto-generated method stub
		return null;
	}


	public static Date getCommandDate(String[] inputTokens) {
		Date date = new Date();
		String temp;
		for(int i=0;i<inputTokens.length;i++){
			try{
				temp= inputTokens[i].replaceAll("[-+.^:/,]","");
				date = sdf.parse(temp);			
			} catch (Exception e) {
				continue;
			}
			break;
		}
		return date;
	}


	private static String getCommandDescription(String[] inputTokens) {
		String description="";
		for(int i=0;i<inputTokens.length;i++){
			if(inputTokens[i].charAt(0)=='/'){
				StringBuilder sb = new StringBuilder(inputTokens[i]); 
				sb=sb.deleteCharAt(0);
				description = sb.toString();
				break;
			}
		}
		return description;
	}


	private static String getCommandTitle(String[] inputTokens) {
		String title="";
		title=inputTokens[1];
		return title;
	}


	private static CommandType getCmd(String string) {
		string=string.toLowerCase();
		switch(string){
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
			default:
				return CommandType.INVALID;
		}
	}
}
