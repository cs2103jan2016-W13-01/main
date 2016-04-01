package storage;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import logic.tasks.*;

/* @@author A0112184R
 * This class controls the adding, deleting, saving, loading, searching in the storage
 */
public class StorageController {
	
	private static TaskList<Task> displayList;
	/*
	private static TaskList<Task> floatList;
	private static TaskList<Deadline> deadlineList;
	private static TaskList<Session> sessionList;
	private static TaskList<RecurringTask> recurringList;
	*/
	public static TaskList<Task> getDisplayList() {
		return displayList;
	}
	
	public static void initialize() throws IOException {
		GrandTaskList.initialize();
		Database.initialize();
	}
	
	public static Task deleteByIndex(int index) throws IOException, NoSuchElementException {
		int counter;
		Iterator<Task> it = displayList.iterator();
		Task task = it.next();
		for (counter = 0; counter < index; counter++) {
			task = it.next();
		}
		displayList.delete(task);
		deleteTask(task);
		return task;
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
		displayList.delete(task);
		return result;
	}
	
	public static boolean displayAllTasks() {
		return false;
	}
	
	public static void setPath(String pathName) throws IOException {
		Database.setPath(pathName);
	}
	
	public static String getPath() {
		return Database.getPath();
	}
	
	public static void searchTask(Predicate<Task> predicate) {
		displayList = GrandTaskList.search(predicate);
	}
}
