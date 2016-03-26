package logic.commands;

import java.io.IOException;

import Storage.Storage;
import logic.ExecutedCommands;
import logic.Task;

/**
 * @author Bao Linh
 * Class CommandAdd: This class encapsulates the "add" commands from the user.
 */
public class CommandAdd implements Command {
	
	private static final String MESSAGE_TASK_ADDED = "Task added successfully";
	private static final String MESSAGE_ADD_ERROR = "Error encountered when adding task. Please try again.";
	private static final String MESSAGE_UNDONE = "Action undone: add %1$s";
	private static final String MESSAGE_UNDO_ERROR = "Failed to undo action: %1$s";
	
	private final Task task;
	
	public CommandAdd(Task task) {
		assert task != null: "Attempt to create a null task";
		this.task = task;
	}
	
	public Task getTask() {
		return task;
	}
	
	public CommandType getType() {
		return CommandType.ADD;
	}

	public String execute() {
		try {
			Storage.addNewTask(task);
			ExecutedCommands.addCommand(this);
			return MESSAGE_TASK_ADDED;
		} catch (IOException e) {
			e.printStackTrace();
			return MESSAGE_ADD_ERROR;
		}
	}

	public String undo() {
		try {
			Storage.deleteTask(task);
			return String.format(MESSAGE_UNDONE, task.toString());
		} catch (IOException e) {
			return String.format(MESSAGE_UNDO_ERROR, e);
		} catch (Error e) {
			return String.format(MESSAGE_UNDO_ERROR, e);
		}
	}
	
}
