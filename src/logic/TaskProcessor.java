package logic;

import java.util.ArrayList;

import Parser.CommandParser;
import Storage.Storage;

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
	
	private static final String MESSAGE_ADD_ERROR = "Error encountered when adding task. Please try again.";
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
	
	private static Logger logger = Logger.getLogger("Logic Logger");
	
	public static ArrayList<String> getListToDisplay() {
		return listToDisplay;
	}
	
	public static String executeCommand(String cmd) {
		logger.log(Level.INFO, "begin parsing input: " + cmd);
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
	
	private static String addTask(Task task) {
		assert task == null : "task is null";
		logger.log(Level.INFO, "begin adding task: " + task.toString());
		try {
			loadIntoDisplayList(Storage.addNewTask(task));
			logger.log(Level.INFO, "task added successfully: " + task.toString());
			return MESSAGE_TASK_ADDED;
		} catch (Exception e) {
			logger.log(Level.WARNING, "failed to add task" + task.toString());
			return MESSAGE_ADD_ERROR;
		}
	}
	
	private static String deleteTask(int taskNumber) {
		logger.log(Level.INFO, "begin deleting task at " + taskNumber);
		try {
			loadIntoDisplayList(Storage.deleteTask(taskNumber));
			logger.log(Level.INFO, "task deleted successfully: ");
			return MESSAGE_TASK_DELETED;
		} catch (Exception e) {
			logger.log(Level.WARNING, "failed to delete task at " + taskNumber);
			return MESSAGE_DELETE_ERROR;
		}
	}
	
	public static void initialize() {
		logger.log(Level.INFO, "initializing memory...");
		Storage.retrieveFile();
		listToDisplay = new ArrayList<String>();
		logger.log(Level.INFO, "loading tasks from storage...");
		try {
			ArrayList<Task> taskList = Storage.loadTaskList();
			loadIntoDisplayList(taskList);
			logger.log(Level.INFO, "memory initialized");
		} catch (Exception e) {
			System.out.println("Error caught");
			logger.log(Level.WARNING, "Task list cannot be loaded from storage");
		}
	}
	
	private static void loadIntoDisplayList(ArrayList<Task> taskList) {
		for (Task task: taskList) {
			assert task == null : "Some task in the task list is null";
			listToDisplay.add(task.toString());
		}
	}
}
