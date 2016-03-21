package logic;

/**
 * @author Bao Linh
 * This class contains details for "edit" commands
 */
public class CommandEdit implements Command {
	
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
