/* @@author A0112184R */
package logic.commands;

import java.util.function.Predicate;

import Storage.Storage;
import logic.tasks.Deadline;

/* @@author A0112184R
 * This class contains details for "search" commands
 */
public class CommandSearch implements Command {
	
	private static final String MESSAGE_SEARCH_ERROR = "Error encountered when searching for keyword. Please try again.";
	private static final String MESSAGE_NO_MATCH = "No match found.";
	private static final String MESSAGE_SEARCH_RESULT = "Search results for %1$s:";
	
	private String keyword;
	
	public CommandType getType() {
		return CommandType.SEARCH;
	}
	
	public CommandSearch(String word) {
		keyword = word;
	}
	
	public String execute() {
		
		Predicate<Deadline> searchPredicate = new Predicate<Deadline>() {
			public boolean test(Deadline task) {
				String titleString = task.getTitle().toLowerCase();
				return titleString.contains(keyword.toLowerCase());
			}
		};
		
		Storage.searchTask(searchPredicate);
		return String.format(MESSAGE_SEARCH_RESULT, keyword);
	}
	
	public String undo() {
		return null;
	}
}
