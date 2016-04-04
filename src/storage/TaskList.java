package storage;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

import logic.tasks.Task;

/* @@author A0112184R
 *
 */
public interface TaskList<T extends Task> extends Iterable<T> {
	
	public String getName();
	public void setName(String name);
	public boolean add(T task);
	public int size();
	public boolean isEmpty();
	public boolean delete(T task);
	public boolean addAll(TaskList<T> otherList);
	public Iterator<T> iterator();
	public TaskList<Task> search(Predicate<Task> predicate);
	public TaskList<Task> search(TaskList<Task> result, Predicate<Task> predicate);
	public TaskList<Task> merge(TaskList<Task> result);
	public void clear();
	public Collection<T> getInternalList();
	
}
