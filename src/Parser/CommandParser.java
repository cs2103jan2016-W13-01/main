package Parser;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import main.Logic;

public class CommandParser{
	public enum command {
		ADD,DELETE,DISPLAY,UNDO,EDIT,MARK,UNMARK,ERROR
	}
	
	public CommandParser cmdParser;
	public ArrayList<String> list;
	public SimpleDateFormat sdf;
	public SimpleDateFormat sdf1;
	public void init(){
		cmdParser = new CommandParser();
		list = new ArrayList<String>();
		sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		sdf1 = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		sdf.setLenient(false);
		sdf1.setLenient(false);
	}
	


	//for now assume the user input the full required string
	//format e.g: add "cs2103 user guide v0.0" "event"
	//            "14/02/2016" , A temp user guide for v0.0"
	// "cmd" "title" "type" "date" "description" where date is in DD/MM/YYYY
	public boolean parseInput(String input){
		String[] inputTokens = input.split(",");
		boolean checker = checkInput(inputTokens);
		if(!checker){
			System.out.println("Invalid Command"); //printout from here for now.
			return false;
		}
		else{
			return true;
		}
	}
	
	//format e.g <cmd>,<title>,<date>,/<desc> ------ note: , and / ------ 
	/*
	 * <cmd>=add or a
	 * <title> = submit progress report
	 * <date> = 09/03/2016 23:59
	 * <desc> = anyrandomdescription
	 */
	
	public boolean checkInput(String[] inputTokens) {
		
		command cmd = getCmd(inputTokens[0]);
		switch(cmd){
		case ERROR:
			return false;
		/*case DISPLAY:
			Logic.displayTasks();
			return true;
		case DISPLAY:
			Logic.unDo();
			return true;
		*/case ADD:
			ParsedCommand pcAdd = getRestOfCommand(inputTokens);
			//Logic.addTask(pcAdd);
			return true;
		case DELETE:
			ParsedCommand pcDelete = getRestOfCommand(inputTokens);
			//Logic.deleteTask(pcDelete);
			return true;
		default:
			return false;
		}
		
	}


	private ParsedCommand getRestOfCommand(String[] inputTokens) {
		trim(inputTokens);
		String title = getCommandTitle(inputTokens);
		String description = getCommandDescription(inputTokens);
		Date date = getCommandDate(inputTokens);
		String taskType = getCommandTaskType(inputTokens);
		ParsedCommand pc = new ParsedCommand(title,taskType,date,description);	
		return pc;
	}

// removes extra spaces
	private void trim(String[] inputTokens) {
		for(int i=0;i<inputTokens.length;i++){
			inputTokens[i]=inputTokens[i].trim();				
		}	
	}


	private String getCommandTaskType(String[] inputTokens) {
		// TODO Auto-generated method stub
		return null;
	}


	public Date getCommandDate(String[] inputTokens) {
		Date date=null;
		for(int i=0;i<inputTokens.length;i++){
			try{
				date = sdf.parse(inputTokens[i]);			
			} catch (Exception e) {
				continue;
			}
			break;
		}
		return date;
	}


	private String getCommandDescription(String[] inputTokens) {
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


	private String getCommandTitle(String[] inputTokens) {
		String title="";
		title=inputTokens[1];
		return title;
	}


	private command getCmd(String string) {
		string=string.toLowerCase();
		switch(string){
		case "a": 
		case "add":
			return command.ADD;
		case "d": 
		case "delete":
			return command.DELETE;
		case "dis": 
		case "display":
			return command.DISPLAY;
		case "u": 
		case "undo":
			return command.UNDO;
		case "m": 
		case "mark":
			return command.MARK;
		case "um": 
		case "unmark":
			return command.UNMARK;
		default:
			return command.ERROR;
		}
	}

/*
	public boolean checkDate(String string) {
	
		try{
			Date date = sdf.parse(string);
			System.out.println(date);
		
		} catch (Exception e) {
			System.out.println("Invalid date");
			return false;
		}
		return true;
	}
*/
}
