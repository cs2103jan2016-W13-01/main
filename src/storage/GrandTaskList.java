package storage;

import java.io.IOException;
import java.util.Calendar;
import java.util.function.Predicate;

import org.apache.commons.lang.time.DateUtils;

import logic.tasks.*;
/* @@author A0112184R
 *
 */
public class GrandTaskList {
	
	private static SortedTaskList<Task> floatTaskList;
	private static SortedTaskList<Deadline> deadlineList;
	private static SortedTaskList<Session> sessionList;
	private static SortedTaskList<RecurringTask> recurringTaskList;
	
	private static SortedTaskList<Task> doneTaskList;
	
	public static void initialize() {
		floatTaskList = new SortedTaskList<Task>();
		deadlineList = new SortedTaskList<Deadline>();
		sessionList = new SortedTaskList<Session>();
		recurringTaskList = new SortedTaskList<RecurringTask>();
		doneTaskList = new SortedTaskList<Task>();
	}
	
	public static SortedTaskList<Task> getFloatList() {
		return floatTaskList;
	}
	
	public static SortedTaskList<Deadline> getDeadlineList() {
		return deadlineList;
	}
	
	public static SortedTaskList<Session> getSessionList() {
		return sessionList;
	}
	
	public static SortedTaskList<RecurringTask> getRecurringList() {
		return recurringTaskList;
	}
	
	public static SortedTaskList<Task> getDoneList() {
		return doneTaskList;
	}
	
	public static SortedTaskList<Task> getTotalList() {
		SortedTaskList<Task> results = new SortedTaskList<Task>();
		deadlineList.merge(results);
		sessionList.merge(results);
		recurringTaskList.merge(results);
		floatTaskList.merge(results);
		doneTaskList.merge(results);
		return results;
	}
	
	public static SortedTaskList<Task> getNoRecurringList() {
		SortedTaskList<Task> results = new SortedTaskList<Task>();
		deadlineList.merge(results);
		sessionList.merge(results);
		floatTaskList.merge(results);
		return results;
	}
	
	public static SortedTaskList<Task> getUnDoneList() {
		SortedTaskList<Task> results = new SortedTaskList<Task>();
		deadlineList.merge(results);
		sessionList.merge(results);
		floatTaskList.merge(results);
		recurringTaskList.merge(results);
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
	
	public static boolean markDone(Task task) throws IOException {
		boolean result = deleteTask(task);
		doneTaskList.add(task);
		Database.saveDone();
		return result;
	}
	
	public static boolean unmarkDone(Task task) throws IOException {
		boolean result = doneTaskList.delete(task);
		addNewTask(task);
		return result;
	}
	
	public static TaskList<Task> search(Predicate<Task> predicate) {
		TaskList<Task> results = new SortedTaskList<Task>();
		deadlineList.search(results, predicate);
		sessionList.search(results, predicate);
		recurringTaskList.search(results, predicate);
		floatTaskList.search(results, predicate);
		return results;
	}
	
	public static SortedTaskList<Task> getTasksOnDate(Calendar date) {
		SortedTaskList<Task> result = new SortedTaskList<Task>();
		for (Task task: deadlineList) {
			if (DateUtils.isSameDay(task.getMainDate(), date)) {
				result.add(task);
			}
		}
		for (Task task: sessionList) {
			if (DateUtils.isSameDay(task.getMainDate(), date)) {
				result.add(task);
			}
		}
		for (RecurringTask task: recurringTaskList) {
			if (task.willOccur(date)) {
				result.add(task.generate(date));
			}
		}
		return result;
	}
	
	public static void clearIncomplete() {
		deadlineList.clear();
		sessionList.clear();
		recurringTaskList.clear();
		floatTaskList.clear();
	}
	
	public static void clearAll() throws IOException {
		deadlineList.clear();
		sessionList.clear();
		recurringTaskList.clear();
		floatTaskList.clear();
		doneTaskList.clear();
		Database.clear();
	}
}
