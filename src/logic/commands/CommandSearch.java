package logic.commands;

import java.util.function.Predicate;

import logic.tasks.Task;
import storage.StorageController;

/* @@author A0112184R
 * This class contains details for "search" commands
 */
public class CommandSearch implements Command {
	
	private static final String MESSAGE_SEARCH_RESULT = "Search results for %1$s:";
	
	private String keyword;
	
	public CommandType getType() {
		return CommandType.SEARCH;
	}
	
	public CommandSearch(String word) {
		keyword = word;
	}
	
	public String execute() {
		
		Predicate<Task> searchPredicate = new Predicate<Task>() {
			public boolean test(Task task) {
				String titleString = task.getTitle().toLowerCase();
				return titleString.contains(keyword.toLowerCase());
			}
		};
		
		StorageController.searchTask(searchPredicate);
		return String.format(MESSAGE_SEARCH_RESULT, keyword);
	}
	
	public String undo() {
		return null;
	}
}
