<<<<<<< HEAD:src/gui/Controller2.java
package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Controller2 {
	public static DisplayWindow DW;
	public static CommandWindow CW;

	public static void main(String args[]) {
		DW = new DisplayWindow("Display");
		CW = new CommandWindow("Command");
		while (true) {
			executeCommand();
			displayTasks();
		}
	}

	public static void executeCommand() {
		String cmd = CW.getCommand();
	}

	public static void clearWindow() {
		DW.clearTasks();
	}

	public static void displayTasks() {
		clearWindow();
	}

	public void getFileName() {

	}

	public static void displayAllTasks(File storageFile) {
		int linesWritten = 0;
		try {
			BufferedReader displayBufferedReader = initBufferedReader(storageFile);
			String line = "";
			while ((line = displayBufferedReader.readLine()) != null) {
				DW.displayTask(linesWritten + ". " + line);
				linesWritten++;
			}
			displayBufferedReader.close();
		} catch (IOException e) {
		}
	}

	private static BufferedReader initBufferedReader(File textFile) {
		try {
			FileReader fileReader = new FileReader(textFile.getAbsoluteFile());
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			return bufferedReader;
		} catch (IOException e) {
		}
		return null;
	}
}
=======
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
>>>>>>> 5856fd2b89485607ac1cfd0c0b6ce938a8f2dd2c:src/Controller.java
