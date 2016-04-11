package logic.commands;

import logic.ExecutedCommands;
import logic.LogicLogger;
import logic.tasks.Task;
import logic.tasks.TaskUtil;
import storage.StorageController;

import java.io.IOException;
import java.util.Calendar;
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
	private Task newTask;
	private Task oldTask;
	private String oldTab;
	
	public CommandEdit(int taskNumber,Task task) {
		taskNumberToEdit = taskNumber;
		newTask = task;
	}
	
	public CommandType getType() {
		return CommandType.EDIT;
	}
	
	public String execute() {
		LogicLogger.log(Level.INFO, "editing task: " + newTask.toString() + " in storage");
		try {
			oldTab = StorageController.getTabType();
			oldTask = StorageController.deleteByIndex(taskNumberToEdit-1);
			String titleString = newTask.getTitle();
			Calendar start = newTask.getStartDate();
			Calendar end = newTask.getEndDate();
			int period = newTask.getPeriod();
			
			if (titleString == null) {
				titleString = oldTask.getTitle();
			}
			if (start == null) {
				start = oldTask.getStartDate();
			}
			if (end == null) {
				end = oldTask.getEndDate();
			}
			if (period == 0) {
				period = oldTask.getPeriod();
			}
			newTask = TaskUtil.getInstance(titleString, start, end, period);
			
			StorageController.addNoSwitchTab(taskNumberToEdit-1, newTask);
			ExecutedCommands.addCommand(this);
			LogicLogger.log(Level.INFO, "edited successfully");
			return String.format(MESSAGE_EDITED, newTask.toMessage());
		} catch (IndexOutOfBoundsException d) {
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
			StorageController.setTabType(oldTab);
			StorageController.deleteTask(newTask);
			StorageController.addNoSwitchTab(taskNumberToEdit-1, oldTask);
			return String.format(MESSAGE_UNDONE, oldTask.toMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return String.format(MESSAGE_UNDO_ERROR, oldTask.toMessage());
		}
	}
}
