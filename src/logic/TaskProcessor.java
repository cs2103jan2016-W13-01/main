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
	
	private static ArrayList<Task> taskList;
	
	public static ArrayList<Task> getTaskList() {
		return taskList;
	}
	
	public static String getReturnMessage(String cmd) {
		CommandDetails cmdDetails = CommandParser.parseInput(cmd);
		CommandType cmdType = cmdDetails.getCommand();
		Task task = cmdDetails.getTask();
		switch (cmdType) {
			case ADD:
				return add(task);
			case DELETE:
				return delete(task.getId());
			case INVALID:
			default:
				return MESSAGE_INVALID_COMMAND;
		}
	}
	
	public static void showToUser() {
		
	}
	
	public static String add(Task task) {
		taskList.add(task);
		if (Storage.addNewTask(task.toString())) {
			return MESSAGE_TASK_ADDED;
		} else {
			return MESSAGE_ADD_ERROR;
		}
	}
	
	public static String delete(String taskId) {
		int taskNumberToDelete = 0;
		for (Task task : taskList) {
			if (taskId.equals(task.getId())) {
				taskList.remove(task);
				break;
			}
		}
		if (Storage.deleteTask(taskNumberToDelete)) {
			return MESSAGE_TASK_DELETED;
		} else {
			return MESSAGE_DELETE_ERROR;
		}
	}
	
	public static void initialize() {
		taskList = new ArrayList<Task>();
	}
}
