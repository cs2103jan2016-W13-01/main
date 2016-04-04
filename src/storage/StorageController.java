package storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.function.Predicate;

import org.apache.commons.lang.time.DateUtils;

import logic.tasks.*;

/* @@author A0112184R
 * This class controls the adding, deleting, saving, loading, searching in the storage
 */
public class StorageController {
	
	private static ArrayList<IndexedTaskList<Task>> taskListCollection;
	
	private static final int INDEX_DISPLAY = 0;
	private static final int INDEX_UPCOMING = 1;
	private static final int INDEX_UNDONE = 2;
	private static final int INDEX_DONE = 3;
	private static final int INDEX_ALL = 4;
	
	public static ArrayList<Task> gettaskListCollection.get(INDEX_DISPLAY)() {
		return taskListCollection.get(INDEX_DISPLAY);
	}
	
	public static void initialize() throws IOException {		
		GrandTaskList.initialize();
		Database.initialize();
		taskListCollection = new ArrayList<IndexedTaskList<Task>>();
		taskListCollection.add(INDEX_DISPLAY, new IndexedTaskList<Task>("custom"));
		taskListCollection.add(INDEX_ALL, new IndexedTaskList<Task>("all"));
		taskListCollection.add(INDEX_UPCOMING, new IndexedTaskList<Task>("upcoming"));
		loadtaskListCollection.get(INDEX_DISPLAY)();
	}
	
	public static void loadtaskListCollection.get(INDEX_DISPLAY)() {
		for (Task task: GrandTaskList.getTotalList()) {
			taskListCollection.get(INDEX_DISPLAY).add(task);
		}
	}
	
	public static Task deleteByIndex(int index) throws IOException {
		Task task = taskListCollection.get(INDEX_DISPLAY).remove(index);
		deleteTask(task);
		return task;
	}
	
	public static Task markDoneByIndex(int index) throws IOException {
		Task task = taskListCollection.get(INDEX_DISPLAY).remove(index);
		markDone(task);
		return task;
	}
	
	public static Task unmarkDoneByIndex(int index) throws IOException {
		Task task = taskListCollection.get(INDEX_DISPLAY).remove(index);
		unmarkDone(task);
		return task;
	}
	
	public static boolean addNewTask(Task task) throws IOException {
		taskListCollection.get(INDEX_DISPLAY).clear();
		boolean result = GrandTaskList.addNewTask(task);
		taskListCollection.get(INDEX_DISPLAY).add(task);
		return result;
	}
	
	public static boolean deleteTask(Task task) throws IOException {
		boolean result = GrandTaskList.deleteTask(task);
		taskListCollection.get(INDEX_DISPLAY).remove(task);
		return result;
	}
	
	public static boolean markDone(Task task) throws IOException {
		taskListCollection.get(INDEX_DISPLAY).clear();
		boolean result = GrandTaskList.markDone(task);
		taskListCollection.get(INDEX_DISPLAY).remove(task);
		return result;
	}
	
	public static boolean unmarkDone(Task task) throws IOException {
		taskListCollection.get(INDEX_DISPLAY).clear();
		boolean result = GrandTaskList.unmarkDone(task);
		taskListCollection.get(INDEX_DISPLAY).add(task);
		return result;
	}
	
	public static <T extends Task> IndexedTaskList<T> convertFromSorted(SortedTaskList<T> list, String name) {
		IndexedTaskList<T> result = new IndexedTaskList<T>(name);
		for (T task: list) {
			result.add(task);
		}
		return result;
	}
	
	public static void getAllTasks() {
		taskListCollection.get(INDEX_DISPLAY).clear();
		for (Task task: GrandTaskList.getNoRecurringList()) {
			taskListCollection.get(INDEX_DISPLAY).add(task);
		}
	}
	
	public static void displayFloatTasks() {
		taskListCollection.get(INDEX_DISPLAY).clear();
		for (Task task: GrandTaskList.getFloatList()) {
			taskListCollection.get(INDEX_DISPLAY).add(task);
		}
	}
	
	public static void displayDeadlines() {
		taskListCollection.get(INDEX_DISPLAY).clear();
		for (Task task: GrandTaskList.getDeadlineList()) {
			taskListCollection.get(INDEX_DISPLAY).add(task);
		}
	}
	
	public static void displaySessions() {
		taskListCollection.get(INDEX_DISPLAY).clear();
		for (Task task: GrandTaskList.getSessionList()) {
			taskListCollection.get(INDEX_DISPLAY).add(task);
		}
	}
	
	public static void displayRecurring() {
		taskListCollection.get(INDEX_DISPLAY).clear();
		for (Task task: GrandTaskList.getRecurringList()) {
			taskListCollection.get(INDEX_DISPLAY).add(task);
		}
	}
	
	public static void displayTasksOnDate(Calendar date) {
		for (Task task: GrandTaskList.getTasksOnDate(date)) {
			taskListCollection.get(INDEX_DISPLAY).add(task);
		}
	}
	
	public static void clearDisplayedTasks() throws IOException {
		for (Task task: taskListCollection.get(INDEX_DISPLAY)) {
			GrandTaskList.deleteTask(task);
		}
		taskListCollection.get(INDEX_DISPLAY).clear();
	}
	
	public static void clearAllTasks() throws IOException {
		GrandTaskList.clearAll();
		taskListCollection.get(INDEX_DISPLAY).clear();
	}
	
	public static void setPath(String pathName) throws IOException {
		Database.setPath(pathName);
	}
	
	public static String getPath() {
		return Database.getPath();
	}
	
	public static void searchTask(Predicate<Task> predicate) {
		for (Task task: GrandTaskList.search(predicate)) {
			taskListCollection.get(INDEX_DISPLAY).add(task);
		}
	}
	
	public static ArrayList<Task> getTimelineList() {
		ArrayList<Task> result = new ArrayList<Task>();
		for (Task task: GrandTaskList.getNoRecurringList()) {
			Calendar date = task.getMainDate();
			Calendar nextOneMonth = Calendar.getInstance();
			nextOneMonth.add(Calendar.DATE, 30);
			if (date != null
				&& DateUtils.truncatedCompareTo(date,
						nextOneMonth,
						Calendar.DATE) < 0) {
				result.add(task);
			}
		}
		return result;
	}
}
