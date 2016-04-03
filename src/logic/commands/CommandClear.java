package logic.commands;

import java.io.IOException;
import java.util.ArrayList;

import logic.tasks.Task;
import storage.StorageController;

/* @@author A0112184R
 * This class contains details for "clear" commands
 */
public class CommandClear implements Command {
	
	private static final String MESSAGE_CLEARED = "All tasks removed";
	private static final String MESSAGE_UNDONE = "All tasks added back";
	private static final String MESSAGE_CLEAR_ERROR = "Error: fail to clear tasks";
	
	private ArrayList<Task> oldTaskList;
	
	public CommandType getType() {
		return CommandType.CLEAR;
	}
	
	public CommandClear() {
		oldTaskList = new ArrayList<Task>();
	}
	
	public String execute() {
		try {
			for (Task task: StorageController.getDisplayList()) {
				oldTaskList.add(task);
			}
			StorageController.clearDisplayedTasks();
			return MESSAGE_CLEARED;
		} catch (IOException e) {
			e.printStackTrace();
			return MESSAGE_CLEAR_ERROR;
		}
	}
	
	public String undo() {
		assert (oldTaskList != null) : "task list is null";
		String message = null;
		try {
			for (Task task: oldTaskList) {
				StorageController.addNewTask(task);
			}
			message = MESSAGE_UNDONE;
		} catch (IOException e) {
			message = e.toString();
		}
		return message;
	}
}
