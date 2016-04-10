package storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;

import logic.tasks.Task;

/* @@author A0112184R
 * This class contains the lists of specific types of tasks
 */
public class TaskList<T extends Task> implements Iterable<T> {
	
	private SortedSet<T> taskList;
	private String name;
	
	public TaskList() {
		taskList = new TreeSet<T>();
		name = null;
	}
	
	public TaskList(String nameString) {
		taskList = new TreeSet<T>();
		name = nameString;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String nameString) {
		name = nameString;
	}
	
	public Iterator<T> iterator() {
		return taskList.iterator();
	}
	
	public SortedSet<T> getInternalList() {
		return taskList;
	}
	
	public boolean contains(Task task) {
		return taskList.contains(task);
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
	
	public void clear() {
		taskList.clear();
	}
	
	public void merge(TaskList<Task> results, Predicate<Task> predicate) {
		for (Task task: taskList) {
			if (predicate.test(task)) {
				results.add(task);
			}
		}
	}
	
	public void merge(TaskList<Task> results) {
		Predicate<Task> predicate = new Predicate<Task>() {
			public boolean test(Task task) {
				return true;
			}
		};
		merge(results, predicate);
	}
	
	public ArrayList<Task> toArrayList() {
		ArrayList<Task> result = new ArrayList<Task>();
		for (Task task: taskList) {
			result.add(task);
		}
		return result;
	}

}
