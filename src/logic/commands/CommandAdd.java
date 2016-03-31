/* @@author A0112184R */
package logic.commands;

import java.io.IOException;
import java.util.logging.Level;

import logic.ExecutedCommands;
import logic.LogicLogger;
import logic.tasks.Deadline;
import storage.Storage;

/* @@author A0112184R
 * Class CommandAdd: This class encapsulates the "add" commands from the user.
 */
public class CommandAdd implements Command {
	
	private static final String MESSAGE_TASK_ADDED = "Task added successfully";
	private static final String MESSAGE_ADD_ERROR = "Error encountered when adding task. Please try again.";
	private static final String MESSAGE_UNDONE = "Action undone: add %1$s";
	private static final String MESSAGE_UNDO_ERROR = "Failed to undo action: add %1$s";
	
	private final Deadline task;
	
	public CommandAdd(Deadline task) {
		assert task != null: "Attempt to create a null task";
		this.task = task;
	}
	
	public Deadline getTask() {
		return task;
	}
	
	public CommandType getType() {
		return CommandType.ADD;
	}

	public String execute() {
		LogicLogger.log(Level.INFO, "adding task: " + task.toString() + " to storage");
		try {
			Storage.addNewTask(task);
			ExecutedCommands.addCommand(this);
			LogicLogger.log(Level.INFO, "added successfully");
			return MESSAGE_TASK_ADDED;
		} catch (IOException e) {
			LogicLogger.log(Level.SEVERE, "Error when adding to storage:");
			LogicLogger.log(Level.SEVERE, e.toString());
			return MESSAGE_ADD_ERROR;
		}
	}

	public String undo() {
		LogicLogger.log(Level.INFO, "deleting task: " + task.toString() + " from storage");
		try {
			Storage.deleteTask(Storage.getTaskList().size() - 1);
			LogicLogger.log(Level.INFO, "undone successfully");
			return String.format(MESSAGE_UNDONE, task.toString());
		} catch (IOException e) {
			LogicLogger.log(Level.SEVERE, "Error when deleting from storage:");
			LogicLogger.log(Level.SEVERE, e.toString());
			return String.format(MESSAGE_UNDO_ERROR, task.toString());
		}
	}
	
}
