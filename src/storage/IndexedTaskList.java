package storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import logic.tasks.Task;

/* @@author A0112184R
 * Class to store tasks in adding order rather than by sorting
 */
public class IndexedTaskList<T extends Task> implements TaskList<T> {
	
	private List<T> taskList;
	private String name;
	
	public IndexedTaskList() {
		name = null;
		taskList = new ArrayList<T>();
	}
	
	public IndexedTaskList(String name) {
		this.name = name;
		taskList = new ArrayList<T>();
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
	
	public List<T> getInternalList() {
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
		IndexedTaskList<Task> results = new IndexedTaskList<Task>();
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
	
	public T get(int index) {
		return taskList.get(index);
	}
	
	public T delete(int index) {
		return taskList.remove(index);
	}
	
	public boolean add(int index, T task) {
		return taskList.add(task);
	}
}
