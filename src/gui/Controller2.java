package gui;

import Parser.CommandParser;

public class Controller2 {
	public static DisplayWindow DW;
	public static CommandWindow CW;
	
	public static void main(String args[]){
		DW = new DisplayWindow("Display");
		CW = new CommandWindow("Command");
		while (true){
		executeCommand();
		}}
	
	public static void executeCommand(){
		String cmd = CW.getCommand();
<<<<<<< HEAD
		Parser.parseCommand(cmd);
=======
		LogicDemo.parseCommand(cmd);
>>>>>>> 7dfdce5465ea7042de0af85484f0db2063da77ee
	}	
	
	public void clearDW(){
		DW.clearUI();
		}
	
	public void displayDW(String task){
		DW.displayUI(task);		
	}
	
}
