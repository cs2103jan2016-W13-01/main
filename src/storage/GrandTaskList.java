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
	private static TaskList<Task> recurringInstanceList;
	private static TaskList<RecurringTask> doneRecurringList;
	
	// initializer
	public static void initialize() throws IOException {
		normalTaskList = new TaskList<Task>("normal");
		recurringTaskList = new TaskList<RecurringTask>("recurring");
		doneTaskList = new TaskList<Task>("done");
		doneRecurringList = new TaskList<RecurringTask>("done recurring");
		recurringInstanceList = new TaskList<Task>("instances");
		Database.load();
		initRecurringInstances();
		initDoneList();
	}
	
	public static void initRecurringInstances() {
		for (RecurringTask recur: recurringTaskList) {
			recurringInstanceList.add(recur.generateNearestInstance());
		}
	}
	
	public static void initDoneList() {
		for (RecurringTask recurTask: recurringTaskList) {
			addDoneRecurringInstance(recurTask);
		}
	}
	
	public static void addDoneRecurringInstance(RecurringTask recurTask) {
		for (Task instance: recurTask.getDoneInstances()) {
			instance.setDone(true);
			doneTaskList.add(instance);
		}
	}
	
	// get different types of list
	public static TaskList<Task> getNormalList() {
		return normalTaskList;
	}
	
	public static TaskList<Task> getRecurringInstanceList() {
		return recurringInstanceList;
	}
	
	public static TaskList<RecurringTask> getRecurringList() {
		return recurringTaskList;
	}
	
	public static TaskList<Task> getDoneList() {
		return doneTaskList;
	}
	
	public static TaskList<RecurringTask> getDoneRecurringList() {
		return doneRecurringList;
	}
	
	public static TaskList<Task> getTotalList() {
		TaskList<Task> results = new TaskList<Task>();
		normalTaskList.merge(results);
		doneTaskList.merge(results);
		recurringInstanceList.merge(results);
		return results;
	}
	
	public static TaskList<Task> getNonRecurringList() {
		TaskList<Task> results = new TaskList<Task>();
		normalTaskList.merge(results);
		doneTaskList.merge(results);
		return results;
	}
	
	public static TaskList<Task> getUndoneList() {
		TaskList<Task> results = new TaskList<Task>();
		normalTaskList.merge(results);
		recurringInstanceList.merge(results);
		return results;
	}	
	
	public static TaskList<Task> getUpcomingList() {
		return getUpComingList(2);
	}
	
	public static TaskList<Task> getUpComingList(int numDays) {
		Calendar date = Calendar.getInstance();
		TaskList<Task> result = new TaskList<Task>();
		for (int i=0; i<numDays; i++) {
			getTasksOnDate(date).merge(result);
			date.add(Calendar.DATE, 1);
		}
		return result;
	}
	
	public static TaskList<Task> search(Predicate<Task> predicate) {
		TaskList<Task> results = new TaskList<Task>();
		normalTaskList.merge(results, predicate);
		recurringInstanceList.merge(results, predicate);
		doneTaskList.merge(results, predicate);
		return results;
	}
	
	public static TaskList<Task> getTasksOnDate(Calendar date) {
		TaskList<Task> results = new TaskList<Task>();
		for (Task task: normalTaskList) {
			if (task.willOccur(date)) {
				results.add(task);
			}
		}
		for (RecurringTask task: recurringTaskList) {
			if (task.willOccur(date)) {
				results.add(task.generate(date));
			}
		}
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
	
	// operations on tasks
	public static boolean addNewTask(Task task) throws IOException {
		boolean result;
		if (task instanceof RecurringTask) {
			result = addRecurringTask((RecurringTask) task);
			Database.saveRecurring();
		} else if (task.getParent() == null) {
			result = normalTaskList.add(task);
			Database.saveNormal();
		} else {
			RecurringTask parent = task.getParent();
			recurringInstanceList.delete(parent.generateNearestInstance());
			result = parent.reviveDeletedInstance(task);
			recurringInstanceList.add(parent.generateNearestInstance());
			Database.saveRecurring();
		}
		return result;
	}
	
	public static boolean addRecurringTask(RecurringTask recurTask) {
		boolean result = recurringTaskList.add(recurTask);
		if (!result) {
			return result;
		}
		for (Task task: doneTaskList) {
			if (task.getParent() == recurTask) {
				doneTaskList.delete(task);
				recurTask.addDoneInstance(task);
			}
		}
		recurringInstanceList.add(recurTask.generateNearestInstance());
		return result;
	}
	
	public static boolean deleteTask(Task task) throws IOException {
		boolean result;
		if (task instanceof RecurringTask) {
			result = deleteRecurringTask((RecurringTask) task);
			Database.saveRecurring();
		} else if (task.getParent() == null) {
			result = normalTaskList.delete(task);
			Database.saveNormal();
		} else {
			RecurringTask parent = task.getParent();
			parent.addDeletedInstance(task);
			if (recurringInstanceList.contains(task)) {
				recurringInstanceList.delete(task);
				recurringInstanceList.add(parent.generateNearestInstance());
			}
			Database.saveRecurring();
			result = true;
		}
		return result;
	}
	
	public static boolean deleteRecurringTask(RecurringTask recurTask) throws IOException {
		boolean result = recurringTaskList.delete(recurTask);
		if (!result) {
			return result;
		}
		for (Task instance: recurTask.getDoneInstances()) {
			instance.setParent(null);
		}
		Database.saveDone();
		return result;
	}
	
	public static boolean markDone(Task task) throws IOException {
		boolean result;
		task.setDone(true);
		if (task instanceof RecurringTask) {
			result = recurringTaskList.delete((RecurringTask) task);
			doneRecurringList.add((RecurringTask) task);
			Database.saveRecurring();
			Database.saveDoneRecurring();
		} else if (task.getParent() == null) {
			result = deleteTask(task);
			doneTaskList.add(task);
			Database.saveNormal();
			Database.saveDone();
		} else {
			RecurringTask parent = task.getParent();
			parent.addDoneInstance(task);
			if (recurringInstanceList.delete(task)) {
				recurringInstanceList.add(parent.generateNearestInstance());
			}
			Database.saveRecurring();
			result = true;
		}
		return result;
	}
	
	public static boolean unmarkDone(Task task) throws IOException {
		boolean result;
		task.setDone(false);
		if (task instanceof RecurringTask) {
			result = doneRecurringList.delete((RecurringTask) task);
			recurringTaskList.add((RecurringTask) task);
			Database.saveRecurring();
			Database.saveDoneRecurring();
		} else if (task.getParent() == null) {
			result = doneTaskList.delete(task);
			addNewTask(task);
			Database.saveDone();
		} else {
			RecurringTask parent = task.getParent();
			recurringInstanceList.delete(parent.generateNearestInstance());
			parent.removeDoneInstance(task);
			recurringInstanceList.add(parent.generateNearestInstance());
			Database.saveRecurring();
			result = true;
		}
		return result;
	}
	
	public static void clearIncomplete() {
		normalTaskList.clear();
	}
	
	public static void clearAll() throws IOException {
		recurringTaskList.clear();
		normalTaskList.clear();
		doneTaskList.clear();
		doneRecurringList.clear();
		recurringInstanceList.clear();
		Database.clear();
	}
}
