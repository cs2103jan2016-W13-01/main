package storage;

import java.io.IOException;
import java.util.Calendar;
import java.util.function.Predicate;

import logic.tasks.*;
import storage.Database;
import storage.TaskList;
/* @@author A0112184R
 *
 */
public class GrandTaskList {
	
	private static TaskList<Task> normalTaskList;
	private static TaskList<RecurringTask> recurringTaskList;
	private static TaskList<Task> doneTaskList;
	
	public static void initialize() {
		normalTaskList = new TaskList<Task>("normal");
		recurringTaskList = new TaskList<RecurringTask>("recurring");
		doneTaskList = new TaskList<Task>("done");
	}
	
	public static TaskList<Task> getNormalList() {
		return normalTaskList;
	}
	
	public static TaskList<Task> getRecurringList() {
		TaskList<Task> results = new TaskList<Task>();
		recurringTaskList.merge(results);
		return results;
	}
	
	public static TaskList<Task> getDoneList() {
		return doneTaskList;
	}
	
	public static TaskList<Task> getTotalList() {
		TaskList<Task> results = new TaskList<Task>();
		recurringTaskList.merge(results);
		normalTaskList.merge(results);
		doneTaskList.merge(results);
		return results;
	}
	
	public static TaskList<Task> getNoRecurringList() {
		TaskList<Task> results = new TaskList<Task>();
		normalTaskList.merge(results);
		doneTaskList.merge(results);
		return results;
	}
	
	public static TaskList<Task> getUndoneList() {
		TaskList<Task> results = new TaskList<Task>();
		normalTaskList.merge(results);
		recurringTaskList.merge(results);
		return results;
	}
	
	public static TaskList<Task> getListByName(String name) {
		if (name.equals("all")) {
			return getTotalList();
		} else if (name.equals("upcoming")) {
			return getUpcomingList();
		} else if (name.equals("completed")) {
			return getDoneList();
		} else if (name.equals("incomplete")) {
			return getUndoneList();
		} else {
			return new TaskList<Task>();
		}
	}
	
	public static TaskList<Task> getUpcomingList() {
		return getUpComingList(2);
	}
	
	public static TaskList<Task> getUpComingList(int numDays) {
		Calendar date = Calendar.getInstance();
		TaskList<Task> result = new TaskList<Task>();
		for (int i=0; i<numDays; i++) {
			date.add(Calendar.DATE, 1);
			getTasksOnDate(date).merge(result);
		}
		return result;
	}

	public static boolean addNewTask(Task task) throws IOException {
		boolean result;
		if (task instanceof RecurringTask) {
			result = recurringTaskList.add((RecurringTask) task);
			Database.saveRecurring();
		} else {
			result = normalTaskList.add(task);
			Database.saveNormal();
		}
		return result;
	}
	
	public static boolean deleteTask(Task task) throws IOException {
		boolean result;
		if (task instanceof RecurringTask) {
			result = recurringTaskList.delete((RecurringTask) task);
			Database.saveRecurring();
		} else {
			result = normalTaskList.delete(task);
			Database.saveNormal();
		}
		return result;
	}
	
	public static boolean markDone(Task task) throws IOException {
		boolean result = deleteTask(task);
		doneTaskList.add(task);
		task.setDone(true);
		Database.saveDone();
		return result;
	}
	
	public static boolean unmarkDone(Task task) throws IOException {
		boolean result = doneTaskList.delete(task);
		addNewTask(task);
		task.setDone(false);
		Database.saveDone();
		return result;
	}
	
	public static TaskList<Task> search(Predicate<Task> predicate) {
		TaskList<Task> results = new TaskList<Task>();
		normalTaskList.merge(results, predicate);
		recurringTaskList.merge(results, predicate);
		doneTaskList.merge(results, predicate);
		return results;
	}
	
	public static TaskList<Task> getTasksOnDate(Calendar date) {
		TaskList<Task> result = new TaskList<Task>();
		return result;
	}
	
	public static void clearIncomplete() {
		recurringTaskList.clear();
		normalTaskList.clear();
	}
	
	public static void clearAll() throws IOException {
		recurringTaskList.clear();
		normalTaskList.clear();
		doneTaskList.clear();
		Database.clear();
	}
}
