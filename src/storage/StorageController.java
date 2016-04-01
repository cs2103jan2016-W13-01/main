package storage;

import java.io.IOException;

import logic.tasks.*;

/* @@author A0112184R
 * This class controls the adding, deleting, saving, loading, searching in the storage
 */
public class StorageController {
	
	private static TaskList<Task> displayList;
	private static TaskList<Task> floatList;
	private static TaskList<Deadline> deadlineList;
	private static TaskList<Session> sessionList;
	private static TaskList<RecurringTask> recurringList;
	
	public static TaskList<Task> getDisplayList() {
		return displayList;
	}
	
	public static boolean addNewTask(Task task) throws IOException {
		boolean result;
		if (task instanceof Deadline) {
			result = GrandTaskList.add((Deadline) task);
			Database.saveDeadline();
		} else if (task instanceof Session) {
			result = GrandTaskList.add((Session) task);
			Database.saveSession();
		} else if (task instanceof RecurringTask) {
			result = GrandTaskList.add((RecurringTask) task);
			Database.saveRecurring();
		} else {
			result = GrandTaskList.add(task);
			Database.saveFloat();
		}
		return result;
	}
	
	public static boolean deleteTask(Task task) throws IOException {
		boolean result;
		if (task instanceof Deadline) {
			result = GrandTaskList.delete((Deadline) task);
			Database.saveDeadline();
		} else if (task instanceof Session) {
			result = GrandTaskList.delete((Session) task);
			Database.saveSession();
		} else if (task instanceof RecurringTask) {
			result = GrandTaskList.delete((RecurringTask) task);
			Database.saveRecurring();
		} else {
			result = GrandTaskList.delete(task);
			Database.saveFloat();
		}
		return result;
	}
}
