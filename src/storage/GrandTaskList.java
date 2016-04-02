package storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;

import logic.tasks.*;
/* @@author A0112184R
 *
 */
public class GrandTaskList {
	
	private static TaskList<Task> floatTaskList;
	private static TaskList<Deadline> deadlineList;
	private static TaskList<Session> sessionList;
	private static TaskList<RecurringTask> recurringTaskList;
	
	public static void initialize() {
		floatTaskList = new TaskList<Task>();
		deadlineList = new TaskList<Deadline>();
		sessionList = new TaskList<Session>();
		recurringTaskList = new TaskList<RecurringTask>();
	}
	
	public static TaskList<Task> getFloatList() {
		return floatTaskList;
	}
	
	public static TaskList<Deadline> getDeadlineList() {
		return deadlineList;
	}
	
	public static TaskList<Session> getSessionList() {
		return sessionList;
	}
	
	public static TaskList<RecurringTask> getRecurringList() {
		return recurringTaskList;
	}
	
	public static TaskList<Task> getTotalList() {
		TaskList<Task> results = new TaskList<Task>();
		for (Task task: deadlineList) {
			results.add(task);
		}
		for (Task task: sessionList) {
			results.add(task);
		}
		for (Task task: recurringTaskList) {
			results.add(task);
		}
		for (Task task: floatTaskList) {
			results.add(task);
		}
		return results;
	}
	
	public static TaskList<Task> getNoRecurringList() {
		TaskList<Task> results = new TaskList<Task>();
		for (Task task: deadlineList) {
			results.add(task);
		}
		for (Task task: sessionList) {
			results.add(task);
		}
		for (Task task: floatTaskList) {
			results.add(task);
		}
		return results;
	}
	
	public static boolean addNewTask(Task task) throws IOException {
		boolean result;
		if (task instanceof Deadline) {
			result = deadlineList.add((Deadline) task);
			Database.saveDeadline();
		} else if (task instanceof Session) {
			result = sessionList.add((Session) task);
			Database.saveSession();
		} else if (task instanceof RecurringTask) {
			result = recurringTaskList.add((RecurringTask) task);
			Database.saveRecurring();
		} else {
			result = floatTaskList.add(task);
			Database.saveFloat();
		}
		return result;
	}
	
	public static boolean deleteTask(Task task) throws IOException {
		boolean result;
		if (task instanceof Deadline) {
			result = deadlineList.delete((Deadline) task);
			Database.saveDeadline();
		} else if (task instanceof Session) {
			result = sessionList.delete((Session) task);
			Database.saveSession();
		} else if (task instanceof RecurringTask) {
			result = recurringTaskList.delete((RecurringTask) task);
			Database.saveRecurring();
		} else {
			result = floatTaskList.delete(task);
			Database.saveFloat();
		}
		return result;
	}
	
	public static ArrayList<Task> search(Predicate<Task> predicate) {
		ArrayList<Task> results = new ArrayList<Task>();
		deadlineList.search(results, predicate);
		sessionList.search(results, predicate);
		recurringTaskList.search(results, predicate);
		floatTaskList.search(results, predicate);
		return results;
	}
}
