package logic;

import java.io.IOException;

import Storage.Storage;
/**
 * @author Bao Linh
 * This class encapsulates the "delete" commands from the user.
 */
public class CommandDelete implements Command {
	
	private static final String MESSAGE_DELETE_ERROR = "Error encountered when deleting task";
	private static final String MESSAGE_TASK_NOT_FOUND = "Task not found in task list";
	private static final String MESSAGE_TASK_DELETED = "Task deleted successfully";
	private static final String MESSAGE_UNDONE = "Action undone: delete %1$s";
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
				return MESSAGE_TASK_DELETED;
			} else {
				return MESSAGE_TASK_NOT_FOUND;
			}
		} catch (IOException e) {
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
