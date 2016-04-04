package logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import Parser.CommandParser;
import logic.commands.Command;
import logic.commands.CommandQueue;
import logic.tasks.*;
import storage.StorageController;

/* @@author A0112184R
 * Class TaskProcessor
 * This class contains methods to execute all the user commands and issue commands to other components.
 * Key methods so far:
 *     - executeCommand(Command): execute the Command object by calling Command.execute()
 *     - executeInput(String): execute the input by parsing it and call executeCommand
 *     - initialize: initialize the StorageController and all the components
 */
public class TaskProcessor {
	
	/*
	private static final String MESSAGE_DISPLAY_ERROR = "Error encountered when displaying tasks. Please try again";
	private static final String MESSAGE_CLEAR_ERROR = "Error encountered when clearing all tasks. Please try again";
	private static final String MESSAGE_SORT_ERROR = "Error encountered when sorting tasks. Please try again.";
	*/

	private static ArrayList<String> listToDisplay;
	
	public static void main(String[] args) {
		while (true) {
			getAndExecuteCommand();
		}
	}
	
	public static ArrayList<String> getListToDisplay() {
		loadIntoDisplayList(StorageController.getDisplayList());
		return listToDisplay;
	}
	
	public static void getAndExecuteCommand() {
		Command command = CommandQueue.getCommand();
		if (command != null) {
			executeCommand(command);
		}
	}
	
	public static Response executeInput(String input) {
		LogicLogger.log(Level.INFO, "Executing input: " + input);
		Command command = CommandParser.parseInput(input);
		return executeCommand(command);
	}

	public static Response executeCommand(Command command) {
		String message = command.execute();
		ArrayList<String> taskList = getListToDisplay();
		return new Response(message, taskList);
	}
	
	public static void initialize() {
		listToDisplay = new ArrayList<String>();
		ExecutedCommands.initialize();
		LogicLogger.initialize();
		try {
			LogicLogger.log(Level.INFO, "Initializing StorageController");
			StorageController.initialize();
			loadIntoDisplayList(StorageController.getDisplayList());
		} catch (IOException e) {
			LogicLogger.log(Level.SEVERE, "Error when initializing StorageController");
			e.printStackTrace();
		}
	}
	
	private static void loadIntoDisplayList(ArrayList<Task> taskList) {
		LogicLogger.log(Level.INFO, "Loading list to display from StorageController");
		listToDisplay.clear();
		for (Task task: taskList) {
			assert task != null : "Some task in the task list is null";
			listToDisplay.add(TaskUtil.toString(task));
		}
	}
	
	public static ArrayList<String[]> getTimelineList() {
		ArrayList<String[]> result = new ArrayList<String[]>();
		for (Task task: StorageController.getTimelineList()) {
			String[] pentuple = TaskUtil.toStringArray(task);
			String[] triple = new String[3];
			for (int i=0; i<3; i++) {
				triple[i] = pentuple[i+1];
			}
			result.add(triple);
		}
		return result;
	}
}
