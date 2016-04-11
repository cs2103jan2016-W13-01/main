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
	
	private static ArrayList<Task> displayList;
	private static String tabType;
	
	public static ArrayList<Task> getDisplayList() {
		return displayList;
	}
	
	public static String getTabType() {
		return tabType;
	}
	
	public static void setTabType(String newTab) {
		if (newTab.equals("all")) {
			displayAllTasks();
			tabType = newTab;
		} else if (newTab.equals("incomplete")) {
			displayUndoneList();
			tabType = newTab;
		} else if (newTab.equals("completed")) {
			displayDoneList();
			tabType = newTab;
		} else if (newTab.equals("upcoming")) {
			displayUpcomingList();
			tabType = newTab;
		}
	}
	
	// initializer
	public static void initialize() throws IOException {
		displayList = new ArrayList<Task>();
		Database.initialize();
		GrandTaskList.initialize();
		loadDisplayList();
		setTabType("incomplete");
	}
	
	public static void loadDisplayList() {
		for (Task task: GrandTaskList.getUndoneList()) {
			displayList.add(task);
		}
	}
	
	public static Task getIndex(int index) {
		return displayList.get(index);
	}
	
	// operations on the task list
	// delete
	public static Task deleteByIndex(int index) throws IOException {
		Task task = displayList.get(index);
		deleteTask(task);
		return task;
	}
	
	public static boolean deleteTask(Task task) throws IOException {
		boolean result = GrandTaskList.deleteTask(task);
		displayList.remove(task);
		return result;
	}
	
	public static RecurringTask deleteRecurringTask(RecurringTask recurTask) throws IOException {
		GrandTaskList.deleteTask(recurTask);
		ArrayList<Task> listToDelete = new ArrayList<Task>();
		for (Task instance: displayList) {
			if (instance.getParent() == recurTask && !instance.isDone()) {
					listToDelete.add(instance);
			}
		}
		for (Task instance: listToDelete) {
			displayList.remove(instance);
		}
		return recurTask;
	}
	
	// add
	public static boolean addNewTask(Task task) throws IOException {
		boolean result = GrandTaskList.addNewTask(task);
		return result;
	}
	
	public static boolean addNoSwitchTab(int index, Task task) throws IOException {
		boolean result = GrandTaskList.addNewTask(task);
		displayList.add(index, task);
		return result;
	}
	
	// mark and unmark
	public static Task markDoneByIndex(int index) throws IOException {
		Task task = displayList.get(index);
		markDone(task);
		return task;
	}

	
	public static boolean markDone(Task task) throws IOException {
		boolean result = GrandTaskList.markDone(task);
		displayList.remove(task);
		return result;
	}
	
	public static boolean markDoneRecurring(RecurringTask recurTask) throws IOException {
		boolean result = GrandTaskList.markDone(recurTask);
		ArrayList<Task> markedList = new ArrayList<Task>();
		for (Task instance: displayList) {
			if (instance.getParent() == recurTask && !instance.isDone()) {
				markedList.add(instance);
			}
		}
		for (Task instance: markedList) {
			displayList.remove(instance);
		}
		return result;
	}
	
	public static Task unmarkDoneByIndex(int index) throws IOException {
		Task task = displayList.get(index);
		unmarkDone(task);
		return task;
	}
	
	public static boolean unmarkDone(Task task) throws IOException {
		boolean result = GrandTaskList.unmarkDone(task);
		return result;
	}
	
	public static boolean unmarkDoneRecurring(RecurringTask recurTask) throws IOException {
		boolean result = GrandTaskList.unmarkDone(recurTask);
		for (Task instance: displayList) {
			if (instance.getParent() == recurTask && !recurTask.isDoneInstance(instance)) {
				unmarkDone(instance);
			}
		}
		return result;
	}
	
	// methods to get different sub lists
	public static void displayAllTasks() {
		displayList = GrandTaskList.getTotalList().toArrayList();
	}
	
	public static void displayFloatTasks() {
		Predicate<Task> pred = new Predicate<Task>() {
			public boolean test(Task task) {
				return (task.getStartDate() == null && task.getEndDate() == null);
			}
		};
		searchTask(pred);
	}
	
	public static void displayDeadlines() {
		Predicate<Task> pred = new Predicate<Task>() {
			public boolean test(Task task) {
				return (task.getStartDate() == null && task.getEndDate() != null)
						|| (task.getStartDate() != null && task.getEndDate() == null);
			}
		};
		searchTask(pred);
	}
	
	public static void displaySessions() {
		Predicate<Task> pred = new Predicate<Task>() {
			public boolean test(Task task) {
				return (task.getStartDate() != null && task.getEndDate() != null);
			}
		};
		searchTask(pred);
	}
	
	public static void displayRecurring() {
		displayList.clear();
		displayList = GrandTaskList.getRecurringInstanceList().toArrayList();
	}
	
	public static void displayTasksOnDate(Calendar date) {
		displayList.clear();
		displayList = GrandTaskList.getTasksOnDate(date).toArrayList();
	}
	
	public static void displayUndoneList() {
		displayList.clear();
		displayList = GrandTaskList.getUndoneList().toArrayList();
	}
	
	public static void displayUpcomingList() {
		displayList.clear();
		displayList = GrandTaskList.getUpcomingList().toArrayList();
	}
	
	public static void displayDoneList() {
		displayList.clear();
		displayList = GrandTaskList.getDoneList().toArrayList();
	}
	
	public static void searchTask(Predicate<Task> predicate) {
		ArrayList<Task> searchResults = new ArrayList<Task>();
		for (Task task: displayList) {
			if (predicate.test(task)) {
				searchResults.add(task);
			}
		}
		displayList = searchResults;
	}
	
	// methods to clear lists
	public static void clearDisplayedTasks() throws IOException {
		for (Task task: displayList) {
			GrandTaskList.deleteTask(task);
		}
		displayList.clear();
	}
	
	public static void clearAllTasks() throws IOException {
		GrandTaskList.clearAll();
		displayList.clear();
	}
	
	// set and get storage path
	public static void setPath(String pathName) throws IOException {
		Database.setPath(pathName);
	}
	
	public static String getPath() {
		return Database.getPath();
	}
	
	public static ArrayList<Task> getTimelineList() {
		ArrayList<Task> result = new ArrayList<Task>();
		for (Task task: GrandTaskList.getNonRecurringList()) {
			Calendar date = task.getStartDate();
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
