package logic.commands;

/**
 * @author Bao Linh
 * This class contains details for "search" commands
 */
public class CommandSearch implements Command {
	
	private static final String MESSAGE_SEARCH_ERROR = "Error encountered when searching for keyword. Please try again.";
	private static final String MESSAGE_NO_MATCH = "No match found.";
	
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
