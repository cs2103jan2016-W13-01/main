package logic;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import Storage.Storage;
import gui.CommandWindow;

public class Diamond extends CommandWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String MESSAGE_TASK_LIST = "Current task list:";
	private static final String MESSAGE_NO_TASK = "No task to show.";
	
	public static Diamond diamond;
	
	public Diamond() {
		super("Diamond");
	}

	public static void main(String args[]) {
		diamond = new Diamond();
		Storage.retrieveFile();
		TaskProcessor.initialize();
	}

	public static String executeCommand() {
		String cmd = diamond.getCommand();
		String message = TaskProcessor.getReturnMessage(cmd);
		return message;
	}

	public void actionPerformed(ActionEvent e) {
		diamond.clear();
		diamond.printMessage(executeCommand());
		diamond.printMessage(MESSAGE_TASK_LIST);
		diamond.displayTasks();
	}
	
	public void displayTasks() {
		int taskCounter = 0;
		for (Task task: TaskProcessor.getTaskList()) {
			taskCounter++;
			String taskString = taskCounter + ". " + task.toString();
			diamond.printMessage(taskString);
		}
		if (taskCounter == 0) {
			diamond.printMessage(MESSAGE_NO_TASK);
		}
	}

	public void getFileName() {

	}

	public static BufferedReader initBufferedReader(File textFile) {
		try {
			FileReader fileReader = new FileReader(textFile.getAbsoluteFile());
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			return bufferedReader;
		} catch (IOException e) {
		}
		return null;
	}
}
