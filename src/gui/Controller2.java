package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import Logic.LogicDemo;
import Storage.src.Storage;

public class Controller2 {
	public static DisplayWindow DW;
	public static CommandWindow CW;
	
	public static void main(String args[]){
		DW = new DisplayWindow("Display");
		CW = new CommandWindow("Command");
		while (true){
		executeCommand();
		displayTasks();
		}}
	
	public static void executeCommand(){
		String cmd = CW.getCommand();
	}	
	
	public static void clearWindow(){
		DW.clearTasks();
		}
	
	public static void displayTasks(){
		clearWindow();
		File storageFile = Storage.getFileName();
	}
	
	public void getFileName(){		

	}
	
	private static void displayAllTasks(File storageFile) {	
		int linesWritten = 0;
		try {	
			BufferedReader displayBufferedReader = initBufferedReader(storageFile);
			String line = "";
			while ((line = displayBufferedReader.readLine()) != null) {
				DW.displayTask(linesWritten+ ". " +line);
				linesWritten++;
				}
			displayBufferedReader.close();
		}
		catch (IOException e) {
		}	
	}
	
	private static BufferedReader initBufferedReader(File textFile) {
		try {
			FileReader fileReader = new FileReader(textFile.getAbsoluteFile());
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			return bufferedReader;
		}
		catch (IOException e) {
		}
		return null;
	}
	
}
