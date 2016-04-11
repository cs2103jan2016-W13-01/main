package logic.commands;

import java.io.IOException;

import logic.ExecutedCommands;
import logic.tasks.RecurringTask;
import logic.tasks.Task;
import storage.StorageController;

public class CommandDeleteRecurring implements Command {
	
	private static final String MESSAGE_NOT_RECURRING_TASK = "Sorry, please select a recurring task and try again!";
	private static final String MESSAGE_DELETED = "Recurring task deleted: %s";
	private static final String MESSAGE_DELETE_ERROR = "Sorry, an error is encountered when try to delete this task";
	private static final String MESSAGE_TASK_NOT_FOUND = "Sorry, task number %s not found in task list";
	private static final String MESSAGE_UNDONE = "Task added back to task list: %s";
	private static final String MESSAGE_UNDO_ERROR = "Sorry, failed to undo: delete %s";
	private int taskNumberToDelete;
	private RecurringTask deletedTask;
	private String oldTab;
	
	public CommandDeleteRecurring(int num){
		taskNumberToDelete = num;
	}
	
	@Override
	public CommandType getType() {
		return CommandType.DELETE_RECUR;
	}

	@Override
	public String execute() {
		try {
			Task instance = StorageController.getIndex(taskNumberToDelete-1);
			deletedTask = instance.getParent();
			if (deletedTask == null) {
				return MESSAGE_NOT_RECURRING_TASK;
			}
			oldTab = StorageController.getTabType();
			StorageController.deleteRecurringTask(deletedTask);
			ExecutedCommands.addCommand(this);
			return String.format(MESSAGE_DELETED, deletedTask.toMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return MESSAGE_DELETE_ERROR;
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			return String.format(MESSAGE_TASK_NOT_FOUND, taskNumberToDelete);
		}
	}

	@Override
	public String undo() {
		try {
			StorageController.addNewTask(deletedTask);
			StorageController.setTabType(oldTab);
			return String.format(MESSAGE_UNDONE, deletedTask.toMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return String.format(MESSAGE_UNDO_ERROR, deletedTask.toMessage());
		}
	}

}
