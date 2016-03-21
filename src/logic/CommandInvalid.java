package logic;

/**
 * @author Bao Linh
 * This class signifies invalid commands
 */
public class CommandInvalid implements Command {
	
	public CommandType getType() {
		return CommandType.INVALID;
	}
	
	public CommandInvalid() { }
	
	public String execute() {
		return null;
	}
	
	public String undo() {
		return null;
	}
}
