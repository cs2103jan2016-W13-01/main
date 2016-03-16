package logic;

import java.io.IOException;

import Storage.Storage;
/**
 * @author Bao Linh
 * This class encapsulates the "delete" commands from the user.
 */
public class CommandDelete implements Command {
	
	private static final String MESSAGE_DELETE_ERROR = "Error encountered when deleting task";
	private static final String MESSAGE_TASK_DELETED = "Task deleted successfully";
	private static final String MESSAGE_UNDONE = "Action undone: delete %1$s";
	private static final String MESSAGE_UNDO_ERROR = "Failed to undo action: %1$s";
	
	private int taskNumberToDelete;
	private Task deletedTask;
	
	public String execute() {
		try {
			deletedTask = Storage.deleteTask(taskNumberToDelete);
			return MESSAGE_TASK_DELETED;
		} catch (IOException e) {
			return MESSAGE_DELETE_ERROR;
		}
	}

	public String undo() {

		return null;
	}

}
