package storage;

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
	
	public static boolean add(Task task) {
		return floatTaskList.add(task);
	}
	
	public static boolean add(Deadline task) {
		return deadlineList.add(task);
	}
	
	public static boolean add(Session task) {
		return sessionList.add(task);
	}
	
	public static boolean add(RecurringTask task) {
		return recurringTaskList.add(task);
	}
	
	public static boolean delete(Task task) {
		return floatTaskList.delete(task);
	}
	
	public static boolean delete(Deadline task) {
		return deadlineList.delete(task);
	}
	
	public static boolean delete(Session task) {
		return sessionList.delete(task);
	}
	
	public static boolean delete(RecurringTask task) {
		return recurringTaskList.delete(task);
	}
	
	public static TaskList<Task> search(Predicate<Task> predicate) {
		TaskList<Task> results = new TaskList<Task>();
		deadlineList.search(results, predicate);
		sessionList.search(results, predicate);
		recurringTaskList.search(results, predicate);
		floatTaskList.search(results, predicate);
		return results;
	}
}
