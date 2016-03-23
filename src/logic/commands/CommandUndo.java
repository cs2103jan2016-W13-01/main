package logic.commands;

import logic.ExecutedCommands;

/**
 * @author Bao Linh
 * This class contains details for "undo" commands
 */
public class CommandUndo implements Command {
	
	private static final String MESSAGE_COMMAND_UNDONE = "Command undone successfully";
	private static final String MESSAGE_NO_COMMAND = "No command to undo";

	public CommandType getType() {
		return CommandType.UNDO;
	}
	
	public CommandUndo() {}
	
	public String execute() {
		if (!ExecutedCommands.isEmpty()) {
			Command lastExecutedCommand = ExecutedCommands.getLatestCommand();
			lastExecutedCommand.undo();
			return MESSAGE_COMMAND_UNDONE;
		} else {
			return MESSAGE_NO_COMMAND;
		}
	}
	
	public String undo() {
		return null;
	}
}
