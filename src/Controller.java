package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.logic.Logic;


public class Controller {
	public static DisplayWindow DW;
	
	public static void main(String args[]){
		DW = new DisplayWindow("Display");
    	while (true){
		executeCommand();
		}}

	public static void executeCommand(){
		String cmd = DW.getCommand();
/*		Logic.executeCmd(cmd);*/
	}	
	
	public static void displayTask(String task){
		DW.displayTask(task);
	}

	public static void clearTextArea(){
		DW.clearWindow();	
	}
	}

/**	public static void displayTasks(){
		clearTextArea();
		File storageFile = Storage.retrieveFile();
		displayFromStorage(storage);
	}
	
	public static void displayFromStorage(File storageFile) {	
		int linesWritten = 0;
		try {	
			BufferedReader displayBufferedReader = initBufferedReader(storageFile);
			String line = "";
			while ((line = displayBufferedReader.readLine()) != null) {
				DW.displayTask(linesWritten+ ". " +line + "\n");
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
	}}**/
