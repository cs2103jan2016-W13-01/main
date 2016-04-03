package storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import logic.tasks.*;

/* @@author A0112184R
 * This class controls the adding, deleting, saving, loading, searching in the storage
 */
public class StorageController {
	
	private static ArrayList<Task> displayList;
	/*
	private static TaskList<Task> floatList;
	private static TaskList<Deadline> deadlineList;
	private static TaskList<Session> sessionList;
	private static TaskList<RecurringTask> recurringList;
	*/
	public static ArrayList<Task> getDisplayList() {
		return displayList;
	}
	
	public static void initialize() throws IOException {
		displayList = new ArrayList<Task>();
		GrandTaskList.initialize();
		Database.initialize();
		loadDisplayList();
	}
	
	public static void loadDisplayList() {
		for (Task task: GrandTaskList.getTotalList()) {
			displayList.add(task);
		}
	}
	
	public static Task deleteByIndex(int index) throws IOException {
		Task task = displayList.remove(index);
		deleteTask(task);
		return task;
	}
	
	public static Task markDoneByIndex(int index) throws IOException {
		Task task = displayList.remove(index);
		markDone(task);
		return task;
	}
	
	public static Task unmarkDoneByIndex(int index) throws IOException {
		Task task = displayList.remove(index);
		unmarkDone(task);
		return task;
	}
	
	public static boolean addNewTask(Task task) throws IOException {
		displayList.clear();
		boolean result = GrandTaskList.addNewTask(task);
		displayList.add(task);
		return result;
	}
	
	public static boolean deleteTask(Task task) throws IOException {
		boolean result = GrandTaskList.deleteTask(task);
		displayList.remove(task);
		return result;
	}
	
	public static boolean markDone(Task task) throws IOException {
		displayList.clear();
		boolean result = GrandTaskList.markDone(task);
		displayList.remove(task);
		return result;
	}
	
	public static boolean unmarkDone(Task task) throws IOException {
		displayList.clear();
		boolean result = GrandTaskList.unmarkDone(task);
		displayList.add(task);
		return result;
	}
	
	public static void displayAllTasks() {
		displayList.clear();
		for (Task task: GrandTaskList.getNoRecurringList()) {
			displayList.add(task);
		}
	}
	
	public static void displayTasksOnDate(Calendar date) {
		for (Task task: GrandTaskList.getTasksOnDate(date)) {
			displayList.add(task);
		}
	}
	
	public static void clearDisplayedTasks() throws IOException {
		for (Task task: displayList) {
			GrandTaskList.deleteTask(task);
		}
		displayList.clear();
	}
	
	public static void setPath(String pathName) throws IOException {
		Database.setPath(pathName);
	}
	
	public static String getPath() {
		return Database.getPath();
	}
	
	public static void searchTask(Predicate<Task> predicate) {
		for (Task task: GrandTaskList.search(predicate)) {
			displayList.add(task);
		}
	}
}
