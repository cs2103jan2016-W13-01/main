package logic.commands;

import logic.Task;

/**
 * @author Bao Linh
 * This class contains details for "edit" commands
 */
public class CommandEdit implements Command {
	
	private int taskNumberToEdit;
	private Task editedTask;
	
	public CommandEdit(int taskNumber,Task task) {
		taskNumberToEdit = taskNumber;
		editedTask = task;
	}
	
	public CommandType getType() {
		return CommandType.EDIT;
	}
	
	public String execute() {
		return null;
	}
	
	public String undo() {
		return null;
	}
}
