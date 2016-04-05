package logic.commands;

import java.util.logging.Level;

import logic.ExecutedCommands;
import logic.LogicLogger;
import logic.UndoneCommands;

/* @@author A0112184R
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
			LogicLogger.log(Level.INFO, "Undoing action");
			UndoneCommands.addCommand(lastExecutedCommand);
			return lastExecutedCommand.undo();
		} else {
			LogicLogger.log(Level.INFO, "No action to undo");
			return MESSAGE_NO_COMMAND;
		}
	}
	
	public String undo() {
		return null;
	}
}
