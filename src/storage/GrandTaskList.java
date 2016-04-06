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
	
	private static TaskList<Task> floatTaskList;
	private static TaskList<Deadline> deadlineList;
	private static TaskList<Session> sessionList;
	private static TaskList<RecurringTask> recurringTaskList;
	
	private static TaskList<Task> doneTaskList;
	
	public static void initialize() {
		floatTaskList = new TaskList<Task>();
		deadlineList = new TaskList<Deadline>();
		sessionList = new TaskList<Session>();
		recurringTaskList = new TaskList<RecurringTask>();
		doneTaskList = new TaskList<Task>();
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
	
	public static TaskList<Task> getDoneList() {
		return doneTaskList;
	}
	
	public static TaskList<Task> getTotalList() {
		TaskList<Task> results = new TaskList<Task>();
		deadlineList.merge(results);
		sessionList.merge(results);
		recurringTaskList.merge(results);
		floatTaskList.merge(results);
		doneTaskList.merge(results);
		return results;
	}
	
	public static TaskList<Task> getNoRecurringList() {
		TaskList<Task> results = new TaskList<Task>();
		deadlineList.merge(results);
		sessionList.merge(results);
		floatTaskList.merge(results);
		return results;
	}
	
	public static TaskList<Task> getUndoneList() {
		TaskList<Task> results = new TaskList<Task>();
		deadlineList.merge(results);
		sessionList.merge(results);
		floatTaskList.merge(results);
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
		task.markDone();
		if (task.isRecurrence()) {
			task.getParent().addException(task.getStartDate());
		}
		Database.saveDone();
		return result;
	}
	
	public static boolean unmarkDone(Task task) throws IOException {
		boolean result = doneTaskList.delete(task);
		if (task.isRecurrence()) {
			task.getParent().removeException(task.getStartDate());
		} else {
			addNewTask(task);
			task.unmark();
		}
		Database.saveDone();
		return result;
	}
	
	public static TaskList<Task> search(Predicate<Task> predicate) {
		TaskList<Task> results = new TaskList<Task>();
		deadlineList.search(results, predicate);
		sessionList.search(results, predicate);
		recurringTaskList.search(results, predicate);
		floatTaskList.search(results, predicate);
		return results;
	}
	
	public static TaskList<Task> getTasksOnDate(Calendar date) {
		TaskList<Task> result = new TaskList<Task>();
		for (Task task: deadlineList) {
			if (TaskUtil.willOccur(task, date)) {
				result.add(task);
			}
		}
		for (Task task: sessionList) {
			if (TaskUtil.willOccur(task, date)) {
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
