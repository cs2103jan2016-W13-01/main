package storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;

import logic.tasks.Task;
import logic.tasks.TaskUtil;

/* @@author A0112184R
 * This class contains the lists of specific types of tasks
 */
public class TaskList<T extends Task> implements Iterable<T> {
	
	private SortedSet<T> taskList;
	
	public TaskList() {
		taskList = new TreeSet<T>(new TaskUtil.TaskComparator());
	}
	
	public Iterator<T> iterator() {
		return taskList.iterator();
	}
	
	public SortedSet<T> getInternalList() {
		return taskList;
	}
	
	public boolean add(T task) {
		return taskList.add(task);
	}
	
	public boolean addAll(TaskList<T> addedList) {
		return taskList.addAll(addedList.getInternalList());
	}
	
	public boolean delete(T task) {
		return taskList.remove(task);
	}
	
	public int size() {
		return taskList.size();
	}
	
	public TaskList<T> search(Predicate<T> predicate) {
		TaskList<T> results = new TaskList<T>();
		for (T task: taskList) {
			if (predicate.test(task)) {
				results.add(task);
			}
		}
		return results;
	}
	
	public TaskList<Task> search(TaskList<Task> results, Predicate<Task> predicate) {
		for (T task: taskList) {
			if (predicate.test(task)) {
				results.add(task);
			}
		}
		return results;
	}

	public ArrayList<Task> search(ArrayList<Task> results, Predicate<Task> predicate) {
		for (T task: taskList) {
			if (predicate.test(task)) {
				results.add(task);
			}
		}
		return results;
	}
	
	public void merge(TaskList<Task> result) {
		for (Task task: taskList) {
			result.add(task);
		}
	}
}
