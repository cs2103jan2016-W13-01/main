package logic;

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
	
	private static final String MESSAGE_ADD_ERROR = "Error encountered when adding text. Please try again.";
	private static final String MESSAGE_DELETE_ERROR = "Error encountered when deleting task. Please try again";
	private static final String MESSAGE_DISPLAY_ERROR = "Error encountered when displaying tasks. Please try again";
	private static final String MESSAGE_CLEAR_ERROR = "Error encountered when clearing all tasks. Please try again";
	private static final String MESSAGE_SORT_ERROR = "Error encountered when sorting tasks. Please try again.";
	private static final String MESSAGE_SEARCH_ERROR = "Error encountered when searching for keyword. Please try again.";
	private static final String MESSAGE_NO_MATCH = "No match found.";
	private static final String MESSAGE_TASK_ADDED = "Task added successfully";
	private static final String MESSAGE_TASK_DELETED = "Task deleted successfully";
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command. Please try again.";
	
	private static ArrayList<String> listToDisplay;
	
	public static ArrayList<String> getListToDisplay() {
		return listToDisplay;
	}
	
	public static String executeCommand(String cmd) {
		CommandDetails cmdDetails = CommandParser.parseInput(cmd);
		CommandType cmdType = cmdDetails.getCommand();
		switch (cmdType) {
			case ADD:
				Task task = cmdDetails.getTask();
				return addTask(task);
			case DELETE:
				int taskNumber = cmdDetails.getTaskNumber();
				return deleteTask(taskNumber);
			case INVALID:
			default:
				return MESSAGE_INVALID_COMMAND;
		}
		
	}
	
	public static String addTask(Task task) {
		loadIntoDisplayList(Storage.addNewTask(task));
		return MESSAGE_TASK_ADDED;
	}
	
	public static String deleteTask(int taskNumber) {
		loadIntoDisplayList(Storage.deleteTask(taskNumber));
		return MESSAGE_TASK_DELETED;
	}
	
	public static void initialize() {
		Storage.retrieveFile();
		listToDisplay = new ArrayList<String>();
		loadIntoDisplayList(Storage.loadTaskList());
	}
	
	public static void loadIntoDisplayList(ArrayList<Task> taskList) {
		for (Task task: taskList) {
			listToDisplay.add(task.toString());
		}
	}
}
