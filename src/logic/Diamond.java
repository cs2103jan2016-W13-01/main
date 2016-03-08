package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import gui.CommandWindow;
import gui.DisplayWindow;

public class Diamond {

	private static final String MESSAGE_TASK_LIST = "Current task list:";
	private static final String MESSAGE_NO_TASK = "No task to show.";
	
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
		String message = TaskProcessor.getReturnMessage(cmd);
		DW.clearTasks();
		DW.displayTask(message);
		displayTasks();
	}

	public static void displayTasks() {
		int taskCounter = 0;
		DW.displayTask(MESSAGE_TASK_LIST);
		for (Task task: TaskProcessor.getTaskList()) {
			taskCounter++;
			String taskString = taskCounter + ". " + task.toString();
			DW.displayTask(taskString);
		}
		if (taskCounter == 0) {
			DW.displayTask(MESSAGE_NO_TASK);
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
