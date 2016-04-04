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
public class SortedTaskList<T extends Task> implements TaskList<T> {
	
	private SortedSet<T> taskList;
	private String name;
	
	public SortedTaskList() {
		name = null;
		taskList = new TreeSet<T>(new TaskUtil.TaskComparator());
	}
	
	public SortedTaskList(String name) {
		this.name = name;
		taskList = new TreeSet<T>(new TaskUtil.TaskComparator());
	}
	
	public Iterator<T> iterator() {
		return taskList.iterator();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public SortedSet<T> getInternalList() {
		return taskList;
	}
	
	public boolean isEmpty() {
		return taskList.isEmpty();
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
	
	public TaskList<Task> search(Predicate<Task> predicate) {
		SortedTaskList<Task> results = new SortedTaskList<Task>();
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
	
	public TaskList<Task> merge(TaskList<Task> result) {
		for (Task task: taskList) {
			result.add(task);
		}
		return result;
	}
	
	public void clear() {
		taskList.clear();
	}
}
