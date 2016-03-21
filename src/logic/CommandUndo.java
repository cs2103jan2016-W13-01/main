package logic;

/**
 * @author Bao Linh
 * This class contains details for "undo" commands
 */
public class CommandUndo implements Command {
	
	public CommandType getType() {
		return CommandType.UNDO;
	}
	
	public CommandUndo() {}
	
	public String execute() {
		return null;
	}
	
	public String undo() {
		return null;
	}
}
