package logic.commands;

import logic.ExecutedCommands;
import logic.LogicLogger;
import logic.tasks.Deadline;
import logic.tasks.Task;
import storage.StorageController;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.logging.Level;

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
		LogicLogger.log(Level.INFO, "editing task: " + editedTask.toString() + " in storage");
		try {
			oldTask = StorageController.deleteByIndex(taskNumberToEdit-1);
			StorageController.addNewTask(editedTask);
			ExecutedCommands.addCommand(this);
			LogicLogger.log(Level.INFO, "edited successfully");
			return String.format(MESSAGE_EDITED, editedTask.toString());
		} catch (NoSuchElementException d) {
			LogicLogger.log(Level.WARNING, "Task not found");
			return String.format(MESSAGE_TASK_NOT_FOUND, taskNumberToEdit);
		} catch (IOException e) {
			LogicLogger.log(Level.SEVERE, "Error when deleting from storage:");
			LogicLogger.log(Level.SEVERE, e.toString());
			return MESSAGE_EDIT_ERROR;
		}
	}
	
	public String undo() {
		try {
			StorageController.deleteTask(editedTask);
			StorageController.addNewTask(oldTask);
			return String.format(MESSAGE_UNDONE, oldTask.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return String.format(MESSAGE_UNDO_ERROR, oldTask.toString());
		}
	}
}
