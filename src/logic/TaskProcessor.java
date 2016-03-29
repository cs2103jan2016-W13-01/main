package logic;

import java.io.IOException;
import java.util.ArrayList;

import Parser.CommandParser;
import Storage.Storage;
import logic.commands.Command;
import logic.commands.CommandQueue;

/* @@author A0112184R
 * Class TaskProcessor
 * This class contains methods to execute all the user commands and issue commands to other components.
 * Key methods so far:
 *     - executeCommand(Command): execute the Command object by calling Command.execute()
 *     - executeInput(String): execute the input by parsing it and call executeCommand
 *     - initialize: initialize the Storage and all the components
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
		loadIntoDisplayList(Storage.getTaskList(), Storage.getIndexList());
		return listToDisplay;
	}
	
	public static void getAndExecuteCommand() {
		Command command = CommandQueue.getCommand();
		if (command != null) {
			executeCommand(command);
		}
	}
	
	public static Response executeInput(String input) {
		Command command = CommandParser.parseInput(input);
		return executeCommand(command);
	}

	public static Response executeCommand(Command command) {
		String message = command.execute();
		ArrayList<String> taskList = getListToDisplay();
		Response response = new Response(message, taskList);
		return response;
	}
	
	public static void initialize() {
		listToDisplay = new ArrayList<String>();
		try {
			Storage.retrieveFile();
			ArrayList<Task> taskList = Storage.loadTaskList();
			loadIntoDisplayList(taskList, Storage.getIndexList());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void loadIntoDisplayList(ArrayList<Task> taskList, ArrayList<Integer> indexList) {
		listToDisplay.clear();
		for (int i: indexList) {
			Task task = taskList.get(i);
			assert task != null : "Some task in the task list is null";
			listToDisplay.add(task.toString());
		}
	}
}
