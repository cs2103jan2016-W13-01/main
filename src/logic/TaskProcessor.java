package logic;

import java.io.IOException;
import java.util.ArrayList;

import Parser.CommandParser;
import Storage.Storage;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
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
	private static final String MESSAGE_SEARCH_ERROR = "Error encountered when searching for keyword. Please try again.";
	private static final String MESSAGE_NO_MATCH = "No match found.";
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command. Please try again.";
	*/
	
	private static ArrayList<String> listToDisplay;
	
	private static Logger logger = Logger.getLogger("Logic logger");
	
	public static ArrayList<String> getListToDisplay() {
		return listToDisplay;
	}
	
	public static String executeCommand(String cmd) {
		
	}
	
	public static void initialize() {
		logger.log(Level.INFO, "initializing memory...");
		Storage.retrieveFile();
		listToDisplay = new ArrayList<String>();
		logger.log(Level.INFO, "loading tasks from storage...");
		ArrayList<Task> taskList = Storage.loadTaskList();
		loadIntoDisplayList(taskList);
		logger.log(Level.INFO, "memory initialized");
	}
	
	private static void loadIntoDisplayList(ArrayList<Task> taskList) {
		for (Task task: taskList) {
			assert task != null : "Some task in the task list is null";
			listToDisplay.add(task.toString());
		}
	}
}
