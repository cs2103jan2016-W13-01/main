package logic.commands;

import java.util.Calendar;

import logic.tasks.TaskUtil;
import storage.StorageController;

/* @@author A0112184R
 * This class contains details for the "display" commands
 */
public class CommandDisplay implements Command {
	
	private static final String MESSAGE_ALL_TASKS = "All tasks are displayed below:";
	private static final String MESSAGE_FLOAT_TASKS = "All undecided tasks are displayed below:";
	private static final String MESSAGE_DEADLINES = "All deadlines are displayed below:";
	private static final String MESSAGE_SESSIONS = "All sessions are displayed below:";
	private static final String MESSAGE_RECURRING = "All recurring tasks are displayed below:";
	private static final String MESSAGE_DISPLAY_DATE = "All tasks on %s are displayed below:";
	private static final String MESSAGE_INVALID = "Invalid criteria for display. Please try again";
	private String criteria;

	public CommandType getType() {
		return CommandType.DISPLAY;
	}
	
	public CommandDisplay(String word) {
		criteria = word;
	}
	
	public CommandDisplay() {
		criteria = "all";
	}
	
	public String execute() {
		if (criteria.equals("all")) {
			StorageController.displayAllTasks();
			return MESSAGE_ALL_TASKS;
		} else if (criteria.equals("float")) {
			StorageController.displayFloatTasks();
			return MESSAGE_FLOAT_TASKS;
		} else if (criteria.equals("deadline")) {
			StorageController.displayDeadlines();
			return MESSAGE_DEADLINES;
		} else if (criteria.equals("session")) {
			StorageController.displaySessions();
			return MESSAGE_SESSIONS;
		} else if (criteria.equals("recurring")) {
			StorageController.displayRecurring();
			return MESSAGE_RECURRING;
		} else {
			Calendar date = TaskUtil.parseDate(criteria);
			if (date != null) {
				StorageController.displayTasksOnDate(date);
				return String.format(MESSAGE_DISPLAY_DATE, criteria);
			} else {
				return MESSAGE_INVALID;
			}
		}
	}
	
	public String undo() {
		return null;
	}
}
