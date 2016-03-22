package logic;

import java.io.IOException;
import java.util.ArrayList;

import Parser.CommandParser;
import Storage.Storage;
/**
 * @author Bao Linh
 * Class TaskProcessor
 * This class contains methods to execute all the user commands and issue commands to other components.
 * Methods so far:
 *     - add(task): add the task into the taskList
 *     - delete(task): delete the task from the taskList
 *     - initialize: initialize the taskList as an empty ArrayList
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
		loadIntoDisplayList(Storage.getTaskList());
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
		ExecutedCommands.addCommand(command);
		ArrayList<String> taskList = getListToDisplay();
		Response response = new Response(message, taskList);
		return response;
	}
	
	public static void initialize() {
		listToDisplay = new ArrayList<String>();
		try {
			Storage.retrieveFile();
			ArrayList<Task> taskList = Storage.loadTaskList();
			loadIntoDisplayList(taskList);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void loadIntoDisplayList(ArrayList<Task> taskList) {
		listToDisplay.clear();
		for (Task task: taskList) {
			assert task != null : "Some task in the task list is null";
			listToDisplay.add(task.toString());
		}
	}
}
