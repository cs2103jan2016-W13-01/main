package logic.commands;

/**
 * @author Bao Linh
 * This class signifies invalid commands
 */
public class CommandInvalid implements Command {
	
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command. Please try again.";
	
	public CommandType getType() {
		return CommandType.INVALID;
	}
	
	public CommandInvalid() { }
	
	public String execute() {
		return MESSAGE_INVALID_COMMAND;
	}
	
	public String undo() {
		return null;
	}
}
