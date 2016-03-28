package logic.commands;

/**
 * @author Bao Linh
 * This class contains details for the "display" commands
 */
public class CommandDisplay {
	
	private static final String MESSAGE_ALL_TASKS = "All tasks are displayed below:";

	public CommandType getType() {
		return CommandType.DISPLAY;
	}
	
	public CommandDisplay() {
	}
	
	public String execute() {
		return MESSAGE_ALL_TASKS;
	}
	
	public String undo() {
		return null;
	}
}
