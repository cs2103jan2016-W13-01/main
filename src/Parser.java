import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Parser {
	
	public Parser parser;
	public ArrayList<String> list;
	public void init(){
		parser = new Parser();
		list = new ArrayList<String>();
		
		initList();
	}
	
	
	private  void initList() {
		list.add("add");
		list.add("delete");
		list.add("edit");
	}


	//for now assume the user input the full required string
	//format e.g: add "cs2103 user guide v0.0" "event"
	//            "14/02/2016" , A temp user guide for v0.0"
	// "cmd" "title" "type" "date" "description" where date is in DD/MM/YYYY
	public void parseCommand(String input){
		if(!checkInput(input)){
			System.out.println("Invalid Command");
		}
		String[] command = input.split(" ");
		switch(command[0]){
		case "add":
			Logic.addTask(input);
		case "delete":
			Logic.deleteTask(input);
		case "sort":
			Logic.sortTasks();
		case "search":
			Logic.search(input);
		}
			

	}
	public boolean checkInput(String input) {
		String[] inputTokens = input.split(" ");
		if(!checkFirstWord(inputTokens[0])){
			return false;
		}
		if(!checkDate(inputTokens[2])){
			return false;
		}
		if(!checkInputLength(inputTokens)){
			return false;
		}
		return true;
	}


	public boolean checkInputLength(String[] inputTokens) {
		if(inputTokens.length!=5){
			return false;
		}
		return true;
	}


	public boolean checkDate(String string) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try{
			sdf.setLenient(false);
			Date date = sdf.parse(string);
			System.out.println(date);
		
		} catch (Exception e) {
			System.out.println("Invalid date");
			return false;
		}
		return true;
	}


	public boolean checkFirstWord(String string) {
		if(!list.contains(string)){
			return false;
		}
		return true;
	}
}
