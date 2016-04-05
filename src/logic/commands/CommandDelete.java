package logic.commands;

import java.io.IOException;
import java.util.logging.Level;

import logic.ExecutedCommands;
import logic.LogicLogger;
import logic.tasks.Task;
import storage.StorageController;
/* @@author A0112184R
 * This class encapsulates the "delete" commands from the user.
 */
public class CommandDelete implements Command {
	
	private static final String MESSAGE_DELETE_ERROR = "Error encountered when deleting task";
	private static final String MESSAGE_TASK_NOT_FOUND = "Task not found in task list: %1$s";
	private static final String MESSAGE_TASK_DELETED = "Task deleted: %1$s";
	private static final String MESSAGE_UNDONE = "Task added back to task list: %1$s";
	private static final String MESSAGE_UNDO_ERROR = "Failed to undo action: delete %1$s";
	
	private int taskNumberToDelete;
	private Task deletedTask;
	
	public CommandDelete(int taskNumber) {
		taskNumberToDelete = taskNumber;
		deletedTask = null;
	}
	
	public CommandType getType() {
		return CommandType.DELETE;
	}
	
	public String execute() {
		try {
			deletedTask = StorageController.deleteByIndex(taskNumberToDelete-1);
			LogicLogger.log(Level.INFO, "deleting task: " + deletedTask.toString() + " from storage");
			ExecutedCommands.addCommand(this);
			LogicLogger.log(Level.INFO, "deleted successfully");
			return String.format(MESSAGE_TASK_DELETED, deletedTask.toString());
		} catch (IndexOutOfBoundsException d) {
			LogicLogger.log(Level.WARNING, "Task not found");
			return String.format(MESSAGE_TASK_NOT_FOUND, taskNumberToDelete);
		} catch (IOException e) {
			LogicLogger.log(Level.SEVERE, "Error when deleting from storage:");
			LogicLogger.log(Level.SEVERE, e.toString());
			return MESSAGE_DELETE_ERROR;
		}
	}

	public String undo() {
		LogicLogger.log(Level.INFO, "adding task back: " + deletedTask.toString() + " to storage");
		try {
			StorageController.addNewTask(deletedTask);
			StorageController.setTabType("incomplete");
			LogicLogger.log(Level.INFO, "undone successfully");
			return String.format(MESSAGE_UNDONE, deletedTask.toString());
		} catch (IOException e) {
			LogicLogger.log(Level.SEVERE, "Error when adding from storage:");
			LogicLogger.log(Level.SEVERE, e.toString());
			return String.format(MESSAGE_UNDO_ERROR, deletedTask.toString());
		}
	}

}
