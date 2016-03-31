/* @@author A0112184R */
package logic.commands;

import java.io.IOException;
import java.util.ArrayList;

import logic.tasks.Deadline;
import storage.Storage;

/* @@author A0112184R
 * This class contains details for "clear" commands
 */
public class CommandClear implements Command {
	
	private static final String MESSAGE_CLEARED = "All tasks removed";
	private static final String MESSAGE_UNDONE = "All tasks added back";
	
	private ArrayList<Deadline> oldTaskList;
	
	public CommandType getType() {
		return CommandType.CLEAR;
	}
	
	public CommandClear() {
		
	}
	
	public String execute() {
		oldTaskList = new ArrayList<Deadline>();
		for (Deadline task: Storage.getTaskList()) {
			oldTaskList.add(task.clone());
		}
		Storage.clearAllTasks();
		return MESSAGE_CLEARED;
	}
	
	public String undo() {
		assert (oldTaskList != null) : "task list is null";
		String message = null;
		try {
			for (Deadline task: oldTaskList) {
				Storage.addNewTask(task);
			}
			message = MESSAGE_UNDONE;
		} catch (IOException e) {
			message = e.toString();
		}
		return message;
	}
}
