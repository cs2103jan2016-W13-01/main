/* @@author A0112184R */
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
		
	}
	
	public static boolean addNewTask(Task task) {
		if (task instanceof Deadline) {
			return deadlineList.add((Deadline) task);
		} else if (task instanceof Session) {
			return sessionList.add((Session) task);
		} else if (task instanceof RecurringTask) {
			return recurringTaskList.add((RecurringTask) task);
		} else {
			return floatTaskList.add(task);
		}
	}
	
	public static boolean deleteTask(Task task) {
		if (task instanceof Deadline) {
			return deadlineList.delete((Deadline) task);
		} else if (task instanceof Session) {
			return sessionList.delete((Session) task);
		} else if (task instanceof RecurringTask) {
			return recurringTaskList.delete((RecurringTask) task);
		} else {
			return floatTaskList.delete(task);
		}
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
