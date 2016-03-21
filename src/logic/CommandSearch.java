package logic;

/**
 * @author Bao Linh
 * This class contains details for "search" commands
 */
public class CommandSearch implements Command {
	
	private String keyword;
	
	public CommandType getType() {
		return CommandType.SEARCH;
	}
	
	public CommandSearch(String word) {
		keyword = word;
	}
	
	public String execute() {
		return null;
	}
	
	public String undo() {
		return null;
	}
}
