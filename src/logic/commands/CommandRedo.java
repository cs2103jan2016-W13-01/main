package logic.commands;

import java.util.logging.Level;

import logic.LogicLogger;
import logic.UndoneCommands;

/* @@author A0112184R
 *
 */
public class CommandRedo {
	
	private static final String MESSAGE_NO_COMMAND = "No undone commands";

	public CommandType getType() {
		return CommandType.REDO;
	}
	
	public CommandRedo() {}
	
	public String execute() {
		if (!UndoneCommands.isEmpty()) {
			Command lastUndoneCommand = UndoneCommands.getLatestCommand();
			LogicLogger.log(Level.INFO, "Undoing action");
			return lastUndoneCommand.execute();
		} else {
			LogicLogger.log(Level.INFO, "No action to undo");
			return MESSAGE_NO_COMMAND;
		}
	}
	
	public String undo() {
		return null;
	}
}
