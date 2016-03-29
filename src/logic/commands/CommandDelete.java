package logic.commands;

import java.io.IOException;

import Storage.Storage;
import logic.ExecutedCommands;
import logic.Task;
/**
 * @author Bao Linh
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
			deletedTask = Storage.deleteTask(taskNumberToDelete);
			if (deletedTask != null) {
				ExecutedCommands.addCommand(this);
				return String.format(MESSAGE_TASK_DELETED, deletedTask.toString());
			} else {
				return String.format(MESSAGE_TASK_NOT_FOUND, taskNumberToDelete);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return MESSAGE_DELETE_ERROR;
		}
	}

	public String undo() {
		try {
			Storage.addNewTask(deletedTask, taskNumberToDelete);
			return String.format(MESSAGE_UNDONE, deletedTask.toString());
		} catch (IOException e) {
			return String.format(MESSAGE_UNDO_ERROR, deletedTask.toString());
		}
	}

}
