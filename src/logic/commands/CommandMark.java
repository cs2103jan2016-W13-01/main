package logic.commands;

import java.io.IOException;

import logic.ExecutedCommands;
import logic.tasks.Task;
import storage.StorageController;

/* @@author A0112184R
 * This class contains details for the mark as done commands
 */
public class CommandMark implements Command {
	
	private static final String MESSAGE_TASK_MARKED = "Task completed: %s";
	private static final String MESSAGE_MARK_ERROR = "Error: failed to mark task as done";
	private static final String MESSAGE_TASK_DONE = "Task is already done";
	private static final String MESSAGE_TASK_UNMARKED = "Task is unmarked: %s";
	private static final String MESSAGE_UNMARK_ERROR = "Error: failed to unmark task";
<<<<<<< HEAD
	private static final String MESSAGE_TASK_NOT_FOUND = "Task not found: %s";
=======
	private static final String MESSAGE_TASK_NOT_FOUND = "Task number %s not found in task list";
>>>>>>> 9a9e865c0749c398f03055730beb5bde91c3517a
	private int taskNumber;
	private Task task;
	
	public CommandMark(int inputNum) {
		taskNumber = inputNum;
	}
	
	public CommandType getType() {
		return CommandType.MARK;
	}
	
	public String execute() {
		try {
			task = StorageController.getIndex(taskNumber-1);
			if (task.isDone()) {
				return MESSAGE_TASK_DONE;
			}
			task.markDone();
			StorageController.markDoneByIndex(taskNumber-1);
			ExecutedCommands.addCommand(this);
			return String.format(MESSAGE_TASK_MARKED, task.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return MESSAGE_MARK_ERROR;
<<<<<<< HEAD
		} catch (IndexOutOfBoundsException d) {
=======
		} catch (IndexOutOfBoundsException e) {
>>>>>>> 9a9e865c0749c398f03055730beb5bde91c3517a
			return String.format(MESSAGE_TASK_NOT_FOUND, taskNumber);
		}
	}
	
	public String undo() {
		try {
			assert (task != null): "Task is null";
			assert (task.isDone()): "Task is somehow not done";
			StorageController.unmarkDone(task);
			StorageController.setTabType("incomplete");
			task.unmark();
			return String.format(MESSAGE_TASK_UNMARKED, task.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return MESSAGE_UNMARK_ERROR;
		}
	}
}
