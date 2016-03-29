package logic.commands;

import logic.ExecutedCommands;
import logic.Task;

import java.io.IOException;

import Storage.Storage;

/* @@author A0112184R
 * This class contains details for "edit" commands
 */
public class CommandEdit implements Command {
	
	private static final String MESSAGE_EDITED = "Task edited: %1$s";
	private static final String MESSAGE_TASK_NOT_FOUND = "Task %1$s not found in task list";
	private static final String MESSAGE_EDIT_ERROR = "Error encountered when editing task";
	private static final String MESSAGE_UNDONE = "Task reverted: %1$s";
	private static final String MESSAGE_UNDO_ERROR = "Error when undoing action: edit %1$s";
	
	private int taskNumberToEdit;
	private Task editedTask;
	private Task oldTask;
	
	public CommandEdit(int taskNumber,Task task) {
		taskNumberToEdit = taskNumber;
		editedTask = task;
	}
	
	public CommandType getType() {
		return CommandType.EDIT;
	}
	
	public String execute() {
		try {
			oldTask = Storage.deleteTask(taskNumberToEdit);
			if (oldTask != null) {
				Storage.addNewTask(editedTask, taskNumberToEdit);
				ExecutedCommands.addCommand(this);
				return String.format(MESSAGE_EDITED, editedTask.toString());
			} else {
				return String.format(MESSAGE_TASK_NOT_FOUND, taskNumberToEdit);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return MESSAGE_EDIT_ERROR;
		}
	}
	
	public String undo() {
		try {
			assert (Storage.deleteTask(taskNumberToEdit) != null): "Cannot find edited task";
			Storage.addNewTask(oldTask, taskNumberToEdit);
			return String.format(MESSAGE_UNDONE, oldTask.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return String.format(MESSAGE_UNDO_ERROR, oldTask.toString());
		}
	}
}
